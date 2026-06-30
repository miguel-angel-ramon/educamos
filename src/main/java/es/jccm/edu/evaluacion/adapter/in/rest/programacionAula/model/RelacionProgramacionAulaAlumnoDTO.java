package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RelacionProgramacionAulaAlumnoDTO", description = "DTO Relacion ProgramacionAula - Alumno")
public class RelacionProgramacionAulaAlumnoDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Relacion ProgramacionAula - Alumno")
	private Long id;

	@Schema(description = "Id. Programación Aula")
	private Long programacionAula;

	@Schema(description = "Id. Unidad Centro")
	private Long unidadCentro;
	
	@Schema(description = "Matricula del alumno")
	private MatriculaAlumnoDTO matriculaAlumno;
	
	@Schema(description = "Id. del usuario en Moodle")
	private Long idUsuarioMoodle;
}