package es.jccm.edu.gestionidentidades.application.ports.out;

public interface IClientCerberosRestTemplate {
	
	void refrescarUsuarioUsingCredentials(String username);
	
	String obtenerTokenClientCredentials();
}
