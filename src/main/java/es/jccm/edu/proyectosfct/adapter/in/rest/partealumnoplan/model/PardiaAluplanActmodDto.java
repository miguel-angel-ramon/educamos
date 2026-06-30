package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "PardiaAluplanActmodDto", description = "Información sobre la relación entre parte diario y actividades del módulo")
public class PardiaAluplanActmodDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador único de la relación entre parte diario y actividad del módulo")
    private Long idPardiaAluplanActmod;

    @Schema(description = "ID del parte diario asociado")
    private Long idPardiaAluplan;

    @Schema(description = "ID de la actividad del módulo asociada")
    private Long idActividadModulo;
}
