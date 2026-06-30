package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PonderacionDto", description = "DTO Ponderación")
public class PonderacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la ponderación")
    Long idPonderacion;

    @Schema(description = "Id de la materia")
    Long idMateria;

    @Schema(description = "Id del docente")
    Long idDocente;

    @Schema(description = "Nombre de la materia")
    String nombreMateria;

    @Schema(description = "Indica si una ponderación es editable")
    String editable;

    @Schema(description = "Lista de competencias específicas")
    List<CompetenciasEspecificasDto> competencias;

}
