package es.jccm.edu.buzon.application.domain.buzonCentro;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AlumnoUnidad {

	@Id
	private Long idMatricula;
	
	private Long idAlumno;
	
	private Long idUnidad;
	
	private String nombreUnidad;
	
	private String Curso;
	
	private Long idPersona;
	
	private String nombre;
	
	private String apellido1;
	
	private String apellido2;
}
