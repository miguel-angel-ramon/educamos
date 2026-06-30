package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "ResultadoAprendizajeEvaluacionDto", description = "Información sobre un resultado de aprendizaje y su evaluación")
public class ResultadoAprendizajeEvaluacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del resultado de aprendizaje")
    private Long resultadoId;

    @Schema(description = "Abreviatura del resultado de aprendizaje")
    private String resultadoAbv;

    @Schema(description = "Nombre del resultado de aprendizaje")
    private String resultadoNombre;

    @Schema(description = "Orden del resultado de aprendizaje")
    private Integer resultadoOrden;

    @Schema(description = "Id de la evaluación asociada al resultado de aprendizaje, -1 si no tiene")
    private Long evaluacionId;

    @Schema(description = "Id de la calificación del resultado de aprendizaje, -1 si no tiene")
    private Integer calificacionId;

    @Schema(description = "Motivación o comentario asociado al resultado de aprendizaje, puede ser null")
    private String motivacion;
}
