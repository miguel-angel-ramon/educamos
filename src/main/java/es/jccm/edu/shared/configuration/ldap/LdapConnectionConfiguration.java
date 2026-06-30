package es.jccm.edu.shared.configuration.ldap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "ldap-connection")
public class LdapConnectionConfiguration {
	 
	private String initialcontextfactory;
	private String url;
	private String principal;
	private String credentials;
	private String ignore_untrusted_certs;
	private String user_root;
	private String debug;
	private String securityauthentication;
}
