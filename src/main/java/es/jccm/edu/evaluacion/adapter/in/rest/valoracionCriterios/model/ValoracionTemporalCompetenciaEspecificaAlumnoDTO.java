package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CalificacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Valoración temporal competencia específica del alumno", description = "Dto valoración temporal competencia específica del alumno")
public class ValoracionTemporalCompetenciaEspecificaAlumnoDTO {

	@Schema(description = "Id de la valoración de la competencia")
	Long idCompetencia;
	
	@Schema(description = "Id de la ponderación")
	Long idPonderacion;
	
	@Schema(description = "Id competencia especifica")
	Long comEsp;
	
	@Schema(description = "Id de la calificación")
	Long idCalifica;
	
	//provisional
    @Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Nota de la competencia")
    int nota;
    //
	
	@Schema(description = "Id materia matrícula del alumno")
	Long matMatricula;
	
	@Schema(description = "Calificación de la competencia específica")
	CalificacionDTO calificacion;
	
	@Schema(description = "valoraciones temporales criterios de evaluación del alumno")
	List<ValoracionTemporalCriterioEvaluacionAlumnoDTO> criterios;
	
}
