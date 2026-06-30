package es.jccm.edu.simulacion.application.domain.usuarios.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class PuestoTrabajoEmpleadoGenericoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "X_EMPLEADO", nullable = false, insertable=false, updatable=false)
    private Long idEmpleado;

    @Column(name = "F_TOMAPOS", nullable = false)
    private Date fechaTomaPosesion;

}
