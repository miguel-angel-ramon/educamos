package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = " HorarioDireccion", description = "Horarios del equipo directivo")
public class HorarioDireccionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del Tramo")
	private Long idTramo;
	
	@Schema(description = "Fecha de Inicio del tramo")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;
	
	@Schema(description = "Fecha de Fin del tramo")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;
	
	@Schema(description = "Hora de inicio")
	private String horaInicio;
	
	@Schema(description = "Hora de fin")
	private String horaFin;
	
	@Schema(description = "Número de día de la semana")
	private Integer diaSemana;
	
	@Schema(description = "Título")
	private String titulo;

}
