package es.jccm.edu.pdc.application.domain.planActuacion.projection;



import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Objetivos específicos", description = "Proyección para rescatar los objetivos específicos")
public interface ObjetivoEspecificoActualizadoProjection {

	@Schema(description = "x_competencia al que pertenece el objetivo")
	Integer getIdAmbito();
	
	@Schema(description = "Id del objetivo especifico")
	Integer getIdObjetivoEsp();

	@Schema(description = "Descripción del objetivo específico")
	String getDescripcionObj();
	
	@Schema(description = "Id linea actuación")
	Integer getIdLinAct();
	
	@Schema(description = "Titulo de la actuación")
	String getTituloActuacion();
	
	@Schema(description = "Descripción actuación")
	String getDescipcionActuacion();
	
	@Schema(description = "Fecha creacion actuación")
	Date getFechaCreacionActuacion();
	
	@Schema(description = "Fecha inicio actuación")
	Date getFechaInicioActuacion();
	
	@Schema(description = "Fecha fin actuación")
	Date getFechaFinActuacion();
	
	@Schema(description = "Responsable actuación")
	String getResponsableActuacion();
	
	@Schema(description = "Logra actuación")
	String getLogroActuacion();
	
	@Schema(description = "Instrumentos actuación")
	String getInstrumentosActuacion();
	
	@Schema(description = "Porcentaje actuación")
	Integer getPorcActuacion();
	
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
}

