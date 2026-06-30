package es.jccm.edu.pdc.application.domain.evaluacion.projection;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;

@Projection(name = "Evaluacion", types = {EvaluacionHome.class})
@Schema(name = "Evaluacion", description = "Proyección para rescatar los datos de la Evaluacion")
public interface EvaluacionHomeProjection {

	@Schema(description = "Id del Seguiemiento Linea Actuacion")
	Long getXSeguiLinAct();
	
	@Schema(description = "Porcentaje ejecutado")
	Integer getPorcEjec();
	
	@Schema(description = "Estado de la tarea")
	String getEstado();
	
	@Schema(description = "Fecha de actualizacion")
	Date getFactualizacion();
}