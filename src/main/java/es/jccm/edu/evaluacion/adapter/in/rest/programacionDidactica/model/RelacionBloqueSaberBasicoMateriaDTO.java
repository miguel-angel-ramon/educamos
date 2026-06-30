package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "EvaRelacionBloqueSaberBasicoMateriaDTO", description = "EvaRelacionBloqueSaberBasicoMateriaDTO")
public class RelacionBloqueSaberBasicoMateriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Materia")
    private Long idMateriaOmg;

    @Schema(description = "Bloque saber basico")
    private BloqueSaberBasicoDTO bloqueSaberBasico;
}
