package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoHorario", description = "AlumnosHorario rescatados de la BBDD de comunica")
public interface AlumnoDetalleProjection {

	@Schema(description = "Id del Alumno")
	Long getIdAlumno();

	@Schema(description = "Nombre")
	String getNombre();

	@Schema(description = "Primer apellido")
	String getApellido1();

	@Schema(description = "Segundo apellido")
	String getApellido2();

	@Schema(description = "Numero escolar")
	String getIdescolar();

	@Schema(description = "Numero identifcacion")
	String getNumide();

	@Schema(description = "Foto alumno")
	byte[] getFoto();

	@Schema(description = "Dirección")
	String getDireccion();

	@Schema(description = "Fecha de nacimiento")
	Date getFechaNacimiento();

	@Schema(description = "Correo")
	String getCorreo();

	@Schema(description = "Teléfono")
	String getTelefono();

	@Schema(description = "Tlefnourg")
	String getTlefnourg();

	@Schema(description = "Id del Expediente")
	String getIdExpediente();

}
