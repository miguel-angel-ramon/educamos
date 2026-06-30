package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CalificacionCalculoTemporalMateriaProjection {

	@Schema(description = "Id de la calificacion")
	Long getIdCalifica();

	@Schema(description = "Valor mínimo")
	float getValorMinimo();

	@Schema(description = "Valor máximo")
	float getValorMaximo();

	@Schema(description = "Abreviatura de la calificación")
	String getAbreviatura();

	@Schema(description = "Aprueba")
	String getAprueba();
}
