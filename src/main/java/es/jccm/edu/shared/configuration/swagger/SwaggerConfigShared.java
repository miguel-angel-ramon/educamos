package es.jccm.edu.shared.configuration.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigShared {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;


	@Bean
	GroupedOpenApi sharedApis() {
		return GroupedOpenApi.builder()
				.group("Shared")
				.pathsToMatch("/**/shared/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}

}
