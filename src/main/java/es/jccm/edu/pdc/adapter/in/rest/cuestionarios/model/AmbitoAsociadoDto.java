package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ambito Asociados", description = "Ambitos asociados a cada linea de actuacion")
public class AmbitoAsociadoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idCompetencia;
	
	private String desCompetencia;

	
}