package es.jccm.edu.comunicaciones.application.domain.avisos.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "NotificacionProjection", description = "Proyección para recuperar notificaciones")
public interface NotificacionProjection {

	Long getIdNotificacion();
	
	String getTitulo();
	
	String getCuerpo();
	
	String getTipo();
	
	Long getIdAdjunto();
	
	String getIdrodal();
	
	String getFichero();
	
	Date getFechaInicio();
	
	Date getFechaFin();
	
}
