package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CentroAlumnosProjection {

	@Schema(description = "Id del centro)")
	Long getId();
	
	@Schema(description = "Nombre completo")
	String getNombreCompleto();
	
	@Schema(description = "Nombre completo mas provincia")
	String getCentroProvincia();
}
