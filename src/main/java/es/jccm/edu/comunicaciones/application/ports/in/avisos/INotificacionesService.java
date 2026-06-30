package es.jccm.edu.comunicaciones.application.ports.in.avisos;

import org.springframework.data.domain.Page;

import es.jccm.edu.comunicaciones.application.domain.avisos.Notificacion;


public interface INotificacionesService {
	
	Page<Notificacion> getNotificaciones(Long idUsuario, Long codCentro, Long idEmpleado, Long Anno, String tipo, String text, String perfil, int page, int size);
	
	Long getNoFirmadosCount(Long idUsuario, Long idEmpleado, Long Anno);

}
