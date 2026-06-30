package es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Notificacion", description = "Notificaciones Firma - Avisos")
public class NotificacionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id notificacion")
	private Long idNotificacion;
	
	@Schema(description = "Título Notificacion")
	private String titulo;
	
	@Schema(description = "Contenido")
	private String cuerpo;
	
	@Schema(description = "Tipo")
	private String tipo;
	
	@Schema(description = "fichero")
	private String fichero;
	
	@Schema(description = "rodal")
	private String idrodal;
	
	@Schema(description = "idAdjunto")
	private Long idAdjunto;
	
	@Schema(description = "Fecha de inicio de vigencia")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;
	
	@Schema(description = "Fecha de fin de vigencia")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;

}

