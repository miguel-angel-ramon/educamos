package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Alumnos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idAlumno;
	
	private String idEscolar;

	private Long idMatricula;
	
	private String nombre;
	
	private String unidad;
	
	private Date fechaNacimiento;
	
	private boolean diversifica;
	
	private String estadoMatricula;

	@Lob
	private byte[] foto;

}
