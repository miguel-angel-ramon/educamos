package es.jccm.edu.proyectosfct.application.domain.datosgestora.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_DATOS_GESTORA")
public class DatosGestora extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_DATOS_GESTORA")
    @SequenceGenerator(name = "SQ_FCT_DATOS_GESTORA", sequenceName = "SQ_FCT_DATOS_GESTORA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_DATOS_GESTORA")
    private Long idDatosGestora;

    @Column(name = "DS_TIPO")
    private String dsTipo;

    @Column(name = "DS_ESTADO")
    private String dsEstado;

    @Column(name = "CD_ERROR")
    private String cdError;

    @Column(name = "F_ALTA")
    private Date fechaAlta;

    @Column(name = "F_BAJA")
    private Date fechaBaja;

    @Column(name = "LG_ERASMUS_CB")
    private Integer lgErasmusCb;

    @Column(name = "LG_ERASMUS_SB")
    private Integer lgErasmusSb;

    @Column(name = "CD_EMPRESA")
    private String cdEmpresa;

    @Column(name = "F_RECEPCION")
    private Date fechaRecepcion;

    @Column(name = "DS_ERROR_ALTA")
    private String dsErrorAlta;

    @Column(name = "NU_DIAS_PRACTICAS")
    private Integer nuDiasPracticas;

    @Column(name = "DS_TIPO_CONTRATO")
    private String dsTipoContrato;

    @Column(name = "CD_CNUMIDE")
    private String cdCnumide;

    @Column(name = "CD_NUSS")
    private String cdNuss;

    @Column(name = "CD_CUENTA_NUSS")
    private String cdCuentaNuss;

    @Column(name = "CD_GRUPO_NUSS")
    private String cdGrupoNuss;

    @Column(name = "DS_OCUPACION")
    private String DsOcupacion;

    @Column(name = "DS_SITUACION_BAJA")
    private String dsSituacionBaja;

    @Column(name = "LG_ERASMUS")
    private Integer lgErasmus;

    @Column(name = "CD_CIF_EMPRESA")
    private String cdCifEmpresa;

    @Column(name = "ID_GESTORA")
    private Integer idGestora;
    
    @Column(name = "LG_FILE")
    private Integer lgFile;
    
    @Column(name = "LG_DATA")
    private Integer lgData;   

    @Column(name = "ID_USUARIO_ULT")
    private Long idUsuarioUlt;

    @Column(name = "ID_INTERNO_ALTA")
    private Long idInternoAlta;

    @Column(name = "DS_MOTIVO_ERROR_ARCHIVADO")
    private String dsMotivoErrorArchivado;

    @Transient
    private Boolean consistencia;

    @Transient
    private String nombreCompleto;


    // ---------- Relationships -----------

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

    @Column(name = "X_CENTRO")
    private Long xCentro;

    @Column(name = "NU_ANNO")
    private Integer nuAnno;


}
