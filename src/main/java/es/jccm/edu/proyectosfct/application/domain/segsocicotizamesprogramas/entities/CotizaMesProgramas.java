package es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities;

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
@Table(name = "FCT_COTIZAMES_PROG")
public class CotizaMesProgramas extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasReal;

    @Column(name = "LG_VALDEL")
    private Integer lgValDel;

    @Column(name = "ID_CONVPROG_ALU")
    private Long idConvProgAlu;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacu;

    @Column(name = "DS_WARNINGS")
    private String warnings;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInte;

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

    @Column(name = "ID_COTIZAMES_PROG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_COTIZAMESPROG")
    @SequenceGenerator(name = "SQ_FCT_COTIZAMESPROG", sequenceName = "SQ_FCT_COTIZAMESPROG", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Id
    private Long idCotizaMes;

    @Column(name = "NU_MES")
    private Integer nuMes;

    @Column(name = "X_USUARIO_ULT")
    private Long xusuarioUlt;

    @Column(name = "LG_VALCEN")
    private Integer lgValCen;

    @Column(name = "LG_VALTUT")
    private Integer lgValTut;
    
    @Column(name = "LG_ENVIO_EMP")
    private Integer lgEnvioEmp;
    
    @Column(name = "F_ENVIOSS")
    private Date fechaEnvio;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEra;

}
