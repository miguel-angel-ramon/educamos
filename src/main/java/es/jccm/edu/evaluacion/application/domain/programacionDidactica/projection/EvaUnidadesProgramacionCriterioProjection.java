package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UnidadesProgramacionCriterioProjection", description = "Proyección para rescatar las unidades de programación por criterio")
public interface EvaUnidadesProgramacionCriterioProjection {

	@Schema(description = "Id del Criterio")
	Long getIdCriterio();

	@Schema(description = "Nº de unidades de programación")
	Long getNumUnidadesProgramacion();
	
}
