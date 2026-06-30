package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "HorarioGrupoActividad", description = "Descripcion para el modelo de horario para la el módulo del escritorio")
public class HorarioGrupoActividadListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del grupo de actividad")
	private Long idGrupoActividad;
	
	@Schema(description = "Id del tramo")
	private Long idTramo;

	@Schema(description = "Id del Horario")
	private Long idHorario;
	
	@Schema(description = "Fecha de inicio")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;

	@Schema(description = "Fecha de fin")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;
	
	@Schema(description = "Hora de inicio")
	private String horaInicio;
	
	@Schema(description = "Hora de fin")
	private String horaFin;
	
	@Schema(description = "Día de la semana")
	private Integer diaSemana;
	
	@Schema(description = "Descripción")
	private String descripcion;
	
	@Schema(description = "Dependencia")
	private String dependencia;
	

}
