package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "ListadoCalificacionEvaluacionDto", description = "Información sobre las calificaciones de los RA")
public class CalificacionEvaluacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador de la calificación")
    private Long calificacionId;

    @Schema(description = "Abreviatura de la calificación")
    private String calificacionAbv;

    @Schema(description = "Descripción de la calificación")
    private String calificacionDesc;
}
