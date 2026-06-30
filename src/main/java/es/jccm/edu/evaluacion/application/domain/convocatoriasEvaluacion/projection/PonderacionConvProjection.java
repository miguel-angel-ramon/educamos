package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ponderacion", description = "Proyección para rescatar la ponderación de una convocatoria")
public interface PonderacionConvProjection {
	
	@Schema(description = "Id de la ponderación")
	Long getIdPonderacion();
	
}