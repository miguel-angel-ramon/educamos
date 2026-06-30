package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ultimo años", description = "Proyección para rescatar los ultimos años")
public interface AnnosProjection {

    @Schema(description = "Años")
    Long getAnno();

    @Schema(description = "Duración del año")
    String getTramo();

    @Schema(description = "Si es el año actual")
    String getAnnoActual();
}
