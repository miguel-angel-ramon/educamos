package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ActividadDTO", description = "DTO Relacion Alumno Oid")
public class RelacionAlumnoOidDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Alumno")
	private Long idAlumno;

	@Schema(description = "Oid del Alumno")
	private Long oid;
}