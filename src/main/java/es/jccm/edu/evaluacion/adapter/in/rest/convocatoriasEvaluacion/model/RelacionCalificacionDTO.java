package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Relación de calificaciones", description = "Proyección para rescatar la relación de notas de un sistema/criterio de calificación")
public class RelacionCalificacionDTO {
	
	@Schema(description = "Id de la relación de calificación")
	Long idRelacionCalificacion;
	
	@Schema(description = "Id de la calificación")
	Long idCalificacion;
	
	@Schema(description = "Nota asociada a la calificación")
	String descCal;
	
	@Schema(description = "Si con esta calificación se supera la materia")
	String aprueba;
	
	@Schema(description = "Mínimo valor calculado para la nota")
	Float minima;
	
	@Schema(description = "Máximo valor calculado para la nota")
	Float maxima;
	
}
