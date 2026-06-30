package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class PuestoTrabajoEmpleadoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="X_EMPLEADO", insertable=false, updatable=false)
	private Long empleado;

	@Column(name="F_TOMAPOS")
	private Date fechaTomaPosesion;

}