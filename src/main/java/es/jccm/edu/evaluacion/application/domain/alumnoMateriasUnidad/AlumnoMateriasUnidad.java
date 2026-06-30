package es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AlumnoMateriasUnidad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long idMateria;
	
	private String nombreMateria;
	
	private String descripcionMateria;
	
	private String materiaAbreviatura;
	
	private Long idUnidad;
	
	private String nombreCurso;
	
	private Long idAlumno;
	
	private String nombre;
	
	private String apellidos;
		
}
