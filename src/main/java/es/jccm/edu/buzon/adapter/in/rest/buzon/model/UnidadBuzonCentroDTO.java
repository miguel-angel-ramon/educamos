package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidad de evaluación", description = "Proyección para rescatar las unidades de evaluación")
public class UnidadBuzonCentroDTO {

    @Schema(description = "Id de la unidad")
    Long idUnidad;

    @Schema(description = "Id del curso")
    Long idCurso;
    
    @Schema(description = "Descripción de la unidad")
    String unidad;
	
    @Schema(description = "Descripción de la curso")
	String curso;

    @Schema(description = "Id de la etapa secundario para bachiller")
    Long idEtapaSec;
    
    @Schema(description = "Id de la etapa")
	Long idEtapa;

    @Schema(description = "Id del ciclo")
    Long idCiclo;

    @Schema(description = "Id de la ponderacion")
    Long idPonderacion;
    
    @Schema(description = "Descripción de la etapa")
	String etapa;
    
    @Schema(description = "Id de la oferta de matrícula genérica")
    Long idOfertamatrig;

}
