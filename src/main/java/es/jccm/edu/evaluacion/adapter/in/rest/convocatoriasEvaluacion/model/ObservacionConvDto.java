package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.ObservacionPKConv;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Observaciones", description = "Proyección para rescatar las observaciones de la evaluación")
public class ObservacionConvDto {
	
	@Schema(description = "id")
	ObservacionPKConv id;
	
	@Schema(description = "Observaciones de la evaluación")
	String observaciones;

}
