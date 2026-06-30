package es.jccm.edu.proyectosfct.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigProyectosfct {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi proyectosfctApis() {
		return GroupedOpenApi.builder()
				.group("Proyectos FCT")
				.pathsToMatch("/**/proyectosfct/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}


}
