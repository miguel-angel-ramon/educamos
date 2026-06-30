package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Punto Partida", description = "Proyección para rescatar los puntos de partida de detalle de Ámbito")
public interface PuntoPartidaProjection {



	@Schema(description = "Id de la opción seleccionada por el usuario")
	Long getIdOpcion();

	@Schema(description = "Descripción de la competencia")
	String getDesCompetencia();

	@Schema(description = "Descripción de la pregunta")
	String getDesPregunta();

	@Schema(description = "Descripción de la opción seleccionada")
	String getDesOpcion();






	
}

