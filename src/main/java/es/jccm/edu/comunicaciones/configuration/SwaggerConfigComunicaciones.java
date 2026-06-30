package es.jccm.edu.comunicaciones.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.comunicaciones.adapter.in.rest.BasePath;
import es.jccm.edu.shared.configuration.swagger.BasePathOpenApiCustomiser;

@Configuration
public class SwaggerConfigComunicaciones {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;


	@Bean
	GroupedOpenApi comunicacionesApis() {
		return GroupedOpenApi.builder()
				.group("Comunicaciones")
				.pathsToMatch("/**/comunicaciones/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.addOpenApiCustomiser(new BasePathOpenApiCustomiser(BasePath.ComunicacionesBasePath))
				.build();
	}
}
