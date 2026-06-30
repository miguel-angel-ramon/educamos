package es.jccm.edu.buzon.application.domain.buzonCentro.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidad de evaluación", description = "Proyección para rescatar las unidades de evaluación")
public interface UnidadBuzonCentroProjection {

    @Schema(description = "Id de la oferta de matrícula genérica")
    Long getIdOfertamatrig();

    @Schema(description = "Id de la unidad")
    Long getIdUnidad();

    @Schema(description = "Descripción de la curso")
    String getCurso();

    @Schema(description = "Descripción de la unidad")
    String getUnidad();

    @Schema(description = "Id de la etapa")
    Long getIdEtapa();

    @Schema(description = "Id del ciclo")
    Long getIdCiclo();

    @Schema(description = "Id de la ponderacion")
    Long getIdPonderacion();

    @Schema(description = "Id de la etapa secundario para bachiller")
    Long getIdEtapaSec();

    @Schema(description = "Descripción de la etapa")
    String getEtapa();

    @Schema(description = "Id del curso")
    Long getIdCurso();

}
