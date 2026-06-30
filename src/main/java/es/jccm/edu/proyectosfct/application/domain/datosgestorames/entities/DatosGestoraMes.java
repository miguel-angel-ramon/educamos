package es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities;

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

@Data
@Entity
@Table(name = "FCT_DATOS_GESTORA_MES")
public class DatosGestoraMes extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_DATOS_GESTORA_MES")
    @SequenceGenerator(name = "SQ_FCT_DATOS_GESTORA_MES", sequenceName = "SQ_FCT_DATOS_GESTORA_MES", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_DATOS_GESTORA_MES")
    private Long idDatosGestoraMes;

    @Column(name = "DS_ESTADO")
    private String dsEstado;

    @Column(name = "CD_ERROR")
    private String cdError;

    @Column(name = "CD_CNUMIDE")
    private String cdCnumide;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacu;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInte;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasReal;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEra;

    @Column(name = "NU_MES")
    private Integer nuMes;

    @Column(name = "ID_GESTORA")
    private Integer idGestora;

    @Column(name = "CD_NUSS")
    private String cdNuss;

    @Column(name = "ID_USUARIO_ULT")
    private Long idUsuarioUlt;

    @Column(name = "LG_ERASMUS")
    private Integer lgErasmus;

    @Transient
    private boolean consistencia;

    @Transient
    private String nombreCompleto;


    // ---------- Relationships -----------

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

    @Column(name = "X_CENTRO")
    private Long idCentro;

    @Column(name = "NU_ANNO")
    private Integer nuAnno;



}
