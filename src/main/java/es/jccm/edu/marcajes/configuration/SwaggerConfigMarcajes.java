package es.jccm.edu.marcajes.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.shared.configuration.swagger.BasePathOpenApiCustomiser;
import es.jccm.edu.marcajes.adapter.in.rest.BasePath;

@Configuration
public class SwaggerConfigMarcajes {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;
	
	@Bean
	GroupedOpenApi marcajesApi() {
		return GroupedOpenApi.builder()
				.group("Marcajes")
				.pathsToMatch("/marcaje/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.addOpenApiCustomiser(new BasePathOpenApiCustomiser(BasePath.MarcajeBasePath))
				.build();
	}
}
