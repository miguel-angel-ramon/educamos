package es.jccm.edu.shared.configuration.security.oauth2.cors;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.jccm.edu.shared.configuration.security.oauth2.configuration.CorsConfiguration.Mapping;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.HttpConfiguration;

@ConditionalOnProperty(prefix = "spring.security.http.cors", name = "enabled", havingValue = "true")
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Autowired
	HttpConfiguration httpConfiguration;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		for (Entry<String, Mapping> entry : httpConfiguration.getCors().getMappings().entrySet()) {

			// @formatter:off
			var conf = entry.getValue();
			registry.addMapping(entry.getKey())
						.allowedOriginPatterns(conf.getAllowedOrigins().stream().toArray(String[]::new))
						.allowedMethods(conf.getAllowedMethods().stream().toArray(String[]::new)); 
			// @formatter:on

		}
	}
}