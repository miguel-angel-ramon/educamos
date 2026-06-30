package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface GrupoDeAlumnosProjection {
	
	@Schema(description = "Id de grupo de alumnos")
	Long getIdUnidad();
	
	@Schema(description = "Código de grupo de alumnos")
	Long getXOfertaMatrig();
	
	@Schema(description = "Descripción de grupo de alumnos")
	String getDOfertaMatrig();
	
	@Schema(description = "Nombre de grupo de alumnos")
	String getTNombre();

}
