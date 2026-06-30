package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CompetenciasEspecificasPonderacionProjection", description = "CompetenciasEspecificasPonderacionProjection")
public interface CompetenciasEspecificasPonderacionProjection {

	@Schema(description = "Id")
	Long getId();

	@Schema(description = "Descripcion")
	String getDescripcion();

	@Schema(description = "Abreviatura")
	String getAbrev();
	
	@Schema(description = "Id ciclo")
	String getIdCiclo();

	@Schema(description = "Orden")
	Float getNordenPres();
	
}
