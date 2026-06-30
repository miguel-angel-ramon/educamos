package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HorarioGrupoActividad", description = "Proyección para rescatar el horario semanal de un profesor")
public interface HorarioGrupoActividadProjection {
	
	@Schema(description = "Id del grupo de actividad")
	Long getIdGrupoActividad();
	
	@Schema(description = "Id del tramo")
	Long getIdTramo();
	
	@Schema(description = "Id del horario")
	Long getIdHorario();
	
	@Schema(description = "Fecha de inicio")
	Date getFechaInicio();

	@Schema(description = "Fecha de fin")
	Date getFechaFin();
	
	@Schema(description = "Hora de inicio")
	String getHoraInicio();
	
	@Schema(description = "Hora de fin")
	String getHoraFin();
	
	@Schema(description = "Día de la semana")
	Integer getDiaSemana();
	
	@Schema(description = "Descripción")
	String getDescripcion();
	
	@Schema(description = "Dependencia")
	String getDependencia();
	
}

