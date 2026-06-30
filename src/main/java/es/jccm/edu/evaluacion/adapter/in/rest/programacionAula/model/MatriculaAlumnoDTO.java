package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MatriculaAlumnoDTO", description = "DTO Matrícula Alumno")
public class MatriculaAlumnoDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Matrícula del Alumno")
	private Long id;

	@Schema(description = "Alumno")
	private AlumnoDTO alumno;
	
	@Schema(description = "Año Académico de la Matrícula del Alumno")
	private Integer  anno;
	
	@Schema(description = "Id. de la Oferta Matriculación")
	private Long idOfertaMatriculacion;
	
	@Schema(description = "Unidad asignada de la Matrícula del Alumno")
	private Long idUnidad;

}