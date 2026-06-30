package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Sistema de calificación", description = "Proyección para rescatar las notas posibles según la etapa")
public interface SistemaCalificacionProjection {

    @Schema(description = "Id calificación")
    Long getIdCalifica();

    @Schema(description = "Id sistema de calificación")
    Long getIdSistCal();

    @Schema(description = "Nota seleccionable")
    Long getNota();

    @Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Si la nota es aprobada o no")
    String getAprueba();

    @Schema(description = "Descripción sistema de calificación")
    String getDescripcion();

    @Schema(description = "Descripción abreviada sistema de calificación")
    String getDescSis();

    @Schema(description = "Indica si el sistema de calificación es numérico")
    Boolean getNumerico();

}
