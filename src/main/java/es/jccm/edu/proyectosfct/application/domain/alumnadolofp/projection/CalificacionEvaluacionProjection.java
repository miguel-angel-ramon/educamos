package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CalificacionEvaluacionProjection", description = "Proyección para obtener las calificaciones disponibles para evaluar los RA")
public interface CalificacionEvaluacionProjection {

    @Schema(description = "Identificador de la calificación")
    Long getCalificacionId();

    @Schema(description = "Abreviatura de la calificación")
    String getCalificacionAbv();

    @Schema(description = "Descripción de la calificación")
    String getCalificacionDesc();
}
