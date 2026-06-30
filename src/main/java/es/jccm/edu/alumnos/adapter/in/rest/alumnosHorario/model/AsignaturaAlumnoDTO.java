package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Asignaturas", description = "Entidad para rescatar el listado de mis asignaturas")
public class AsignaturaAlumnoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Abreviatura de la asignatura")
	private String abreviatura;
	
	@Schema(description = "Descipcion")
	private String descripcion;
}
