package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoDTO", description = "DTO Alumno")
public class AlumnoDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Alumno")
	private Long id;

	@Schema(description = "Nombre del Alumno")
	private String nombre;
	
	@Schema(description = "Apellido 1 del Alumno")
	private String apellido1;
	
	@Schema(description = "Apellido 2 del Alumno")
	private String apellido2;
	
	@Schema(description = "Núnm. escolar del Alumno")
	private String numEscolar;
	
	@Schema(description = "Nombre completo del Alumno")
	private String nombreCompleto;
	
	@Schema(description = "Indica si el alumno pertenece al aula virtual")
	private boolean enAulaVirtual;

	@Schema(description = "Id Matrícula del Alumno")
	private Long idUsuarioMoodle;
	
	@Schema(description = "Id Matrícula del Alumno")
	private Long idMatricula;

}