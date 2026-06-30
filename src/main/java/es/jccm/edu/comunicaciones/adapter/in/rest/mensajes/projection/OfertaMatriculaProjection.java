package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface OfertaMatriculaProjection {
	
	@Schema(description = "Código de oferta matrícula")
	Long getXOfertaMatrig();
	
	@Schema(description = "Modalidad de oferta matrícula")
	Long getXModalidad();
	
	@Schema(description = "Descripción de oferta matrícula")
	String getDOfertaMatrig();

}
