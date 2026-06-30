package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Materia evaluación alumno", description = "Proyección para rescatar las materias de las que se evalúa el alumno")
public interface MateriaAlumnoEvaluacionProjection {
	
	@Schema(description = "Id de la materia")
	Long getIdMateria();
	
	@Schema(description = "Nombre de la materia")
	String getNombreMateria();
	
	@Schema(description = "Id materia matrícula del alumno")
    Long getIdMatMatriAlu();
	
	@Schema(description = "Id del estado de la materia matrícula del alumno")
	Long getIdEstado();
	
	@Schema(description = "Nombre del estado de la materia matrícula del alumno")
	String getNombreEstado();

	@Schema(description = "El alumno es o no acnee")
	Long getAcnee();

	@Schema(description = "Nivel curricular del alumno")
	String getNivelCurricular();

	@Schema(description = "Materia de adaptacion acnee")
	String getMateriaAdap();

}
