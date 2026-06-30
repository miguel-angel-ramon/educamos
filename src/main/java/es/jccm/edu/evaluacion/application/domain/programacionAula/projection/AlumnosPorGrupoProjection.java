package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumnos de los grupos")
public interface AlumnosPorGrupoProjection {
	
	@Schema(description = "Id Grupo")
	Long getIdGrupo();
	
	@Schema(description = "Nombre Grupo")
	String getNombreGrupo();
	
	@Schema(description = "Numero Alumnos")
	Integer getNumeroAlumnos();
	
	@Schema(description = "Numero Alumnos con Programación asociada")
	Integer getNumeroAlumnosConProgramacion();
	
	@Schema(description = "Numero Alumnos sin Programación asociada")
	Integer getNumeroAlumnosSinProgramacion();
}
