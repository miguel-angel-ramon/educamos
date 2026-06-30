package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CompetenciasEspecificasDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PonderacionDTO", description = "DTO Ponderación")
public class PonderacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la ponderación")
    private Long idPonderacion;

    @Schema(description = "Id de la materia")
    private Long idMateria;

    @Schema(description = "Id del docente")
    private Long idDocente;

    @Schema(description = "Nombre de la materia")
    private String nombreMateria;

    @Schema(description = "Indica si una ponderación es editable")
    private String editable;

    @Schema(description = "Lista de competencias específicas")
    private List<CompetenciasEspecificasDTO> competencias;
}