package es.jccm.edu.proyectosfct.application.domain.programas.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Año, Centro", description = "Año y centro por idConvProgAlu")
public interface ListadoAnnoCentroProjection {

	@Schema(description = "Id del Centro donde se genera el parte semanal")
	Long getCentro();
	
	@Schema(description = "Año del programa sobre en que se genera el parte semanal")
	Long getAnno();
	
}
