package es.jccm.edu.trazaerrores.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.shared.configuration.swagger.BasePathOpenApiCustomiser;

@Configuration
public class SwaggerConfigTrazaerrores {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;


	@Bean
	GroupedOpenApi trazaErroresApis() {
		
		return GroupedOpenApi.builder()
				.group("Traza_errores")
				.pathsToMatch("/**/trazaerrores/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.addOpenApiCustomiser(new BasePathOpenApiCustomiser("/apieducamosclm/trazaerrores"))
				.build();
	}

}
