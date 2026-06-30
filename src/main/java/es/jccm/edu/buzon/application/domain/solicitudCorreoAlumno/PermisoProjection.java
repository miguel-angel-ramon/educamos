package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import io.swagger.v3.oas.annotations.media.Schema;

public interface PermisoProjection {

	@Schema(description = "Id del permiso de correo del alumnado")
	Long getIdPermiso();

	@Schema(description = "Nombre del permiso de correo del alumnado")
	String getCodPermiso();

	@Schema(description = "Descripción del permiso de correo del alumnado")
	String getDescripcion();

}
