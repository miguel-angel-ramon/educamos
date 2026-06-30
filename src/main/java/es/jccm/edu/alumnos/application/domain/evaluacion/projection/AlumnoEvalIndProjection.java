package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Blob;


@Schema(name = "Evaluación Individual", description = "Evaluacion individual de alumnos por grupo/unidad")
public interface AlumnoEvalIndProjection {

	@Schema(description = "Id Matricula")
	Long getIdMatricula();
	
	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id Unidad")
	Long getIdUnidad();

	@Schema(description = "Nombre y apellidos de alumno")
	String getNombreAlumno();
	
	@Schema(description = "Nombre de la unidad")
	String getNombreUnidad();
}
