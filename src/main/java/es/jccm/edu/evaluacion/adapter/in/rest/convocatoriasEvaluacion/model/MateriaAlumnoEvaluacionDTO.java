package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materia evaluación alumno", description = "Proyección para rescatar las materias de las que se evalúa el alumno")
public class MateriaAlumnoEvaluacionDTO {
	
	@Schema(description = "Id de la materia")
	Long idMateria;
	
	@Schema(description = "Nombre de la materia")
	String nombreMateria;

	@Schema(description = "Calificaciones de las convocatorias asociadas a la materia")
	List<CalificacionMateriaAlumnoEvaluacionDTO> calificaciones;
	
	@Schema(description = "Id materia matrícula del alumno")
	Long idMatMatriAlu;
	
	@Schema(description = "Id del estado de la materia matrícula del alumno")
	Long idEstado;
	
	@Schema(description = "Nombre del estado de la materia matrícula del alumno")
	String nombreEstado;

	@Schema(description = "El alumno es o no acnee")
	Long acnee;

	@Schema(description = "Nivel curricular del alumno")
	String nivelCurricular;

	@Schema(description = "Materia de adaptacion del alumno")
	String materiaAdap;
	
	@Schema(description = "¿Está la materia ponderada?")
	private Boolean estaPonderada;
	
	@Schema(description = "Define si la materia tiene nivel curricular en BBDD")
	private Boolean existenCompetencias;
	
	
}
