package es.jccm.edu.gestionauditorias.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigGestionAuditorias {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi gestionAuditoriasApis() {
		return GroupedOpenApi.builder()
				.group("Gestión de Auditorias")
				.pathsToMatch("/**/gestionauditorias/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}

}
