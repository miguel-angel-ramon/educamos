package es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="FCT_ALTASS_PROG")
public class AltasSegSociProg extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ALTASSPROG")
    @SequenceGenerator(name = "SQ_FCT_ALTASSPROG", sequenceName = "SQ_FCT_ALTASSPROG", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_ALTASS_PROG")
    private Long id;
    
    @Column(name = "LG_VALTUT")
    private Integer lgValTut;

    @Column(name = "F_ENVIOSS")
    private Date fechaEnvio;

    @Column(name = "LG_ANULADO")
    private Integer lgAnulado;
    
    @Column(name = "LG_VALCEN")
    private Integer lgValCen;
    
    @Column(name = "LG_ERASMUS_CB")
    private Integer lgEramsusCb;
    
    @Column(name = "ID_GESTORA")
    private Long idGestora;

    @Column(name = "LG_VALDEL")
    private Integer lgValDel;
    
    @Column(name = "F_INICIO")
    private Date fechaInicio;

    @Column(name = "X_USUARIO_ULT")
    private Long xusuarioUlt;
    
    @Column(name = "LG_ENVIO_EMP")
    private Integer lgEnvioEmp;
    
    @Column(name = "F_FIN")
    private Date fechaFin;
    
    @Column(name = "DS_ERRORS")
    private String errors;
    
    @Column(name = "LG_ERASMUS_SB")
    private Integer lgErasmusSb;

    @Column(name = "DS_WARNINGS")
    private String warnings;

    @Column(name = "ID_INTERNO_ALTA")
    private Long idInternoAlta;
    
    // ---------- Relationships -----------

    @Column(name = "ID_CONVPROG_ALU")
    private Long idConvProgAlu;

    @Column(name = "X_MATRICULA")
    private Long xMatricula;
}
