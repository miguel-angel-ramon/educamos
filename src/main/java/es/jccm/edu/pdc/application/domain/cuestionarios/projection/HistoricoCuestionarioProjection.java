package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Historico", description = "Proyección para rescatar el año y el idCuestionario de los cuestionarios que se finalizaron")
public interface HistoricoCuestionarioProjection {
	
	@Schema(description = "C_ANNO")
	Integer getAnio();
	
	@Schema(description = "X_CUEPUBUSU")
	String getIdCuestionario();
		
}
