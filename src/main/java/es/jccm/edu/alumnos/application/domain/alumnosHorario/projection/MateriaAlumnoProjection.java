package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MateriaAlumno", description = "Materias un alumno para un año académico")
public interface MateriaAlumnoProjection {
	
	@Schema(description = "Id de la materia")
	Long getIdMateria();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "IdMateriaOmg")
	String getIdMateriaOmg();
	
	@Schema(description = "Observación")
	String getObservacion();
	
}

