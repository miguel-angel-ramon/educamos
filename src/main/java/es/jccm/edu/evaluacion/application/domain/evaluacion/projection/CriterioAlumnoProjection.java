package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Criterios alumno", description = "Proyección para rescatar los criterios de los alumnos")
public interface CriterioAlumnoProjection {

    @Schema(description = "Id de los criterios")
    Long getCriEva();

    @Schema(description = "Id de los criterios")
    Long getIdCriterio();

    @Schema(description = "Abreviatura del criterio")
    String getAbreviatura();

    @Schema(description = "Descripción del criterio")
    String getDescripcion();

    @Schema(description = "Porcentaje de los criterios")
    Float getPorcentaje();

    @Schema(description = "Id de la operación del criterio")
    Long getIdTipoOperacion();

    @Schema(description = "Nombre de la operación del criterio")
    String getNombreTipoOperacion();
}
