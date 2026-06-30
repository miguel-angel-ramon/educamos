package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Actividades ponderaciones criterios programación aula")
public class CriterioActividadPonderacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id criterio evaluación")
	Long idCriterioEvaluacion;
	
	@Schema(description = "Id actividad")
	Long idActividad;
	
	@Schema(description = "Id competencia específica")
	Long idCompetenciaEspecifica;
	
	@Schema(description = "Id unidad programación")
	Long idUnidadProgramacion;
	
	@Schema(description = "Abreviatura criterio evaluación")
	String abrevCriterioEvaluacion;
	
	@Schema(description = "Descripción criterio evaluación")
	String descripcionCriterioEvaluacion;
	
	@Schema(description = "Abreviatura actividad")
	String abrevActividad;
	
	@Schema(description = "Nombre actividad")
	String nombreActividad;
	
	@Schema(description = "Abreviatura competencia específica")
	String abrevCompetenciaEspecifica;
	
	@Schema(description = "Descripción competencia específica")
	String descripcionCompetenciaEspecifica;
	
	@Schema(description = "Abreviatura unidad programación")
	String abrevUnidadProgramacion;
	
	@Schema(description = "Nombre unidad programación")
	String nombreUnidadProgramacion;
	
	@Schema(description = "¿Está ponderado el criterio de la actividad?")
	Boolean esPonderada;
	
	@Schema(description = "Peso del criterio de la actividad")
	Integer peso;
	
	@Schema(description = "Porcentaje ponderado de la actividad")
	Float porcentaje;
	
	@Schema(description = "Actividad asociada")
	ActividadDTO actividadAsociada;

}
