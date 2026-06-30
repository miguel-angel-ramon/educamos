package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Punto Partida", description = "Proyección para rescatar los puntos de partida de detalle de Ámbito")
public interface SugerenciaProjection {



	@Schema(description = "Código de la competencia")
	String getCodCompetencia();

	@Schema(description = "Título de la competencia")
	String getTituloCompetencia();

	@Schema(description = "Descripción de la competencia")
	String getDesCompetencia();

	@Schema(description = "Identificador de la competencia")
	Long getIdCompetencia();

	@Schema(description = "Identificador de la subdimensión")
	Long getIdSubDimension();

	@Schema(description = "Identificador de la sugerencia")
    Long getIdSugerencia();

	@Schema(description = "Identificador del sector")
	Long getIdSector();

	@Schema(description = "Identificador de la sugerencia nivel")
	Long getIdSugNiv();

	@Schema(description = "Identificador del nivel")
	Long getIdNivel();

	@Schema(description = "Descripción de cómo mejorar")
	String getComoMejorar();

	@Schema(description = "Descripción de la sugerencia")
	String getDesSugerencia();







	
}

