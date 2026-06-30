package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "Evaluación Individual", description = "Evaluacion individual de alumnos por grupo/unidad")
public interface AlumnoEvaluacionSelProjection {

	@Schema(description = "Id Matricula")
	Long getIdMatricula();
	
	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id Etapa")
	Long getIdEtapa();
	
	@Schema(description = "Id Ciclo")
	Long getIdCiclo();
	
	@Schema(description = "Id Unidad")
	Long getIdUnidad();

	@Schema(description = "Nombre y apellidos de alumno")
	String getNombreAlumno();
	
	@Schema(description = "Nombre de la etapa")
	String getNombreEtapa();
	
	@Schema(description = "Nombre del ciclo")
	String getNombreCiclo();
	
	@Schema(description = "Nombre de la unidad")
	String getNombreUnidad();
	
	@Schema(description = "Es ACNEE")
	Integer getAcnee();
	
	@Schema(description = "Nivel curricular alumno ACNEE")
	String getNivelCurricular();
}
