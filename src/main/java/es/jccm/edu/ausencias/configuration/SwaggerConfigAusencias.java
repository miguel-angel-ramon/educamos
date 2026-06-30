package es.jccm.edu.ausencias.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigAusencias {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi ausenciasApis() {
		return GroupedOpenApi.builder()
				.group("Ausencias")
				.pathsToMatch("/**/ausencias/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}
}
