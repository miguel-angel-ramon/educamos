package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cabecera gastos anexo", description = "Descripcion para el modelo de cabecera gastos anexo")
public interface CabeceraAnexoVIIIProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Importe total tutores")
	String getProvincia();
	
	@Schema(description = "Importe total Alumnado")
	String getLiquidacion();
	
	@Schema(description = "Localidad")
	Integer getNumCentros();
	
	@Schema(description = "Centro")
	Double getTotalTutores();
	
	@Schema(description = "Centro")
	Double getTotalAlumnado();
	
	@Schema(description = "Centro")
	Double getTotal();
	
	@Schema(description = "Nombre")
	String getNombre();
}

