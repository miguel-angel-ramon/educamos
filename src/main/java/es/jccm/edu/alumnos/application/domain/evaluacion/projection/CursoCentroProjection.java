package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Curso Centro", description = "Curso Centro")
public interface CursoCentroProjection {
	
	@Schema(description = "Id Curso")
	Long getIdOfermatrig();
	
	@Schema(description = "Descripcion")
	String getDescripcionCorta();

	@Schema(description = "tipo")
	String getTipo();
	
	@Schema(description = "orden")
	Long getOrden();
}
