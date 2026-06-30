package es.jccm.edu.gestionidentidades.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.application.ports.in.ICerberosService;
import es.jccm.edu.gestionidentidades.application.ports.out.IClientCerberosRestTemplate;

@Service
public class CerberosService implements ICerberosService{


	@Autowired
	private IClientCerberosRestTemplate clientCerberosRestTemplate;
	
	public void refrescarUsuario(String username) {
		clientCerberosRestTemplate.refrescarUsuarioUsingCredentials(username);
	}
	
}
