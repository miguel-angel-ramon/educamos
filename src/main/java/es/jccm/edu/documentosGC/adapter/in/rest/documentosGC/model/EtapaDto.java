package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Etapa", description = "Descripcion para el modelo Etapa ")
public class EtapaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador etapa")
	private Long id;
	
	@Schema(description = "Descripcion etapa")
	private String descripcion;

}
