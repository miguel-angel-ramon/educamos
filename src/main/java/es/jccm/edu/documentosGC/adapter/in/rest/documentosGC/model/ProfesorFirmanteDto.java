package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Profesor", description = "Descripcion para el modelo Profesor firmante")
public class ProfesorFirmanteDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador del empleado")
	private Long id;
	
	@Schema(description = "Tipo de firmante")
	private String tipo;

}
