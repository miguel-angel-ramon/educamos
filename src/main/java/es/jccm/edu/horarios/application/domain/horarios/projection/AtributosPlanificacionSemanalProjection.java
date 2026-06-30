package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AtributosPlanificacionSemanal", description = "Proyección para rescatar los atributos necesarios para la planificación semanal")
public interface AtributosPlanificacionSemanalProjection {
	
	@Schema(description = "Identificador")
	Long getIdentificador();
	
	@Schema(description = "Fecha")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	Date getFecha();
	
	@Schema(description = "Descripción")
	String getDescripcion();

	@Schema(description = "Tipo")
	String getTipo();
	
}