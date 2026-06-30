package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CompentenciasEspecificasConPorcentajeYPesoProjection {

	@Schema(description = "id del Criterio")
	Long getIdComEsp();

	@Schema(description = "id de ValCriAlu temporal")
	Long getIdValComEspTemp();

	@Schema(description = "id de ValCriAlu")
	Long getIdValComEsp();

	@Schema(description = "id de la calificacion")
	Long getIdCalifica();

	@Schema(description = "Numero")
	int getNumero();

	@Schema(description = "Peso del criterio")
	Integer getPeso();

	@Schema(description = "Porcentaje del criterio")
	Float getPorcentaje();
	    
}
