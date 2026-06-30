package es.jccm.edu.pdc.application.domain.evaluacion.projection;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionCompleto;

@Projection(name = "Evaluacion", types = {EvaluacionCompleto.class})
@Schema(name = "Evaluacion", description = "Proyección para rescatar los datos de la Evaluacion")
public interface EvaluacionCompletoProjection {
	
	@Schema(description = "Id Objetivo")
	Long getIdObjetivo();
	
	@Schema(description = "Id Centro")
	Long getIdCentro();
	
	@Schema(description = "Id Seguimiento")
	Long getIdSeguimiento();

	@Schema(description = "Id Competencia")
	Long getIdCompetencia();
	
	@Schema(description = "Id del Seguiemiento Linea Actuacion")
	Long getIdObjLinAct();
	
	@Schema(description = "Descripcion Objetivo")
	String getObjetivo();
	
	@Schema(description = "Descripcion Competencia")
	String getCompetencia();
	
	@Schema(description = "Descripcion Titulo Linea Actualizacion")
	String getTituloLinAct();
	
	@Schema(description = "Responsable")
	String getResponsable();
	
	@Schema(description = "Tareas")
	String getTareas();
	
	@Schema(description = "Valoracion")
	String getValoracion();
	
	@Schema(description = "Dificualtades y acciones")
	String getDificultades_acciones();
	
	@Schema(description = "Comentarios")
	String getComentarios();
	
	@Schema(description = "Anno actual")
	Integer getAnno();
	
	@Schema(description = "Porcentaje")
	Double getPorcentaje();
	
	@Schema(description = "Fecha Inicio")
	Date getFechaInicio();
	
	@Schema(description = "Fecha Fin")
	Date getFechaFin();
	
	@Schema(description = "Fecha Inicio Ejecución")
	Date getFechaInicioEjecucion();
	
	@Schema(description = "Fecha Fin Ejecución")
	Date getFechaFinEjecucion();

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha de fActualiza")
	Date  getFActualiza();
}