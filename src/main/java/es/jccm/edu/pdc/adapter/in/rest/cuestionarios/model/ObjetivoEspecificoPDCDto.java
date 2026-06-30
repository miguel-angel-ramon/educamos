package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaActuacionPDC;

@Data
@Schema(name = "Cuestionario", description = "Descripcion para el modelo de cuestionario")

public class ObjetivoEspecificoPDCDto implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	
	Integer idAmbito;

	String descripcion;
	
	Integer porcentajeTotal;
	
	List<LineaActuacionPDCDto> actuaciones;
}
