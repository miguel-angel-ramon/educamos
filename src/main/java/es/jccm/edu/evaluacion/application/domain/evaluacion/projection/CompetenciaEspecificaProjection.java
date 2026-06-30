package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Competencias especificas", description = "Proyección para rescatar las competencias especificas")
public interface CompetenciaEspecificaProjection {

    @Schema(description = "Materia de la competencia")
    String getMateria();

    @Schema(description = "Abreviatura competencia especifica")
    String getCompetenciaEspAbrev();

    @Schema(description = "Id competencia especifica")
    Long getIdCompetenciaEsp();

    @Schema(description = "Descripción de competencia especifica")
    String getCompetenciaEspD();

    @Schema(description = "Abreviatura de la calificacion de la competencia")
    String getValoracion();
    
    @Schema(description = "Indica con 'S' o 'N' si aprueba con la calificacion")
    String getAprueba();

}
