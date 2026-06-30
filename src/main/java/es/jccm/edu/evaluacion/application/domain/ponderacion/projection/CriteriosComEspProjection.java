package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CriteriosComEsp", description = "Proyección para rescatar los criterios de ponderacion")
public interface CriteriosComEspProjection {

	@Schema(description = "Id relacion del criterio")
	Long getIdPonderacion();

	@Schema(description = "Id del criterio")
	Long getIdCriterio();
	
	
	
}