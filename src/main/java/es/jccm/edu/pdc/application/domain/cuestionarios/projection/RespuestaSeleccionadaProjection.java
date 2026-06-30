package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.rest.core.config.Projection;

@Schema(name = "RespuestaSeleccionada", description = "Proyección para rescatar las respuestas seleccionadas del usuario")
public interface RespuestaSeleccionadaProjection {

	@Schema(description = "Id cuestionario del usuario")
	Long getIdCuePubUsu();

	@Schema(description = "Id de la pregunta")
	Long getIdPregunta();

	@Schema(description = "Id de la respuesta")
	Long getIdRespuesta();

	@Schema(description = "Texto de la respuesta")
	String getTextoRespuesta();
	
}

