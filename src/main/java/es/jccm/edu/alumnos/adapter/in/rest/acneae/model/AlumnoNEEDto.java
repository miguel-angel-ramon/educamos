package es.jccm.edu.alumnos.adapter.in.rest.acneae.model;

import java.io.Serializable;
import java.util.Date;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Alumnos",description="Entidad para rescatar el listado de alumnos con NEE")
public class AlumnoNEEDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description="Id del alumno")
	private Long id;
	
	@Schema(description="Necesidad educativa especial")
	private String necesidadEducativa;
	
	@Schema(description="Nombre del alumno")
	private String nombre;
	
	@Schema(description="Primer apellido del alumno")
	private String apellido1;
	
	@Schema(description="Segundo apellido del alumno")
	private String apellido2;
	
	@Schema(description="Estado de la matricula")
	private String estadoMatricula;
	
	@Schema(description="Fecha de Nacimiento del alumno")
	private Date fechaNacimiento;
	
	@Schema(description="Id Matricula")
	private Long idMatricula;
	
	@Schema(description="Nivel de adaptación")
	private String nivelAdaptacion;
	
	
	

}
