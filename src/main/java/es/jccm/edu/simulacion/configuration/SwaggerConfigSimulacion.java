package es.jccm.edu.simulacion.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigSimulacion {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi simulacionApis() {
		return GroupedOpenApi.builder()
				.group("Usuario y simulación")
				.pathsToMatch("/**/simulacion/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}

}
