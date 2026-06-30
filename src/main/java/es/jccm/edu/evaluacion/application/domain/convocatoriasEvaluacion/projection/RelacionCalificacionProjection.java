package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Relación de calificaciones", description = "Proyección para rescatar la relación de notas de un sistema/criterio de calificación")
public interface RelacionCalificacionProjection {
	
	@Schema(description = "Id de la relación de calificación")
	Long getIdRelacionCalifacion();
	
	@Schema(description = "Id de la calificación")
	Long getIdCalificacion();
	
	@Schema(description = "Nota asociada a la calificación")
	String getDescCal();

	@Schema(description = "Descripción sistema de calificación")
	String getDescSis();
	
	@Schema(description = "Si con esta calificación se supera la materia")
	String getAprueba();
	
	@Schema(description = "Mínimo valor calculado para la nota")
	Float getMinima();
	
	@Schema(description = "Máximo valor calculado para la nota")
	Float getMaxima();

}
