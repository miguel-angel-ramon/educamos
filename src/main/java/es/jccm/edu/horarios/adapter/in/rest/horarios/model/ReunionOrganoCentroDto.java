package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ReunionOrganoCentro", description = "Reunión de un órgano del centro")
public class ReunionOrganoCentroDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la reunión")
	private Long idReunion;
	
	@Schema(description = "Título")
	private String titulo;
	
	@Schema(description = "Tipo de reunión")
	private String tipo;
	
	@Schema(description = "Fecha de la reunión")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fecha;
	
	@Schema(description = "Hora de inicio")
	private String horaInicio;
	
	@Schema(description = "Hora de fin")
	private String horaFin;

}
