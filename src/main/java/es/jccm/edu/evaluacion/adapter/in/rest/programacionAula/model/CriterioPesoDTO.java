package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CriterioPesoDTO", description = "DTO Criterio Peso")
public class CriterioPesoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del criterio")
	private Long idCriterio;
	
	@Schema(description = "Abreviatura del criterio")
	private String abrevCriterio;

	@Schema(description = "Peso del criterio")
	private Integer peso;
	
	@Schema(description = "valor logico de ponderacion")
	private String lPonderada;
	
}
