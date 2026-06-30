package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Estados Promocion", description = "Estados Promocion")
public interface EstadosPromocionProjection {

	@Schema(description = "Resultado")
	Long getCResultado();

	@Schema(description = "Descripción")
	String getDescripcion();

	@Schema(description = "Abreviatura")
	String getAbrev();
	
	@Schema(description = "Estado")
	Long getIdEstado();
}
