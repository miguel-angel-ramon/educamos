package es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities;

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
@Table(name = "FCT_COTIZAMES_PROY")
public class CotizaMesProyectos extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NU_MES")
    private Integer nuMes;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacu;

    @Column(name = "DS_WARNINGS")
    private String warnings;

    @Column(name = "LG_VALTUT")
    private Integer lgValTut;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasReal;

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

    @Column(name = "ID_CONVPROY_ALU")
    private Long idConvProyAlu;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEra;

    @Column(name = "LG_VALDEL")
    private Integer lgValDel;

    @Column(name = "ID_COTIZAMES_PROY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_COTIZAMESPROY")
    @SequenceGenerator(name = "SQ_FCT_COTIZAMESPROY", sequenceName = "SQ_FCT_COTIZAMESPROY", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Id
    private Long idCotizaMes;

    @Column(name = "LG_VALCEN")
    private Integer lgValCen;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInte;

    @Column(name = "X_USUARIO_ULT")
    private Long xusuarioUlt;
    
    @Column(name = "LG_ENVIO_EMP")
    private Integer lgEnvioEmp;
    
    @Column(name = "F_ENVIOSS")
    private Date fechaEnvio;

}
