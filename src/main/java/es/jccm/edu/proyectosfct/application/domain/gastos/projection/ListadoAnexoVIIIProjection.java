package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado gastos anexo", description = "Descripcion para el modelo de Listado gastos anexo")
public interface ListadoAnexoVIIIProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Importe total tutores")
	String getTotalT();
	
	@Schema(description = "Importe total Alumnado")
	String getTotalA();
	
	@Schema(description = "Localidad")
	String getLocalidad();
	
	@Schema(description = "Centro")
	String getDenominacion();
}

