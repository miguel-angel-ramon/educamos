package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Criterio conv dto", description = "Entidad para rescatar los criterios de una convocatoria")
public class CriteriosConvDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id relacion del criterio")
    Long idRelacionCri;

    @Schema(description = "Id del criterio")
    Long idCriterio;

    @Schema(description = "Descripción")
    String descripcion;

    @Schema(description = "Abreviatura")
    String codigo;

    @Schema(description = "Peso")
    Float peso;

    @Schema(description = "Valoración del alumno")
    String valoracion;

    @Schema(description = "Si el alumno aprueba o no")
    String aprueba;

}
