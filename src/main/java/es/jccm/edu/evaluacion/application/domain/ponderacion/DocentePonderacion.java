package es.jccm.edu.evaluacion.application.domain.ponderacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class DocentePonderacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idEmpleado;

	private String nombre;

	private String apellidos;

}
