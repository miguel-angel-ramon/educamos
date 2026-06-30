package es.jccm.edu.pdc.application.domain.evaluacion.projection;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.LineaActuacionDto;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;

@Projection(name = "Evaluacion", types = {EvaluacionHome.class})
@Schema(name = "Evaluacion", description = "Proyección para rescatar los datos de la Evaluacion")
public interface ObjetivoEspecificoEvaProjection {

	@Schema(description = "Id Objetivo especifico")
	Long getIdObjEsp();
	
	@Schema(description = "Id Objetivo")
	Long getIdObjetivo();
	
	@Schema(description = "Id Centro")
	Long getIdCentro();
	
	@Schema(description = "Descripcion")
	String getDescripcion();
	
	@Schema(description = "Año Actual")
	Integer getAnno();
}