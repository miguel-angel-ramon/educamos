package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Información sobre actividades relacionadas con un parte de día")
public class InfoActividadesParteDiaDto {

    @Schema(description = "Número total de actividades relacionadas")
    private Integer numeroActividades;

    @Schema(description = "Número total de horas")
    private Integer totalHoras;

    @Schema(description = "Indica si hay actividades relacionadas (1 para true, 0 para false)")
    private Integer actividadesRelacionadasCheck;
}