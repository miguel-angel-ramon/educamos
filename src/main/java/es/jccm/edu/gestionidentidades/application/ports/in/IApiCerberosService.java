package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioRequest;
import es.jccm.edu.gestionidentidades.application.domain.DatosUsuarioLdap;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.AltaUsuarioException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ModuloAccesoException;

public interface IApiCerberosService {

	Usuario altaUsuario(AltaUsuarioRequest usuario, DatosUsuarioLdap datosUsuarioLdap) throws AltaUsuarioException;

	Usuario getUsuarioFromDniOPasaporte(String docIdentificativo) throws ModuloAccesoException;

	void desbloqueaUsuario(String loginUsuario) throws ModuloAccesoException;

	Usuario altaUsuarioP(AltaUsuarioRequest usuarioRequest) throws AltaUsuarioException;

	Usuario altaUsuarioLdap(AltaUsuarioRequest usuarioRequest, String uidLdapUsuario) throws AltaUsuarioException;
	
}
