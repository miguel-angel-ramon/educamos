package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Criterios alumno", description = "Proyección para rescatar los criterios de los alumnos")
public class CriterioAlumnoDto {

    @Schema(description = "Id del criterio")
    Long criEva;

    @Schema(description = "Id del criterio")
    Long idCriterio;

    @Schema(description = "Porcentaje del criterio")
    Float porcentaje;

    @Schema(description = "Id criterio alumno")
    Long idCrialu;

    @Schema(description = "Id de la calificacion")
    Long idCalifica;

    @Schema(description = "Nota del criterio")
    Long nota;

    @Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;

    @Schema(description = "Indica si una nota a sido cambiada")
    Boolean notaChanged = false;
}
