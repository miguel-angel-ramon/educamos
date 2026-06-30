package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Criterios", description = "Proyección para rescatar los criterios de evaluación")
public interface CriteriosConvProjection {

	@Schema(description = "Id relacion del criterio")
	Long getIdRelacionCri();

	@Schema(description = "Id del criterio")
	Long getIdCriterio();
	
	@Schema(description = "Descripción")
	String getDescripcion();

	@Schema(description = "Abreviatura")
	String getCodigo();

	@Schema(description = "Peso")
	Float getPeso();
	
}