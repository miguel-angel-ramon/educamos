package es.jccm.edu.proyectosfct.application.domain.programasPFE.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CombosProgramasPFEProjection {

    @Schema(description = "Identificador único del combo)")
    Long getId();

    @Schema(description = "Código identificativo o descripción del combo")
    String getDescripcion();


}
