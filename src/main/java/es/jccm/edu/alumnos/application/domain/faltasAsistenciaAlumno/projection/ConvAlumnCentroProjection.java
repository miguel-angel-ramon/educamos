package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Convocatoria", description = "Proyección para rescatar los datos de las Convocatorias")

public interface ConvAlumnCentroProjection {

    @Schema(description = "Id Convocatoria")
    Long getIndice();

    @Schema(description = "Descripcion")
    String getDescripcion();

    @Schema(description = "fecha inicio Convocatoria")
    String getFecinicon();

    @Schema(description = "fecha fin Convocatoria")
    String getFecfincon();
}
