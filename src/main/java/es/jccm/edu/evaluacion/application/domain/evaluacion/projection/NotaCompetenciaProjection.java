package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Notas competencias", description = "Proyección para rescatar las notas de las competencias")
public interface NotaCompetenciaProjection {

    @Schema(description = "Id competencia del alumno")
    Long getIdComAlu();

    @Schema(description = "Id de la calificación")
    Long getIdCalifica();

    @Schema(description = "Nota de la competencia por alumno")
    String getNota();

    @Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();

}
