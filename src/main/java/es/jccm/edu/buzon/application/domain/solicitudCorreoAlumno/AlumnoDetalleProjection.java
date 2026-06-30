package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AlumnoDetalleProjection {

	@Schema(description = "Id del usuario en la tabla UsuariosT")
	Long getOidUsuario();

	@Schema(description = "Nombre completo del alumno")
	String getNombreCompleto();

	@Schema(description = "Nombre de la unidad de los alumnos")
	String getUnidad();

	@Schema(description = "Estado del correo del alumnado")
	Integer getEstado();

	@Schema(description = "Permiso que tiene el alumnado")
	String getCodPermiso();

	@Schema(description = "Pendiente de actualización")
	String getLPendiente();

}
