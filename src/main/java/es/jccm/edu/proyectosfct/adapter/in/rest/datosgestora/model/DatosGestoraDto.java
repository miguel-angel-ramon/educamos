package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestora.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "DatosGestoraDto", description = "Datos de la Seguridad Social de alumnos por centro")
public class DatosGestoraDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "dsTipo")
    private String dsTipo;

    @Schema(name = "dsEstado")
    private String dsEstado;

    @Schema(name = "cdError")
    private String cdError;

    @Schema(name = "fechaAlta")
    private Date fechaAlta;

    @Schema(name = "fechaBaja")
    private Date fechaBaja;

    @Schema(name = "lgErasmusCb")
    private Integer lgErasmusCb;

    @Schema(name = "lgErasmusSb")
    private Integer lgErasmusSb;

    @Schema(name = "cdEmpresa")
    private String cdEmpresa;

    @Schema(name = "fechaRecepcion")
    private Date fechaRecepcion;

    @Schema(name = "dsErrorAlta")
    private String dsErrorAlta;

    @Schema(name = "nuDiasPracticas")
    private Integer nuDiasPracticas;

    @Schema(name = "dsTipoContrato")
    private String dsTipoContrato;

    @Schema(name = "consistencia")
    private Boolean consistencia;

    @Schema(name = "cdCnumide")
    private String cdCnumide;

    @Schema(name = "cdNuss")
    private String cdNuss;

    @Schema(name = "nombreCompleto")
    private String nombreCompleto;

    @Schema(name = "cdCuentaNuss")
    private String cdCuentaNuss;

    @Schema(name = "cdGrupoNuss")
    private String cdGrupoNuss;

    @Schema(name = "DsOcupacion")
    private String DsOcupacion;

    @Schema(name = "dsSituacionBaja")
    private String dsSituacionBaja;

    @Schema(name = "lgErasmus")
    private Integer lgErasmus;

    @Schema(name = "cdCifEmpresa")
    private String cdCifEmpresa;

    @Schema(name = "idGestora")
    private Integer idGestora;
    
    @Schema(name = "lgFile")
    private Integer lgFile;
    
    @Schema(name = "lgData")
    private Integer lgData;

    @Schema(name = "idInternoAlta")
    private Long idInternoAlta;
    
}
