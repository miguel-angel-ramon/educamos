package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Actividades criterios programación aula")
public interface CriterioActividadProjection {

	@Schema(description = "Id criterio evaluación")
	Long getIdCriterioEvaluacion();
	
	@Schema(description = "Id actividad")
	Long getIdActividad();
	
	@Schema(description = "Id competencia específica")
	Long getIdCompetenciaEspecifica();
	
	@Schema(description = "Id unidad programación")
	Long getIdUnidadProgramacion();
	
	@Schema(description = "Abreviatura criterio evaluación")
	String getAbrevCriterioEvaluacion();
	
	@Schema(description = "Descripción criterio evaluación")
	String getDescripcionCriterioEvaluacion();
	
	@Schema(description = "Abreviatura actividad")
	String getAbrevActividad();
	
	@Schema(description = "Nombre actividad")
	String getNombreActividad();
	
	@Schema(description = "Abreviatura competencia específica")
	String getAbrevCompetenciaEspecifica();
	
	@Schema(description = "Descripción competencia específica")
	String getDescripcionCompetenciaEspecifica();
	
	@Schema(description = "Abreviatura unidad programación")
	String getAbrevUnidadProgramacion();
	
	@Schema(description = "Nombre unidad programación")
	String getNombreUnidadProgramacion();
	
	@Schema(description = "¿Está ponderado el criterio de la actividad?")
	Boolean getEsPonderada();
	
	@Schema(description = "Peso del criterio de la actividad")
	Integer getPeso();
		
}
