package es.jccm.edu.proyectosfct.application.domain.proyectos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Listado de anio y centro", description = "Campos para el anio y el id de centro")
public interface ListadoAnioCentroProjection {

    @Schema(description = "anio")
    Long getAnio();

    @Schema(description = "id del centro")
    Long getIdCentro();

}
