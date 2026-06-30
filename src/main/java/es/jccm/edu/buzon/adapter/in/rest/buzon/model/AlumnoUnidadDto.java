package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoUnidad", description = "Alumno y la unidad a la que pertenece")
public class AlumnoUnidadDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la matrícula")
	private Long idMatricula;
	
	@Schema(description = "Id del alumno")
	private Long idAlumno;
	
	@Schema(description = "Id de la unidad")
	private Long idUnidad;
	
	@Schema(description = "Nombre de la unidad")
	private String nombreUnidad;
	
	@Schema(description = "Curso")
	private String Curso;
	
	@Schema(description = "Id de la persona")
	private Long idPersona;

	@Schema(description = "Nombre de la persona")
	private String nombre;
	
	@Schema(description = "Primer apellido de la persona")
	private String apellido1;
	
	@Schema(description = "Segundo apellido de la persona")
	private String apellido2;
}
