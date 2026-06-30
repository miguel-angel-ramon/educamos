package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(name = "ListadoResultadosAsociadosPlanRelacionadosDto", description = "DTO para el listado de resultados asociados a módulos en relación")
public class ListadoResultadosAsociadosPlanRelacionadosDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID de Resultado Asociado al Módulo")
    private Long id_resultadoa_modulo;

    @Schema(description = "ID de Aprendizaje")
    private Long x_comesp;

    @Schema(description = "Abreviatura")
    private String abreviatura;

    @Schema(description = "Descripción del Aprendizaje")
    private String descripcion;
    
    @Schema(description = "Flag que indica si el resultado está asociado a la actividad")
    private Integer lgres;
}
