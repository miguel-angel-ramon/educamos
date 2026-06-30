package es.jccm.edu.shared.configuration.security.oauth2.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import es.jccm.edu.shared.configuration.security.oauth2.configuration.HttpConfiguration;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtConfiguration;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtEducamosConfiguration;
import io.jsonwebtoken.SignatureAlgorithm;


@Configuration
public class JwtAuthenticationConfiguration {
	
	Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

	
	@Autowired
	JwtAuthenticationConverter jwtAuthenticationConverter;
	

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HttpConfiguration httpConfiguration,
    		JwtConfiguration jwtConfiguration,JwtEducamosConfiguration jwtEducamosConfiguration,
            Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
        http.cors();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        httpConfiguration.getAuthorizeRequests().getPermit().entrySet().forEach(
                uri -> uri.getValue().forEach(method -> getAuthorizeUrl(registry, uri.getKey(), method).permitAll()));

        httpConfiguration.getAuthorizeRequests().getAuthenticated().entrySet().forEach(uri -> uri.getValue()
                .forEach(method -> getAuthorizeUrl(registry, uri.getKey(), method).authenticated()));
     
        if (jwtEducamosConfiguration.getKeyValue() != null && !"".equals(jwtEducamosConfiguration.getKeyValue())) {

			List<String> issuers = new ArrayList<>();
			issuers.add(jwtConfiguration.getIssuerUri());
			issuers.add(jwtEducamosConfiguration.getIssuerUri());


			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
					.oauth2ResourceServer(oauth2ResourceServer -> {
						oauth2ResourceServer
	                    .authenticationManagerResolver(new MyAuthenticationManagerResolver(issuers,authenticationConverter, jwtEducamosConfiguration.getKeyValue()));
					});
			

		} else {
			// @formatter:off
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(authenticationConverter);
            // @formatter:on
		}

        
        return http.build();
    }
    


    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl getAuthorizeUrl(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry, String uri,
            String method) {
        if ("ALL".equals(method)) {
            return registry.antMatchers(uri);
        } else {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            return registry.antMatchers(httpMethod, uri);
        }
    }

}
