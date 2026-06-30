package es.jccm.edu.pdc.application.domain.planActuacion.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Objetivos específicos", description = "Proyección para rescatar los objetivos específicos")
public interface LineaSeguimientoProjection {
	
	@Schema(description = "Id seguimiento linea actuación")
	Long getIdSeguiLinAct();
	
	@Schema(description = "Fecha de inicio de ejecucion")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaInicioEjecucion();
	
	@Schema(description = "Fecha de fin de ejecucion")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaFinEjecucion();
	
	@Schema(description = "Porcentaje")
	Integer getPorcentaje();
	
	@Schema(description = "Tareas")
	String getTareas();
	
	@Schema(description = "Valoración")
	String getValoracion();
	
	@Schema(description = "Dificultades y acciones")
	String getDificultades_acciones();
	
	@Schema(description = "Comentarios")
	String getComentarios();
	
	@Schema(description = "Id linea actuación")
	Long getIdLinAct();
	
	@Schema(description = "Id usuario creación")
	Long getIdUsuCreacion();
	
	@Schema(description = "Fecha de creación")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaCreacion();
	
	@Schema(description = "Id usuario actualiza")
	Long getIdUsuActualiza();
	 
	@Schema(description = "Fecha de actualización")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaActualizacion();
}

