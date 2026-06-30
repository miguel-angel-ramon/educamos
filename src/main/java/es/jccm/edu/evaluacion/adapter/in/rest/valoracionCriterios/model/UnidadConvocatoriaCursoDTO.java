package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Tupla de unidades, convocatorias y cursos", description = "Tupla de unidades, convocatorias y cursos")
public class UnidadConvocatoriaCursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la unidad")
    private Long idUnidad;

    @Schema(description = "Id de la convocatoria")
    private Long idConvocatoria;

    @Schema(description = "Id de la oferta matrícula")
    private Long idOfertamatrig;

}
