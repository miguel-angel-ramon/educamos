package es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_ALTASS_PROY")
public class AltasSegSociProy extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ALTASSPROY")
    @SequenceGenerator(name = "SQ_FCT_ALTASSPROY", sequenceName = "SQ_FCT_ALTASSPROY", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_ALTASS_PROY")
    private Long id;

    @Column(name = "LG_VALCEN")
    private Integer lgValCen;

    @Column(name = "LG_ERASMUS_SB")
    private Integer lgErasmusSb;
    
    @Column(name = "LG_VALDEL")
    private Integer lgValDel;
    
    @Column(name = "LG_ERASMUS_CB")
    private Integer lgEramsusCb;

    @Column(name = "X_USUARIO_ULT")
    private Long xusuarioUlt;
    
    @Column(name = "LG_ENVIO_EMP")
    private Integer lgEnvioEmp;
    
    @Column(name = "F_FIN")
    private Date fechaFin;
    
    @Column(name = "LG_VALTUT")
    private Integer lgValTut;
    
    @Column(name = "F_ENVIOSS")
    private Date fechaEnvio;
    
    @Column(name = "F_INICIO")
    private Date fechaInicio;

    @Column(name = "LG_ANULADO")
    private Integer lgAnulado;

    @Column(name = "DS_WARNINGS")
    private String warnings;

    @Column(name = "DS_ERRORS")
    private String errors;
    
    @Column(name = "ID_GESTORA")
    private Long idGestora;

    @Column(name = "ID_INTERNO_ALTA")
    private Long idInternoAlta;

    // ---------- Relationships -----------

    @Column(name = "ID_CONVPROY_ALU")
    private Long idConvProyAlu;

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

}
