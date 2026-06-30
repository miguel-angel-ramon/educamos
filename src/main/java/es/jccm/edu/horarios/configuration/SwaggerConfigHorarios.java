package es.jccm.edu.horarios.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigHorarios {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi horariosApis() {
		return GroupedOpenApi.builder()
				.group("Horarios")
				.pathsToMatch("/**/horarios/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}
}
