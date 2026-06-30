package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Blob;


@Schema(name = "Evaluacion", description = "Evaluacion de alumnos por grupo actividad")
public interface EvaluacionProjection {

	@Schema(description = "Id Matricula")
	Long getIdMatricula();
	
	@Schema(description = "Id Alumno")
	Long getIdAlumno();

	@Schema(description = "Nombre y apellidos de alumno")
	String getNombreAlumno();

	@Schema(description = "Foto alumno")
	Blob getFoto();

	@Schema(description = "El alumno tiene nivel acnee")
	Long getAcnee();

	@Schema(description = "Nivel Curricular del alumno acnee")
	String getNivelCurricular();
	
	@Schema(description = "Id estado de la convocatoria del centro")
	Long getIdEstadoConvocatoria();
	
	@Schema(description = "Estado de la convocatoria")
	String getNombreEstadoConvocatoria();

}
