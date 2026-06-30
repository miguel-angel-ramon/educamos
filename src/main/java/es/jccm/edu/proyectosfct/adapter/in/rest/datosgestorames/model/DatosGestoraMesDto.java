package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestorames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "DatosGestoraMesDto", description = "Datos de la Seguridad Social de alumnos por mes")
public class DatosGestoraMesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "dsEstado")
    private String dsEstado;

    @Schema(name = "nuDiasNacu")
    private Integer nuDiasNacu;

    @Schema(name = "nuDiasInte")
    private Integer nuDiasInte;

    @Schema(name = "nuDiasReal")
    private Integer nuDiasReal;

    @Schema(name = "nuDiasInteEra")
    private Integer nuDiasInteEra;

    @Schema(name = "nuAnno")
    private Integer nuAnno;

    @Schema(name = "cdCnumide")
    private String cdCnumide;

    @Schema(name = "consistencia")
    private Boolean consistencia;

    @Schema(name = "nuMes")
    private Integer nuMes;

    @Schema(name = "idGestora")
    private Long idGestora;

    @Schema(name = "cdNuss")
    private String cdNuss;

    @Schema(name = "nombreCompleto")
    private String nombreCompleto;

}
