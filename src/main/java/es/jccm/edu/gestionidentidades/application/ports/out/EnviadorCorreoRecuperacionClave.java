package es.jccm.edu.gestionidentidades.application.ports.out;

import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;

public interface EnviadorCorreoRecuperacionClave {

	//String enviarCorreoDeRecuperacionDeClave(
	//		SolicitudRecuperacionClave datosRecuperacionClave, String correo);

	//TODO se ha cambiado UsuarioModuloAcceso por Usuario hay que ajustarlo
	String enviarCorreoDeRecuperacionDeClave(SolicitudRecuperacionClave solicitudRecuperacionClave,
			Usuario usuario);

}