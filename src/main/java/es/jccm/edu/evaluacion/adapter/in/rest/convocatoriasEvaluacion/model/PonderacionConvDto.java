package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Ponderacion", description = "Proyección para rescatar la ponderación de una convocatoria")
public class PonderacionConvDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la ponderación")
    Long idPonderacion;

    @Schema(description = "Lista de competencias conv")
    List<CompetenciasConvDto> competencias;

}
