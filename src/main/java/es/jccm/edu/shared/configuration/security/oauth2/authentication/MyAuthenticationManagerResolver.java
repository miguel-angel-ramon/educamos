package es.jccm.edu.shared.configuration.security.oauth2.authentication;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.util.Assert;

import com.nimbusds.jwt.JWTParser;

public class MyAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

	private final AuthenticationManager authenticationManager;
	private final Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter;
	private final String keyValue;

	/**
	 * Construct a {@link MyAuthenticationManagerResolver} using the provided
	 * parameters
	 * 
	 * @param trustedIssuers a list of trusted issuers
	 */
	public MyAuthenticationManagerResolver(String... trustedIssuers) {
		this(Arrays.asList(trustedIssuers));
	}

	/**
	 * Construct a {@link MyAuthenticationManagerResolver} using the provided
	 * parameters
	 * 
	 * @param trustedIssuers a list of trusted issuers
	 */
	public MyAuthenticationManagerResolver(Collection<String> trustedIssuers) {
		Assert.notEmpty(trustedIssuers, "trustedIssuers cannot be empty");
		this.jwtAuthenticationConverter = null;
		this.keyValue = null;
		this.authenticationManager = new ResolvingAuthenticationManager(
				new TrustedIssuerJwtAuthenticationManagerResolver(
						Collections.unmodifiableCollection(trustedIssuers)::contains));
	}

	/**
	 * Construct a {@link MyAuthenticationManagerResolver} using the provided
	 * parameters
	 * 
	 * @param trustedIssuers a list of trusted issuers
	 */
	public MyAuthenticationManagerResolver(Collection<String> trustedIssuers,
			Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter) {
		Assert.notEmpty(trustedIssuers, "trustedIssuers cannot be empty");
		Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
		this.keyValue = null;
		this.authenticationManager = new ResolvingAuthenticationManager(
				new TrustedIssuerJwtAuthenticationManagerResolver(
						Collections.unmodifiableCollection(trustedIssuers)::contains));
	}

	/**
	 * Construct a {@link MyAuthenticationManagerResolver} using the provided
	 * parameters
	 * 
	 * @param trustedIssuers a list of trusted issuers
	 */
	public MyAuthenticationManagerResolver(Collection<String> trustedIssuers,
			Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter, String keyValue) {
		Assert.notEmpty(trustedIssuers, "trustedIssuers cannot be empty");
		Assert.notNull(keyValue, "keyValue cannot be null");
		Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
		this.keyValue = keyValue;
		this.authenticationManager = new ResolvingAuthenticationManager(
				new TrustedIssuerJwtAuthenticationManagerResolver(
						Collections.unmodifiableCollection(trustedIssuers)::contains,this.jwtAuthenticationConverter));
	}

	@Override
	public AuthenticationManager resolve(HttpServletRequest request) {

		String issuer = extractIssuerFromRequest(request);

		Assert.notNull(issuer, "Issuer cannot be null");
		if (issuer.contains("educamosclm")) {

			Assert.notNull(this.keyValue, "KeyValue cannot be null");
			SecretKeySpec key = new SecretKeySpec(this.keyValue.getBytes(StandardCharsets.UTF_8), "HS256");

			AuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(
					NimbusJwtDecoder.withSecretKey(key).build());
			((JwtAuthenticationProvider) jwtAuthenticationProvider)
					.setJwtAuthenticationConverter(this.jwtAuthenticationConverter);
			return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));

		} else {
			return this.authenticationManager;
		}

	}

	private String extractIssuerFromRequest(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");

		String[] jwtChunks = authorizationHeader.split(" ")[1].split("\\.");
		String decodedPayload = new String(Base64.getUrlDecoder().decode(jwtChunks[1]));
		JSONObject tokenJson = new JSONObject(decodedPayload);

		String issUer = tokenJson.getString("iss");

		return issUer;
	}

	private static class ResolvingAuthenticationManager implements AuthenticationManager {

		private final Converter<BearerTokenAuthenticationToken, String> issuerConverter = new JwtClaimIssuerConverter();

		private final AuthenticationManagerResolver<String> issuerAuthenticationManagerResolver;

		ResolvingAuthenticationManager(AuthenticationManagerResolver<String> issuerAuthenticationManagerResolver) {
			this.issuerAuthenticationManagerResolver = issuerAuthenticationManagerResolver;
		}

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			Assert.isTrue(authentication instanceof BearerTokenAuthenticationToken,
					"Authentication must be of type BearerTokenAuthenticationToken");
			BearerTokenAuthenticationToken token = (BearerTokenAuthenticationToken) authentication;
			String issuer = this.issuerConverter.convert(token);
			AuthenticationManager authenticationManager = this.issuerAuthenticationManagerResolver.resolve(issuer);
			if (authenticationManager == null) {
				throw new InvalidBearerTokenException("Invalid issuer");
			}
			return authenticationManager.authenticate(authentication);
		}

	}

	private static class JwtClaimIssuerConverter implements Converter<BearerTokenAuthenticationToken, String> {

		@Override
		public String convert(@NonNull BearerTokenAuthenticationToken authentication) {
			String token = authentication.getToken();
			try {
				String issuer = JWTParser.parse(token).getJWTClaimsSet().getIssuer();
				if (issuer != null) {
					return issuer;
				}
			} catch (Exception ex) {
				throw new InvalidBearerTokenException(ex.getMessage(), ex);
			}
			throw new InvalidBearerTokenException("Missing issuer");
		}

	}

	static class TrustedIssuerJwtAuthenticationManagerResolver implements AuthenticationManagerResolver<String> {

		private final Log logger = LogFactory.getLog(getClass());

		private final Map<String, AuthenticationManager> authenticationManagers = new ConcurrentHashMap<>();

		private final Predicate<String> trustedIssuer;
		private final Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter;
		

		TrustedIssuerJwtAuthenticationManagerResolver(Predicate<String> trustedIssuer) {
			this.trustedIssuer = trustedIssuer;
			this.jwtAuthenticationConverter = null;
		}
		
		TrustedIssuerJwtAuthenticationManagerResolver(Predicate<String> trustedIssuer, Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter ) {
			this.trustedIssuer = trustedIssuer;
			this.jwtAuthenticationConverter = jwtAuthenticationConverter;
		}

		@Override
		public AuthenticationManager resolve(String issuer) {
			if (this.trustedIssuer.test(issuer)) {
				AuthenticationManager authenticationManager = this.authenticationManagers.computeIfAbsent(issuer,
						(k) -> {
							this.logger.debug("Constructing AuthenticationManager");
							JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuer);
							if(jwtAuthenticationConverter!=null) {
								
								JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
								
								((JwtAuthenticationProvider) jwtAuthenticationProvider)
								.setJwtAuthenticationConverter(this.jwtAuthenticationConverter);
								
								
								return jwtAuthenticationProvider::authenticate;
							}else {
								return new JwtAuthenticationProvider(jwtDecoder)::authenticate;	
							}
							
							
						});
				this.logger.debug(LogMessage.format("Resolved AuthenticationManager for issuer '%s'", issuer));
				return authenticationManager;
			} else {
				this.logger.debug("Did not resolve AuthenticationManager since issuer is not trusted");
			}
			return null;
		}

	}

}
