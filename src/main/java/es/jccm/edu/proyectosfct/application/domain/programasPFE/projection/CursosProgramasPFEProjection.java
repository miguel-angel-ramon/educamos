package es.jccm.edu.proyectosfct.application.domain.programasPFE.projection;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "CursosProgramasPFEProjection", description = "Proyección de cursos de programas PFE")
public interface CursosProgramasPFEProjection {

        @Schema(description = "Identificador ofertamatrig")
        Long getIdOfertamatrig();

        @Schema(description = "Descripcion curso")
        String getCurso();

        @Schema(description = "Identificador orden")
        Long getOrden();

}
