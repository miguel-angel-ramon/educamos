package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "lista de alumnos por grupo actividad", description = "lista de alumnos por grupo actividad")
public interface ListaAlumnosGrupoActividadProjection {

	@Schema(description = "Id matricula")
	Long getIdMatricula();

	@Schema(description = "Nombre y apellidos del alumno")
	String getAlumno();

}
