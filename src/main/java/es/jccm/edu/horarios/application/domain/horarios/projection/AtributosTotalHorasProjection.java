package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AtributosTotalHoras", description = "Proyección para rescatar los atributos necesarios para rescatar el total de horas de un profesor")
public interface AtributosTotalHorasProjection {
	
	@Schema(description = "Id de empleado")
	Integer getIdEmpleado();
	
	@Schema(description = "Fecha de toma de posesión")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	Date getFechaTomaPosesion();
	
	@Schema(description = "Fecha de inicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	Date getFechaInicio();

	@Schema(description = "Fecha de fin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	Date getFechaFin();
	
	@Schema(description = "Fecha actual")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	Date getFechaActual();
	
}