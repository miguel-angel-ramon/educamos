package es.jccm.edu.proyectosfct.application.domain.programasPFE.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "FCT_PROGPERFOR")
public class ProgramasPFE extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_PROGPERFOR")
    @SequenceGenerator(name = "SQ_FCT_PROGPERFOR", sequenceName = "SQ_FCT_PROGPERFOR", allocationSize = 1)
    @Column(name = "ID_PROGPERFOR")
    private Long id;

    @Column(name = "X_OFERTAMATRIG", nullable = false)
    private Long idCurso;

    @Column(name = "X_CENTRO", nullable = false)
    private Long idCentro;

    @Column(name = "C_ANNO", nullable = false)
    private Integer anno;
    
    @Column(name = "NU_ANNO_DESDE", nullable = false)
    private Integer nuAnnoDesde;

    @Column(name = "NU_ANNO_HASTA")
    private Integer nuAnnoHasta;

    @Column(name = "LG_ACTIVO")
    private Integer lgActivo;

    @Column(name = "LG_ALCANCE", nullable = false)
    private Integer lgAlcance;

    @Column(name = "NU_ALUMNADO", nullable = false)
    private Integer nuAlumnado;

    @Column(name = "LG_MODALIDAD", nullable = false)
    private Integer lgModalidad;

    @Column(name = "LG_AMPLIACION")
    private Integer lgAmpliacion;

    @Column(name = "NU_HORAS_FPE")
    private Integer nuHorasFpe;

    @Column(name = "NU_HORAS_CUR")
    private Integer nuHorasCur;

    @Column(name = "LG_DIARIO")
    private Integer lgDiario;
    
    @Column(name = "LG_SEMANAL")
    private Integer lgSemanal;
    
    @Column(name = "LG_MENSUAL")
    private Integer lgMensual;
    
    @Column(name = "LG_OTROS")
    private Integer lgOtros;
    
    @Column(name = "NU_HORAS_COMFPE")
    private Integer nuHorasComFpe;

    @Column(name = "NU_HORAS_COMCUR")
    private Integer nuHorasComCur;

    @Column(name = "DS_COO_MODPROF")
    private String dsCooModProf;

    @Column(name = "DS_EMP_ORGCOL")
    private String dsEmpOrgCol;

    @Column(name = "DS_CAR_CONALT")
    private String dsCarConAlt;

    @Column(name = "DS_CON_BECA")
    private String dsConBeca;

    @Column(name = "LG_AUTORIZACION")
    private Integer lgAutorizacion;

    @Column(name = "LG_AUT_400")
    private Integer lgAut400;

    @Column(name = "LG_AUT_FORCOM")
    private Integer lgAutForcom;

    @Column(name = "LG_AUT_CURESP")
    private Integer lgAutCurEsp;

    @Column(name = "LG_AUT_3CUR")
    private Integer lgAut3Cur;

    @Column(name = "LG_AUT_CAMREG")
    private Integer lgAutCamReg;

    @Column(name = "LG_AUT_PROENS")
    private Integer lgAutProEns;
    
    @Column(name = "C_USU_PROG")
    private Long cUsuProg;
    
    @Transient
    private Integer lgCurso;    
    
    @Transient
    private Integer puedeModificar;
    
    @Transient
    private String dsCurso;
    

}

