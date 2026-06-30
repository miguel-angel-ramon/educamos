package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Dto programación aula virtual", description = "DTO rescatar los datos de las programaciones de las aulas virtuales")
public class ProgramacionAulaVirtualDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id programación de aula")
    Long idProgAula;

    @Schema(description = "Id programación didáctica")
    Long idProgDidac;

    @Schema(description = "Nombre de la programación")
    String nombre;

    @Schema(description = "Url de la programación de aula a evaluación")
    String url;

    @Schema(description = "Id aula virtual")
    Long idAula;
    
}
