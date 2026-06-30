package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidades valoracion", description = "Proyección para rescatar las unidades de valoración")
public interface UnidadesValoracionProjection {

    @Schema(description = "Id de la unidad")
    Long getIdUnidad();

    @Schema(description = "Descripción de la unidad")
    String getUnidad();

    @Schema(description = "Id de la ponderacion")
    Long getIdPonderacion();

    @Schema(description = "Id de la programación didáctica")
    Long getIdProgramacionDidactica();

    @Schema(description = "Id de la programación didáctica")
    Long getProgAula();
}
