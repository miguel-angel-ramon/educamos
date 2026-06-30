package es.jccm.edu.aulasVirtuales.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.aulasVirtuales.adapter.in.rest.BasePath;
import es.jccm.edu.shared.configuration.swagger.BasePathOpenApiCustomiser;

@Configuration
public class SwaggerConfigAulasvirtuales {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi aulasVirtualesApis() {
		return GroupedOpenApi.builder()
				.group("Aulas Virtuales")
				.pathsToMatch("/**/aulasVirtuales/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.addOpenApiCustomiser(new BasePathOpenApiCustomiser(BasePath.AulasVirtualesBasePath))
				.build();
	}
}
