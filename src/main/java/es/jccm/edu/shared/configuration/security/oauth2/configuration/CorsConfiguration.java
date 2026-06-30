package es.jccm.edu.shared.configuration.security.oauth2.configuration;

import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class CorsConfiguration {
    
    boolean enabled;
	
	Map<String, Mapping> mappings;

	@Data
	public static class Mapping {
	  
	    List<String> allowedOrigins;

	    List<String> allowedMethods;

	    
	}
	
}
