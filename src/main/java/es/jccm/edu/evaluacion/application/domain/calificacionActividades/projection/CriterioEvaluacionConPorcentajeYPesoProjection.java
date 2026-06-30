package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public interface CriterioEvaluacionConPorcentajeYPesoProjection {

	@Schema(description = "id del Criterio")
	Long getIdCriEva();

	@Schema(description = "id de ValCriAlu temporal")
	Long getIdValCriAluTemp();

	@Schema(description = "id de ValCriAlu")
	Long getIdValCriAlu();

	@Schema(description = "id de la calificacion")
	Long getIdCalifica();

	@Schema(description = "Numero")
	Integer getNumero();

	@Schema(description = "Peso del criterio")
	Integer getPeso();

	@Schema(description = "Porcentaje del criterio")
	Float getPorcentaje();
	    
}
