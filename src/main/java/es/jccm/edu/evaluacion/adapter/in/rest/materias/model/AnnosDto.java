package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ultimo años", description = "Proyección para rescatar los ultimos años")
public class AnnosDto {

    @Schema(description = "Años")
    Long anno;

    @Schema(description = "Duración del año")
    String tramo;

    @Schema(description = "Si es el año actual")
    String annoActual;
}
