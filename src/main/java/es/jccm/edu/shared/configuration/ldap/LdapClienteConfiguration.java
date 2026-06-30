package es.jccm.edu.shared.configuration.ldap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.cstic.marte.util.ldapwsclient.LdapCliente;

@Configuration
@ConfigurationProperties(prefix = "ldap-client")
public class LdapClienteConfiguration {
	private final LdapClientBeanConfig ldapClientBeanConfig=new LdapClientBeanConfig();

	@Bean
	public LdapClientBeanConfig getLdapClientBeanConfig() {
		return ldapClientBeanConfig;
	}
	
	@Bean
	public LdapCliente creaLdapCliente() {
		
		LdapClientBeanConfig config = getLdapClientBeanConfig();
		return new LdapCliente(config.getEntorno(),config.getUser(),config.getPassword());
		
	}
}
