package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "CriterioListDto", description = "Entidad para rescatar los criterios de las competencias específicas")
public class CriterioListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id relacion del criterio")
    Long idRelacionCri;

    @Schema(description = "Id del criterio")
    private Long idCriterio;

    @Schema(description = "Descripción")
    private String descripcionCri;

    @Schema(description = "Codigo")
    private String codigoCri;

    @Schema(description = "Porcentaje")
    private Float porcentajeCri;

    @Schema(description = "Peso")
    private Integer pesoCri;
    
    @Schema(description = "Tipo de operación")
    private Long idTipoOperacion;

}
