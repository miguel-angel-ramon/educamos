package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ValCriActAluProjection {

	 @Schema(description = "id de la calificacion")
	 Long getIdCalifica();
	 
	 @Schema(description = "nombre de la calificacion")
	 String getCalificacion();
	 
	 @Schema(description = "Numero")
	 int getNumero();
	 
	 @Schema(description = "Peso del criterio")
	 Integer getPeso();

	 @Schema(description = "Porcentaje del criterio")
	 float getPorcentaje();

	@Schema(description = "fecha de actualización")
	 Date getFechaActualiza();
	    
}
