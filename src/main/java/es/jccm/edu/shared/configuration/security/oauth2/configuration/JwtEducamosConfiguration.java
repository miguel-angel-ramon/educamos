package es.jccm.edu.shared.configuration.security.oauth2.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "educamos.security.oauth2.resource.jwt")
public class JwtEducamosConfiguration {

	String keyValue;
	
	String issuerUri;
	
}
