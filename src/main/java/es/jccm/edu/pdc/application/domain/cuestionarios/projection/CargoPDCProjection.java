package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cargo", description = "Proyección para rescatar los datos de un cargo")
public interface CargoPDCProjection {
	@Schema(description = "Indica el código del cargo")
	String getCodCargo();
	
	@Schema(description = "Descripción del cargo")
	String getDescripcionCargo();

}
