package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FCT_EVAALU", description = "Descripcion para el modelo de apto evaluacion alumno")
public interface AptoEvaluacionAlumnoProjection {
	
	@Schema(description = "ID")
	Long getId();
	
	@Schema(description = "Descripcion Corta")
	String getDescripcionCorta();
	
	@Schema(description = "Descripcion Larga")
	String getDescripcionLarga();
	
}

