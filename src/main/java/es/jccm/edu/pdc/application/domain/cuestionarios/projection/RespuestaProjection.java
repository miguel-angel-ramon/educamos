package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Respuesta", description = "Proyección para rescatar las respuestas de una pregunta")
public interface RespuestaProjection {
	
	@Schema(description = "Id de la respuesta")
	Long getIdRespuesta();
	
	@Schema(description = "Nombre de la respuesta")
	String getNombre();
	
	@Schema(description = "Valor de la respuesta")
	String getValor();

	@Schema(description = "Orden de la respuesta")
	Integer getOrden();
	
}

