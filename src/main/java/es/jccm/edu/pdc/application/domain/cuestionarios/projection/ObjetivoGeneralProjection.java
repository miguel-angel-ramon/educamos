package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Objetivos Generales", description = "Proyección para rescatar los objetivos generales")
public interface ObjetivoGeneralProjection {




	@Schema(description = "Id de la competencia")
	Long getIdCompetencia();

	@Schema(description = "Código de la competencia")
	Long getCodCompetencia();

	@Schema(description = "Descripción de la Competencia")
	String getDesCompetencia();

	@Schema(description = "Título de la Competencia")
	String getTituloCompetencia();

	@Schema(description = "Id de la subdimensión")
	Long getIdSubDimension();

	@Schema(description = "Id del objetivo")
	Long getIdObjetivo();

	@Schema(description = "Id del grupo de objetivos")
	Long getIdGrupoObjetivo();

	@Schema(description = "Tipo de objetivo")
	Long getTipObjetivo();

	@Schema(description = "Activo")
	String getActivo();

	@Schema(description = "Descripción del objetivo")
	String getDesObjetivo();





	
}

