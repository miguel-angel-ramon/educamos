package es.jccm.edu.shared.configuration.security.oauth2.configuration;

import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class AuthorizeRequestsConfiguration {
	
	Map<String, List<String>> authenticated;

	Map<String, List<String>> permit;
	
}
