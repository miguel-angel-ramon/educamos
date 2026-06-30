package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListaAlumnosGrupoActividad", description = "lista de alumnos por grupo actividad")
public class ListaAlumnosGrupoActividadDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id matricula")
	private Long idMatricula;

	@Schema(description = "Nombre del alumno")
	private String alumno;

}
