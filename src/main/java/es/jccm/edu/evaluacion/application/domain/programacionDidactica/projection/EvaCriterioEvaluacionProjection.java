package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Criterios de evaluacion", description = "Criterios de evaluacion")
public interface EvaCriterioEvaluacionProjection {

	@Schema(description = "Id del criterio")
	Long getId();
	
	@Schema(description = "nombre del criterio")
	String getNombre();
	
	@Schema(description = "abreviatura")
	String getAbreviatura();
	
	@Schema(description = "id del ciclo")
	Long getIdCiclo();
	
	@Schema(description = "Competencia")
	Long getIdCompetenciaEspecifica();
	
	@Schema(description = "Estado de la programación didáctica")
	Integer getOrden();
}
