package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;

public interface GenerarSolicitudRecuperacionClaveUseCase {

	/**
	 * Crea una nueva solicitud de recuperacion de clave para el usuario y la salva
	 * 
	 * @param oid
	 * @return SolicitudDeRecuperacionDeClave creada
	 */
	SolicitudRecuperacionClave newSolicitudRecuperacionClave(Long id);

}