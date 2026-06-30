package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLPTOTRAEMP")
public class PuestoTrabajoEmpleado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PuestoTrabajoEmpleadoPK id;

	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private Centro centro;
	
    @MapsId("empleado")
    @ManyToOne
    @JoinColumn(name="X_EMPLEADO")
    public Empleado empleado;
	
}