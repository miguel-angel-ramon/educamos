package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatoria materia alumno", description = "Proyección para rescatar las convocatorias del curso del alumno")
public class ConvocatoriaAlumnoEvaluacionDTO {
	
	@Schema(description = "Id de la convocatoria")
	Long idConvocatoria;
	
	@Schema(description = "Nombre de la convocatoria")
	String nombreConvocatoria;
	
	@Schema(description = "Descripción de la convocatoria")
	String descripcionConvocatoria;
	
	@Schema(description = "Id interno de la convocatoria")
	Long idConvCentroOmc;
	
	@Schema(description = "Estado de la convocatoria")
	String estado;
	
	@Schema(description = "Observaciones de la convocatoria")
	String observaciones;
	
	@Schema(description = "¿Es convocatoria final?")
	Boolean esFinal;

	@Schema(description = "Indica si la convocatoria debe mostrarse")
	boolean mostrar = true;

	@Schema(description = "Indica si se ha seleccionado todas las materias de la convocatoria")
	boolean seleccionado = false;
}
