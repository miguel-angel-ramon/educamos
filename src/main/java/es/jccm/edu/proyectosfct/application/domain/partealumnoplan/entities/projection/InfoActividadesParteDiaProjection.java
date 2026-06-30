package es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Proyección de datos sobre actividades y horas relacionadas con un parte de día")
public interface InfoActividadesParteDiaProjection {

    @Schema(description = "Número total de actividades relacionadas")
    Integer getNumeroActividades();

    @Schema(description = "Número total de horas")
    Integer getTotalHoras();

    @Schema(description = "Indica si hay actividades relacionadas (1 para true, 0 para false)")
    Integer getActividadesRelacionadasCheck();
}