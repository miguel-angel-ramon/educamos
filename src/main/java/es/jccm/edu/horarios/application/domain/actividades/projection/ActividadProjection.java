package es.jccm.edu.horarios.application.domain.actividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Actividad", description = "Proyección para rescatar las actividades de un profesor")
public interface ActividadProjection {
	
	@Schema(description = "Abreviatura")
	String getabreviatura();
	
	@Schema(description = "Descripción")
	String getDescripcion();
	
	@Schema(description = "Orden")
	Integer getOrden();
	
}

