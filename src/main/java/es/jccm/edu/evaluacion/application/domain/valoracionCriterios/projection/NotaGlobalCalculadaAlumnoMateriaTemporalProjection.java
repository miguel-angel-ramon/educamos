package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Nota global temporal", description = "Nota global calculada temporal del alumno")
public interface NotaGlobalCalculadaAlumnoMateriaTemporalProjection {

	@Schema(description = "Id de la calificación")
	Long getId();

	@Schema(description = "Id materia matrícula del alumno")
	Long getMatMatricula();

	@Schema(description = "Nota global")
	Double getNota();

	@Schema(description = "Descripción de la nota del alumno")
	String getDescCal();

	@Schema(description = "Indica si la nota es aprobada o no")
	String getAprueba();


}
