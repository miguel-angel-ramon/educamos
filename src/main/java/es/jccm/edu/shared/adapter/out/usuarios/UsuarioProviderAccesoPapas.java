package es.jccm.edu.shared.adapter.out.usuarios;

import es.jccm.edu.shared.application.ports.out.resttemplate.accesopapas.ClientAccesoPapasRestTemplatePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.jccm.edu.shared.application.domain.usuarios.Usuario;
import es.jccm.edu.shared.application.ports.out.usuarios.UsuarioProvider;

@Service
public class UsuarioProviderAccesoPapas implements UsuarioProvider {

	@Autowired
	private ClientAccesoPapasRestTemplatePortOut clientAccesoPapasRestTemplatePortOut;
	
	@Override
	public Usuario getUsuarioByOid(String oid) {

		String uri = String.format("/user/%s", oid);
		
		return clientAccesoPapasRestTemplatePortOut.getForObject(uri, Usuario.class);
	}

}
