package es.jccm.edu.alumnos.application.domain.familiasAlumnado;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TutorFamilia implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	private Long id;

	private String nombre;
	
	private String tipoIDE;
	
	private String dni;
	
	private String telefono;
	
	private String telefonoUrgencia;

	private String domicilio;
	
	private String resultado;


	
	

}
