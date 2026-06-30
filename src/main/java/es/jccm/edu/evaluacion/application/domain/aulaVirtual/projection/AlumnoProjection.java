package es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AlumnoProjection {

	@Schema(description = "Id Alumno")
	Long getId();
	
	@Schema(description = "Nombre del Alumno")
	String getNombre();
	
	@Schema(description = "Apellido 1 del Alumno")
	String getApellido1();
	
	@Schema(description = "Apellido 2 del Alumno")
	String getApellido2();
	
	@Schema(description = "Núm. escolar del Alumno")
	String getNumEscolar();
	
	@Schema(description = "Nombre y apellidos del alumno")
	String getNombreCompleto();

	@Schema(description = "Id del alumno en el aula virtual")
	Long getIdUsuarioMoodle();
	
}
