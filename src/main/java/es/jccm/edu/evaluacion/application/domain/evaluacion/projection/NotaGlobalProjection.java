package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Nota Global", description = "Proyección para rescatar las notas globales")
public interface NotaGlobalProjection {

    @Schema(description = "Id nota global del alumno")
    Long getIdNotAlu();

    @Schema(description = "Id de la calificación")
    Long getIdCalifica();

    @Schema(description = "Nota de la competencia por alumno")
    Float getNota();

    @Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();

}
