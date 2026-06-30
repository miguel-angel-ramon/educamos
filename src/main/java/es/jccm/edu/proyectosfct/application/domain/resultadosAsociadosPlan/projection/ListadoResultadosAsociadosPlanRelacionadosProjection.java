package es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoResultadosAsociadosPlanRelacionadosProjection", description = "Listado de resultados asociados a módulos en relación")
public interface ListadoResultadosAsociadosPlanRelacionadosProjection {

    @Schema(description = "ID de Resultado Asociado al Módulo")
    Long getId_resultadoa_modulo();

    @Schema(description = "ID de Aprendizaje")
    Long getX_comesp();

    @Schema(description = "Abreviatura")
    String getAbreviatura();

    @Schema(description = "Descripción del Aprendizaje")
    String getDescripcion();
    
    @Schema(description = "Flag que indica si el resultado está asociado a la actividad 1 o 0 en caso contrario")
    Integer getLgres();
}
