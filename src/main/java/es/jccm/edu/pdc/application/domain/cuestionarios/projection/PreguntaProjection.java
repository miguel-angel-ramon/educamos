package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Preguntas", description = "Proyección para rescatar los datos de una pregunta")
public interface PreguntaProjection {
	
	@Schema(description = "Id de la pregunta")
	Long getIdPregunta();
	
	@Schema(description = "Nombre de la pregunta")
	String getTitulo();
	
	@Schema(description = "Descripción de la pregunta")
	String getDescripcion();

	@Schema(description = "Tipo de la pregunta")
	Long getOrden();

	@Schema(description = "Tipo de la pregunta")
	String getObligatoria();

	@Schema(description = "Tipo de la pregunta")
	String getPermiteTexto();

	@Schema(description = "Tipo de la pregunta")
	String getCodTipo();

	@Schema(description = "Tipo de la pregunta")
	String getTipo();

}

