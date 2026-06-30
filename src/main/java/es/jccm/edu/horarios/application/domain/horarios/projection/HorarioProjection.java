package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HorarioSemanal", description = "Proyección para rescatar el horario semanal de un profesor")
public interface HorarioProjection {
	
	@Schema(description = "Id del horario")
	String getIdHorario();
	
	@Schema(description = "Fecha de inicio")
	Date getFechaInicio();

	@Schema(description = "Fecha de fin")
	Date getFechaFin();
	
	@Schema(description = "Día de la semana")
	Integer getDiaSemana();

	@Schema(description = "Hora de inicio")
	String getHoraInicio();
	
	@Schema(description = "Hora de fin")
	String getHoraFin();
	
	@Schema(description = "getHoraInicioCadena")
	String getHoraInicioCadena();
	
	@Schema(description = "getHoraFinCadena")
	String getHoraFinCadena();
	
	@Schema(description = "Requnidad")
	String getRequnidad();
	
	@Schema(description = "Alumnos")
	String getAlumnos();
	
	@Schema(description = "Id de la Actividad")
	Integer getIdActividad();
	
	@Schema(description = "Abreviatura de la Actividad")
	String getAbreviaturaActividad();
	
	@Schema(description = "Descripción de la Actividad")
	String getDescripcionActividad();
	
	@Schema(description = "Id de la Materia")
	Integer getIdMateria();
	
	@Schema(description = "Id de la unidad")
	Integer getIdUnidad();
	
	@Schema(description = "Abreviatura de la Materia")
	String getAbreviaturaMateria();
	
	@Schema(description = "Descripción de la Materia")
	String getDescripcionMateria();
	
	@Schema(description = "Abreviatura del Aula")
	String getAbreviaturaAula();
	
	@Schema(description = "Descripción del Aula")
	String getDescripcionAula();
	
	@Schema(description = "Docencia")
	String getDocencia();
	
	@Schema(description = "Minutos")
	String getMinutos();
	
	@Schema(description = "Consolidado")
	String getConsolidado();
	
}

