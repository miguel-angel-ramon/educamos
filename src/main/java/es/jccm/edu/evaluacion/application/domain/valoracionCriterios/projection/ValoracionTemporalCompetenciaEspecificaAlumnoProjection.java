package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Nota global temporal", description = "Nota global calculada temporal del alumno")
public interface ValoracionTemporalCompetenciaEspecificaAlumnoProjection {

	@Schema(description = "Id de la competencia específica")
	Long getComEsp();

	@Schema(description = "Id de la nota temporal de la compentencia específica")
	Long getIdCompetencia();

	@Schema(description = "id de la MatMatricula")
	Long getMatMatricula();

	@Schema(description = "id de la Ponderacion")
	Long getIdPonderacion();

	@Schema(description = "Descripción corta de la calificación")
	String getDescCal();

	@Schema(description = "Indica si la nota es aprobada o no")
	String getAprueba();

	@Schema(description = "nota")
	String getNota();

}
