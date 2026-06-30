package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CalificacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Valoración temporal criterio evaluación del alumno", description = "Dto valoración temporal criterio evaluación del alumno")
public class ValoracionTemporalCriterioEvaluacionAlumnoDTO {

	@Schema(description = "Id de la valoración del criterio")
	Long id;
	
	@Schema(description = "Id de la ponderación")
	Long idPonderacion;
	
	@Schema(description = "Id criterio evaluación")
	Long criEva;
	
	@Schema(description = "Id de la calificación")
	Long idCalifica;
	
	//provisonal
    @Schema(description = "Nota del criterio")
    int nota;

    @Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    //
	
	@Schema(description = "Id materia matrícula del alumno")
	Long matMatricula;
	
	@Schema(description = "Calificación del criterio de evaluación")
	CalificacionDTO calificacion;
	
}
