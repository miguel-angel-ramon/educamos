package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Evaluacion", description = "Evaluacion de alumnos por grupo actividad")
public class AlumnoEvalIndDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Matricula")
	private Long idMatricula;
	
	@Schema(description = "Id Alumno")
	private Long idAlumno;
	
	@Schema(description = "Id Unidad")
	Long idUnidad;

	@Schema(description = "Nombre y apellidos de alumno")
	private String nombreAlumno;
	
	@Schema(description = "Nombre de la unidad")
	String nombreUnidad;

}
