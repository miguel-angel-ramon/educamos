/*package es.jccm.edu.shared.configuration.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfiguration {
	
	@Value("${spring.security.oauth2.resource.jwt.key-value}")
	private String secretKey;

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HS256");
		return NimbusJwtDecoder.withSecretKey(key).build();
	}
}*/
