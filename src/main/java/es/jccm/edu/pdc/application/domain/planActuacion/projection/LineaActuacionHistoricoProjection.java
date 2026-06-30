package es.jccm.edu.pdc.application.domain.planActuacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Objetivos específicos", description = "Proyección para rescatar los objetivos específicos")
public interface LineaActuacionHistoricoProjection {
	
	@Schema(description = "Fecha de creación de la línea de actuación")
	Date getFechaCreacion();
	
	@Schema(description = "Id de la línea de actuación")
	Long getIdLinAct();

	@Schema(description = "Título de la línea de actuación")
	String getTitulo();

	@Schema(description = "Descripción de la línea de actuación")
	String getDescripcion();

	@Schema(description = "Fecha de inicio línea de actuación")
	Date getFechaInicio();

	@Schema(description = "Fecha de fin línea de actuación")
	Date getFechaFin();

	@Schema(description = "Responsable de la línea de actuación")
	String getResponsable();

	@Schema(description = "Logro de la línea de actuación")
	String getLogro();

	@Schema(description = "Instrumentos de la línea de actuación")
	String getInstrumentos();

	@Schema(description = "Indica si la línea de actuación está activa")
	String getActivo();
	
	@Schema(description = "Indica el estado de la línea de actuación")
	String getEstado();
	
	@Schema(description = "Indica el porcentaje")
	Integer getPorcentaje();
	
	@Schema(description = "Fecha inicio ejecución de la actuación")
	Date getFechaInicioEjecucionAct();
	
	@Schema(description = "Fecha fin ejecución de la actuación")
	Date getFechaFinEjecucionAct();
	
	@Schema(description = "tareas de la actuación")
	String getTareasActuacion();
	
	@Schema(description = "valoración de la actuación")
	String getValoracionActuacion();
	
	@Schema(description = "dificultades de la actuación")
	String getDificultadesActuacion();
	
	@Schema(description = "comentarios de la actuación")
	String getComentariosActuacion();
	
	@Schema(description = "Descripcion del objetivo especifico")
	String getDescripcionObjetivoEspecifico();
	
}

