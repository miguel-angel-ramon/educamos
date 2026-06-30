package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AtributosTotalHoras implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer idEmpleado;

	private Date fechaTomaPosesion;
	
	private Date fechaInicio;

	private Date fechaFin;
	
	private Date fechaActual;

}
