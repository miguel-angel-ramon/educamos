package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TLPTOTRAEMP", schema="DELPHOS")
public class Ptotraemp implements Serializable{

    @Id
    @Column(name = "X_EMPLEADO")
    private Long id;

    @Id
    @Column(name = "F_TOMAPOS")
    private Date fechaTomaPos;

    @Column(name = "X_CENTRO")
    private Integer centro;

    @Column(name = "X_PUESTO")
    private Integer puesto;

    @Column(name = "X_TIPOPER")
    private Integer tipoOper;

    @Column(name = "F_CESE")
    private Date fechaCese;

    @Column(name = "C_USUCREACION")
    private Long usuarioCreacion;

    @Column(name = "F_CREACION")
    private Date fechaCreacion;

    @Column(name = "C_USUACTUALIZA")
    private Long usuarioActualizacion;

    @Column(name = "F_ACTUALIZA")
    private Date fechaActualizacion;

    @Column(name = "T_ABREV")
    private String abreviatura;

    @Column(name = "X_EMPLEADO_SUSTITUYE")
    private Long empleadoSustituye;

    @Column(name = "F_TOMAPOS_SUSTITUYE")
    private Date fechaTomaPosSustituye;

    @Column(name = "L_PROSIR")
    private String prosir;

    @Column(name = "F_TOMAPOSREA")
    private Date fechaTomaPosRea;

    @Column(name = "X_ORGDEPPUE")
    private Long orgDepPue;

    @Column(name = "X_EMPSIR")
    private Integer empSir;

    @Column(name = "L_USUARIO")
    private String usuario;

    @Column(name = "X_LOCALIDAD")
    private Integer localidad;

    @Column(name = "C_TIPOPUESTO")
    private String tipoPuesto;
    
}
