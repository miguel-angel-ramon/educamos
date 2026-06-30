package es.jccm.edu.alumnos.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigAlumnos {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;
	
	@Bean
	GroupedOpenApi alumnosApis() {
		return GroupedOpenApi.builder()
				.group("Alumnos")
				.pathsToMatch("/**/alumnos/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}
}
