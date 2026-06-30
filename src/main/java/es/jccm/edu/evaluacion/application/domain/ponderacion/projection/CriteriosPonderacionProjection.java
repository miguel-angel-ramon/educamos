package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CriteriosPonderacionProjection", description = "CriteriosPonderacionProjection")
public interface CriteriosPonderacionProjection {

	@Schema(description = "Id")
	Long getId();

	@Schema(description = "Nombre")
	String getNombre();

	@Schema(description = "Abreviatura")
	String getAbreviatura();
	
	@Schema(description = "Id ciclo")
	String getIdCiclo();

	@Schema(description = "Orden")
	Float getOrden();
	
}
