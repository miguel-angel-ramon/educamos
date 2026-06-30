package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valoración", description = "Proyección para rescatar las valoraciones")
public interface ValoracionConvProjection {

	@Schema(description = "Valoración")
	String getValoracion();

	@Schema(description = "Aprobado")
	String getAprueba();
	
}