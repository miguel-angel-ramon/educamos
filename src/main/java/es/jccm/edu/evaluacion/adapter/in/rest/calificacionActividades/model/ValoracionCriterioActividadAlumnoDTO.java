package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ValoracionCriterioActividadAlumnoDTO", description = "DTO Valoracion Criterio Actividad Alumno")
public class ValoracionCriterioActividadAlumnoDTO {
	
	@Schema(description = "Id de la Valoracion Criterio Actividad Alumno")
	private Long id;

	// ---------- Relationships -----------

	@Schema(description = "Identificador de la relación Actividad Criterio Evaluación")
	private Long idRelacionActividadCriterioEvaluacion;
	
	@Schema(description = "Identificador de la relación Actividad Alumno")
	private Long idRelacionActividadAlumno;

	@Schema(description = "Identificador de la Calificación")
	private Long idCalificacion;

}