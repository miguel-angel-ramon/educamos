package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Promocion", description = "Promocion")
public interface PromocionProjection {
	
	@Schema(description = "idPromocion")
	Long getIdPromocion();
	
	@Schema(description = "idEstado")
	Long getIdEstado();
	
	@Schema(description = "estado")
	String getEstado();
	
	@Schema(description = "fechaSesion")
	String getFechaSesion();
}
