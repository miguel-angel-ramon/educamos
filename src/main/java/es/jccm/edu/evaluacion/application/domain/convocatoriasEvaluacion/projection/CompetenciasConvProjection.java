package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Competencias convocatoria", description = "Proyección para rescatar las competencias de una convocatoria")
public interface CompetenciasConvProjection {

	@Schema(description = "Id relacion de la competencia")
	Long getIdRelacionCompe();

	@Schema(description = "Id de la competencia")
	Long getIdCompetencia();

	@Schema(description = "Código de la competencia")
	String getCodigo();
	
	@Schema(description = "Descripcion de la competencia")
	String getDescripcion();

	@Schema(description = "Porcentaje de la competencia")
	Float getPeso();
	
}
