package es.jccm.edu.shared.application.ports.out.usuarios;

import es.jccm.edu.shared.application.domain.usuarios.Usuario;

public interface UsuarioProvider {

	Usuario getUsuarioByOid(String oid);
	
}
