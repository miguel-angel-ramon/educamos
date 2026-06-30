package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosListaActividadesAnexo2PlanProjection", description = "Fuente de datos para la tabla de actividades dentro de la tabla Modulos del Anexo2Plan")
public interface DatosListaActividadesAnexo2PlanProjection {

        @Schema(description = "Descripción de la actividad formativa con los resultados de aprendizaje asociados.")
        String getActividades();

        @Schema(description = "Orden de la actividad.", example = "1")
        Integer getOrden();
    }
