package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "Evaluación curricular Individual", description = "Evaluacion curricular individual de alumnos por grupo/unidad")
public interface AlumnoValoracionProjection {

	@Schema(description = "Id Matricula")
	Long getIdMatricula();
	
	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id Etapa")
	Long getIdEtapa();
	
	@Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
	
	@Schema(description = "Id número escolar del alumno")
	String getNumEscolar();

	@Schema(description = "Nombre y apellidos de alumno")
	String getNombreAlumno();
	
	@Schema(description = "Nombre de la etapa")
	String getNombreEtapa();
	
	@Schema(description = "Nombre de la convocatoria")
	String getNombreConvocatoria();

	@Schema(description = "Es ACNEE")
	Integer getAcnee();

	@Schema(description = "Nivel curricular alumno ACNEE")
	String getNivelCurricular();
	
	@Schema(description = "Id Etapa adaptación alumno ACNEE")
	Long getIdEtapaAdaptacion();
	
}
