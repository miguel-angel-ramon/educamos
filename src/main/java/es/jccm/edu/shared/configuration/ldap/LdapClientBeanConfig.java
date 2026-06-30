package es.jccm.edu.shared.configuration.ldap;

import lombok.Data;

@Data
public class LdapClientBeanConfig {

	private String entorno;
	private String user;
	private String password;
	
}
