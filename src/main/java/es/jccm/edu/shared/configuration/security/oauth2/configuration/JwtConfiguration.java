package es.jccm.edu.shared.configuration.security.oauth2.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class JwtConfiguration {
	
	String name;
	
	String principalClaimName;
	
	String issuerUri;
	
	List<String> roleMappings;
	
	String aclMappings;
}
