package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Competencia alumno", description = "Proyección para rescatar las competencias de los alumnos")
public class CompetenciaAlumnoDto {

    @Schema(description = "Id de la competencia")
    Long comEsp;

    @Schema(description = "Id de la competencia")
    Long idCompetencia;

    @Schema(description = "porcentaje")
    Float porcentaje;

    @Schema(description = "Id de la competencia del alumno")
    Long idComAlu;

    @Schema(description = "Id de la calificacion")
    Long idCalifica;

    @Schema(description = "Nota de la competencia")
    Long nota;

    @Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;

    @Schema(description = "Indica si una nota a sido cambiada")
    Boolean notaChanged = false;

    @Schema(description = "Criterios de las competencias")
    List<CriterioAlumnoDto> criterios;
}
