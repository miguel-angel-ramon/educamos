package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "CompetenciaAlumnoDTO", description = "DTO Competencias Alumno")
public class CompetenciaAlumnoDTO {

    @Schema(description = "Id de la competencia")
    private Long idCompetencia;

    @Schema(description = "Abreviatura de la competencia")
    String abreviatura;

    @Schema(description = "Descripción de la competencia")
    String descripcion;

    @Schema(description = "porcentaje")
    Float porcentaje;

    @Schema(description = "Id de la competencia del alumno")
    private Long idComAlu;

    @Schema(description = "Id de la calificacion")
    private Long idCalifica;

    @Schema(description = "Nota de la competencia")
    private Long nota;

    @Schema(description = "Descripción de la nota del alumno")
    private String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    private String aprueba;

    @Schema(description = "Indica si una nota a sido cambiada")
    private Boolean notaChanged = false;

    @Schema(description = "Criterios de las competencias")
    private List<CriterioAlumnoDTO> criterios;
}