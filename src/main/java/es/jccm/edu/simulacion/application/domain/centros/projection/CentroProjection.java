package es.jccm.edu.simulacion.application.domain.centros.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Centro", description = "Proyección para rescatar los datos de un centro")
public interface CentroProjection {
	
	@Schema(description = "Id del centro")
	Long getIdCentro();
	
	@Schema(description = "Código del centro")
	Long getCodCentro();
	
	@Schema(description = "Tipo de centro")
	String getTipo();
	
	@Schema(description = "Denominación genérica")
	String getDenominacion();
	
	@Schema(description = "Nombre")
	String getNombre();
	
}

