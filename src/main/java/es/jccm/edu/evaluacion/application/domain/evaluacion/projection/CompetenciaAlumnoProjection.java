package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Competencia alumno", description = "Proyección para rescatar las competencias de los alumnos")
public interface CompetenciaAlumnoProjection {

    @Schema(description = "Id de la competencia")
    Long getComEsp();

    @Schema(description = "Id de la competencia")
    Long getIdCompetencia();

    @Schema(description = "Abreviatura de la competencia")
    String getAbreviatura();

    @Schema(description = "Descripción de la competencia")
    String getDescripcion();

    @Schema(description = "Porcentaje de la competencia")
    Float getPorcentaje();
}
