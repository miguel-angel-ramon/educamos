package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoActividadAulaVirtualDTO", description = "DTO Alumno Actividad Aula Virtual")
public class AlumnoActividadAulaVirtualDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Alumno")
	private Long id;

	@Schema(description = "Nombre del Alumno")
	private String firstname;
	
	@Schema(description = "Apellidos del Alumno")
	private String lastname;
	
	@Schema(description = "Nombre completo del Alumno")
	private String fullname;
	
	@Schema(description = "Url foto del Alumno")
	private String profileimage;

	@Schema(description = "Indica si el alumno pertenece al aula virtual")
	private boolean enAulaVirtual;

}