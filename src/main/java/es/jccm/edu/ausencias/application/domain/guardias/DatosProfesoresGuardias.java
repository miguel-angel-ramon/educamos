package es.jccm.edu.ausencias.application.domain.guardias;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class DatosProfesoresGuardias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idUsuario;
	
	private String oIdUsuario;
	
	private String nombre;
	
	private String telefono;
	
	private String correo;

}
