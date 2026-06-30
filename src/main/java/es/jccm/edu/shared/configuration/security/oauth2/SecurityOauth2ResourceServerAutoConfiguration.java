package es.jccm.edu.shared.configuration.security.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

import es.jccm.comun.boot.config.properties.YamlPropertySourceFactory;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.HttpConfiguration;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtConfiguration;
import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtEducamosConfiguration;

@Configuration
//@AutoConfigurationPackage
@ComponentScan(basePackageClasses = { SecurityOauth2ResourceServerAutoConfiguration.class })
@EnableConfigurationProperties({ HttpConfiguration.class, JwtConfiguration.class,JwtEducamosConfiguration.class, OAuth2ClientProperties.class })
//@PropertySource(value = "classpath:security-oauth2-resource-server-default.yaml", factory = YamlPropertySourceFactory.class)
public class SecurityOauth2ResourceServerAutoConfiguration {

	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
	    return  new GrantedAuthorityDefaults("");
	}
}
