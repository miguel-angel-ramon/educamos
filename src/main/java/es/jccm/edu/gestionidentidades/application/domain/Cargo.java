package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TLCARGOS", schema="DELPHOS")
public class Cargo {

    @Id
    @Column(name = "C_CARGO")
    private String codigo;

    @Column(name = "D_CARGO")
    private String descripcion;

    @Column(name = "S_CARGO")
    private String tipoCargo;

    @Column(name = "F_FINVIG")
    private Date fechaFinVigencia;

    @Column(name = "C_USUCREACION")
    private Long usuarioCreacion;

    @Column(name = "F_CREACION")
    private Date fechaCreacion;

    @Column(name = "C_USUACTUALIZA")
    private Long usuarioActualizacion;

    @Column(name = "F_ACTUALIZA")
    private Date fechaActualizacion;

    @Column(name = "C_NIVEL")
    private String nivel;

    @Column(name = "L_ASIGMAN")
    private String asignacionMando;

    @Column(name = "L_VIGENTE")
    private String vigente;

    @Column(name = "L_DEPARTAMENTO")
    private String departamento;

    @Column(name = "L_TRIBUNAL")
    private String tribunal;

    @Column(name = "LG_EQUDIR")
    private Integer equipoDireccion;
    
}
