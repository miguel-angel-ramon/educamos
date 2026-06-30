package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Convocatoria materia alumno", description = "Proyección para rescatar las convocatorias de la materia que cursa el alumno")
public interface CalificacionMateriaAlumnoEvaluacionProjection {
	
	@Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
	
	@Schema(description = "Id de la convocatoria")
	Long getIdConvocatoria();

	@Schema(description = "Id de la calificación calculada")
	Long getIdCalificaCal();
	
	@Schema(description = "Nota calculada")
	String getNotaCal();
	
	@Schema(description = "¿Aprueba con la nota calculada?")
	String getApruebaCal();

	@Schema(description = "Id de la calificación definitiva")
	Long getIdCalificaDef();
	
	@Schema(description = "Nota definitiva")
	String getNotaDef();
	
	@Schema(description = "¿Aprueba con la nota definitiva?")
	String getApruebaDef();

}
