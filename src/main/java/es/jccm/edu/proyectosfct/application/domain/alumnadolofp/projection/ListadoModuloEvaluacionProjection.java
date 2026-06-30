package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoModuloEvaluacionProjection", description = "Proyección para obtener la lista de alumnos con evaluación LOFP")
public interface ListadoModuloEvaluacionProjection {

    @Schema(description = "Id del módulo")
    Long getModuloId();

    @Schema(description = "Nombre del módulo")
    String getModuloNombre();

    @Schema(description = "Código del módulo")
    Long getModuloCodigo();

    @Schema(description = "Orden del módulo")
    Integer getModuloOrden();

    @Schema(description = "Id de matrícula del módulo")
    Long getModuloMatriculaId();

    @Schema(description = "Id del resultado de aprendizaje")
    Long getResultadoId();

    @Schema(description = "Abreviatura del resultado de aprendizaje")
    String getResultadoAbv();

    @Schema(description = "Nombre del resultado de aprendizaje")
    String getResultadoNombre();

    @Schema(description = "Orden del resultado de aprendizaje")
    Integer getResultadoOrden();

    @Schema(description = "Id de la evaluación asociada al resultado de aprendizaje, -1 si no tiene")
    Long getEvaluacionId();

    @Schema(description = "Id de la calificación del resultado de aprendizaje, -1 si no tiene")
    Integer getCalificacionId();

    @Schema(description = "Motivación/Observación asociada al resultado de aprendizaje")
    String getMotivacion();
}
