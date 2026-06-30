package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Dupla de unidades y convocatorias", description = "Dupla de unidades y convocatorias")
public class UnidadConvocatoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la unidad")
    private Long idUnidad;

    @Schema(description = "Id de la convocatoria")
    private Long idConvocatoria;

}
