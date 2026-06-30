package es.jccm.edu.gestionidentidades.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigGestionIdentidades {
	
	@Autowired(required = false)
	OpenApiCustomiser openApiCustomiser;

	@Bean
	GroupedOpenApi gestionIdentidadesApis() {
		return GroupedOpenApi.builder()
				.group("Gestión Identidades y Usuarios")
				.pathsToMatch("/**/gestionidentidades/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.build();
	}

}
