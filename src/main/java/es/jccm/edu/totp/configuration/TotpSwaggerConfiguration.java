package es.jccm.edu.totp.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.shared.configuration.swagger.BasePathOpenApiCustomiser;
import es.jccm.edu.totp.adapter.in.rest.BasePath;

@Configuration(value = "SwaggerSecuredConfigurationTotp")
public class TotpSwaggerConfiguration {
	
	@Autowired(required = true)
	OpenApiCustomiser openApiCustomiser;
	
	@Bean
	GroupedOpenApi swaggerConfigTotp() {
		return GroupedOpenApi.builder()
				.group("Autenticacion_con_doble_factor_Totp")
				.pathsToMatch(BasePath.TotpBasePath+"/**")
				.addOpenApiCustomiser(openApiCustomiser)
				.addOpenApiCustomiser(new BasePathOpenApiCustomiser(BasePath.TotpBasePath))
				.build();
	}
}
