package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TlefDetalle", description = "Tutor rescatados de la BBDD de comunica")
public interface TlefDetalleProjection {
	
	@Schema(description = "Id del tipo")
	Long getidTipo();

	@Schema(description = "Teléfono")
	String getTelefono();
	
}
