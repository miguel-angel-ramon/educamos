package es.jccm.edu.evaluacion.application.domain.programacionAula;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AlumnosPorMateria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idAlumno;
	   
	private String nombreAlumno;
	
	private Long idMatricula;
	
	private String estado;
	
	private Long progAula;
	
	private Long aula;

	private String unidad;
	
	private Long idMatMatricula;
	
    private Long idUnidad;
    
    private Long idUsuarioMoodle;

	private Boolean tieneValoraciones = false;

}
