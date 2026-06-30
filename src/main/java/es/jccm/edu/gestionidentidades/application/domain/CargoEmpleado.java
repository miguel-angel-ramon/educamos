package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TLCARGOSEMP", schema="DELPHOS")
public class CargoEmpleado implements Serializable{

    @Id
    @Column(name = "X_EMPLEADO")
    private Long id;

    @Id
    @Column(name = "F_TOMAPOS")
    private Date fechaTomaPos;

    @Id
    @Column(name = "F_TOMPOS")
    private Date fechaTomaPosCargo;

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

    @Column(name = "X_EMPSIR")
    private Integer empSir;

    @Column(name = "F_TOMPOSREA")
    private Date fechaTomaPosRea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "X_EMPLEADO", referencedColumnName = "X_EMPLEADO"),
            @JoinColumn(name = "F_TOMAPOS", referencedColumnName = "F_TOMAPOS")
    })
    private Ptotraemp empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_CARGO")
    private Cargo cargoDetalle;

}
