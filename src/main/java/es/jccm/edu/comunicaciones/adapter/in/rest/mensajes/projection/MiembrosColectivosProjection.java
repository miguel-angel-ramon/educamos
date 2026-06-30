package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MiembrosColectivos")
public interface MiembrosColectivosProjection {
	
	 @Schema(description = "Id del colectivo")
	    Integer getXColectivo();

	    @Schema(description = "Orden dentro del colectivo")
	    Integer getNOrden();

	    @Schema(description = "Código del tipo de parámetro en colectivo")
	    String getCTipParmEnCol();

	    @Schema(description = "Descripción del tipo de parámetro en colectivo")
	    String getDTipParmEnCol();

	    @Schema(description = "Sentencia SQL asociada")
	    String getTSentencia();

	    @Schema(description = "Sentencia SQL continua asociada")
	    String getTSentenciaContinua();

}
