package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidad de evaluación", description = "Proyección para rescatar las unidades de evaluación")
public interface UnidadEvaluacionProjection {

    @Schema(description = "Id de la unidad")
    Long getIdUnidad();

    @Schema(description = "Descripción de la unidad")
    String getUnidad();
    
    @Schema(description = "Id del curso")
    Long getIdCurso();

    @Schema(description = "Descripción de la curso")
    String getCurso();
    
    @Schema(description = "Id de la etapa")
    Long getIdEtapa();

    @Schema(description = "Id de la etapa secundario para bachiller")
    Long getIdEtapaSec();

    @Schema(description = "Id del ciclo")
    Long getIdCiclo();
    
    @Schema(description = "Descripción de la etapa")
    String getEtapa();

    @Schema(description = "Id de la ponderacion")
    Long getIdPonderacion();
    
    @Schema(description = "Id de la oferta de matrícula genérica")
    Long getIdOfertamatrig();

    @Schema(description = "Id de la oferta de matrícula genérica")
    Long getIdOfertamatric();

    @Schema(description = "Número de Competencias Específicas evaluadas en la unidad")
    Integer getCompetenciasEvaluadas();

    @Schema(description = "Nombre de las materias con competencias específicas sin evaluar a algun alumno")
    String getNombreMaterias();
}
