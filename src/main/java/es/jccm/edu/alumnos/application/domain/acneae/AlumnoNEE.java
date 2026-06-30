package es.jccm.edu.alumnos.application.domain.acneae;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity

public class AlumnoNEE implements Serializable	 {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private String necesidadEducativa;
	
	private String nombre;
	
	private String apellido1;
	
	private String apellido2;
	
	private String estadoMatricula;
	
	private Date fechaNacimiento;
	
	private Long idMatricula;
	
	private String nivelAdaptacion;
	
	
	
}
