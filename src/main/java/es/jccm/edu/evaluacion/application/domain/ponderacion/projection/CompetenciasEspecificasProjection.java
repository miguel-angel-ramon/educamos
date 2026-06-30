package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CompetenciasEspecificas", description = "Proyección para rescatar las competencias especificas")
public interface CompetenciasEspecificasProjection {

	@Schema(description = "Id relacion de la competencia")
	Long getIdRelacionCompe();

	@Schema(description = "Id de la competencia")
	Long getIdCompetencia();

	@Schema(description = "Código de la competencia")
	String getCodigoCompe();
	
	@Schema(description = "Descripcion de la competencia")
	String getDescripcionCompe();

	@Schema(description = "Porcentaje de la competencia")
	Float getPorcentajeCompe();

	@Schema(description = "Peso de la competencia")
	Integer getPesoCompe();
	
}
