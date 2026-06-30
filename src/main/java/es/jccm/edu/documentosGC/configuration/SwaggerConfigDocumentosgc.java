package es.jccm.edu.documentosGC.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigDocumentosgc {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;


	@Bean
	GroupedOpenApi documentosGCApis() {
		return GroupedOpenApi.builder()
				.group("DocumentosGC")
				.pathsToMatch("/**/documentosGC/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}
}
