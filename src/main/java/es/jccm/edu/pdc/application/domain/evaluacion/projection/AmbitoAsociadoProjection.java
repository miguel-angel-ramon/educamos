package es.jccm.edu.pdc.application.domain.evaluacion.projection;


import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.data.rest.core.config.Projection;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;

@Projection(name = "Ambito Asociado", types = {AmbitoAsociado.class})
@Schema(name = "Ambito Asociado", description = "Proyección para rescatar los datos de los Ambitos asociados")
public interface AmbitoAsociadoProjection {

	@Schema(description = "Id competencia")
	Long getIdCompetencia();
	
	@Schema(description = "Competencia")
	String getDesCompetencia();
}