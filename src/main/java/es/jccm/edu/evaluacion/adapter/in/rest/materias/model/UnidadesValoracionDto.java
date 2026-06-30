package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Unidades valoracion", description = "Proyección para rescatar las unidades de valoración")
public class UnidadesValoracionDto {

    @Schema(description = "Id de la unidad")
    Long idUnidad;

    @Schema(description = "Descripción de la unidad")
    String unidad;

    @Schema(description = "si tiene programacion de aula")
    Long progAula;
}
