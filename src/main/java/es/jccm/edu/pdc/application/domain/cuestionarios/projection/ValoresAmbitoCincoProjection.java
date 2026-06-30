package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valores Ambito Cinco", description = "Proyección para rescatar los valores del ambito cinco")
public interface ValoresAmbitoCincoProjection {
	@Schema(description = "Indica el numero por Nivel")
	Long getCountNivel();
	
	@Schema(description = "Indica el nivel")
	String getNivel();
	
	@Schema(description = "Nombre de la subdimension")
	String getSubdimension();
}
