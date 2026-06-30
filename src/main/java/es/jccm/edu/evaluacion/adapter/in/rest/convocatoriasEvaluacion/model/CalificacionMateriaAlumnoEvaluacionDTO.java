package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria materia alumno", description = "Proyección para rescatar las convocatorias de la materia que cursa el alumno")
public class CalificacionMateriaAlumnoEvaluacionDTO {
	
	@Schema(description = "Id interno de la convocatoria")
	Long idConvCentroOmc;
	
	@Schema(description = "Id de la convocatoria")
	Long idConvocatoria;

	@Schema(description = "Id de la calificación calculada")
	Long idCalificaCal;
	
	@Schema(description = "Nota calculada")
	String notaCal;
	
	@Schema(description = "¿Aprueba con la nota calculada?")
	String apruebaCal;

	@Schema(description = "Id de la calificación definitiva")
	Long idCalificaDef;
	
	@Schema(description = "Nota definitiva")
	String notaDef;
	
	@Schema(description = "¿Aprueba con la nota definitiva?")
	String apruebaDef;

	@Schema(description = "Indica si se ha seleccionado la materia para cambiarla")
	boolean seleccionado = false;
	
}
