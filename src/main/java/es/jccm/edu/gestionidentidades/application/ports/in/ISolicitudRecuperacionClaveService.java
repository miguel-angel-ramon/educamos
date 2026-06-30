package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.List;

import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;

public interface ISolicitudRecuperacionClaveService {

	void guardarSolicitud(SolicitudRecuperacionClave solicitud);

	List<SolicitudRecuperacionClave> findByOidUsuario(Long oidUsuario);

}
