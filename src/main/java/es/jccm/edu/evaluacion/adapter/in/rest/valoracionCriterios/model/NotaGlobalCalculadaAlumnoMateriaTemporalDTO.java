package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Nota global temporal", description = "Dto nota global calculada temporal del alumno")
public class NotaGlobalCalculadaAlumnoMateriaTemporalDTO {

	@Schema(description = "Id de la nota global calculada temporal del alumno")
	Long id;
	
	@Schema(description = "Id de la calificación")
	Long idCalificacion;
	
	@Schema(description = "Id materia matrícula del alumno")
	Long matMatricula;
	
	@Schema(description = "Nota global")
	Double nota;
	
	@Schema(description = "Descripción de la nota del alumno")
    String descCal;
	
	@Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
	
}
