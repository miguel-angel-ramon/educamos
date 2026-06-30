package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Observaciones", description = "Proyección para rescatar las observaciones de la evaluación")
public interface ObservacionConvProjection {

	@Schema(description = "Id de la matrícula")
	Long getIdMatricula();
	
	@Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
	
	@Schema(description = "Observaciones de la evaluación")
	String getObservaciones();
	
}
