package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.DatosUsuarioLdap;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.UsuarioLDapException;

public interface IGetUsuarioLdapService {
	
	//DatosUsuarioLdap getDatosLdapByNif(String c_numide) throws UsuarioLDapException ;
	DatosUsuarioLdap getDatosLdapByUid(String c_numide) throws UsuarioLDapException ;
	

}
