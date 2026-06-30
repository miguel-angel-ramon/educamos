package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Colectivos")
public interface ColectivosProjection {
	
	@Schema(description = "Id del colectivo")
	Long getId();
	
	@Schema(description = "Código del colectivo")
	String getCodigoColectivo();
	
	@Schema(description = "Descripción corta del colectivo")
	String getDescripcionCortaColectivo();
	
	@Schema(description = "Descripción larga del colectivo")
	String getDescripcionLargaColectivo();
	
	@Schema(description = "Permite filtrar al colectivo")
	String getPermiteFiltrar();
	
	@Schema(description = "Permite respuesta al colectivo")
	String getPermiteRespuesta();
	
	@Schema(description = "Perfil")
	Long getXPerfil();

}
