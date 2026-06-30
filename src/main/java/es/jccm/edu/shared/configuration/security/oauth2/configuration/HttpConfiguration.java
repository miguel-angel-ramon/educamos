package es.jccm.edu.shared.configuration.security.oauth2.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
@ConfigurationProperties(prefix = "spring.security.http")
public class HttpConfiguration {
	
	AuthorizeRequestsConfiguration authorizeRequests;
	
	CorsConfiguration cors;
	
}
