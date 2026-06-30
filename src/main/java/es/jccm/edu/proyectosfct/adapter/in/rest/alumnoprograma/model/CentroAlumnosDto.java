package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo del centro")
public class CentroAlumnosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del centro")
	private Long id;
	
	@Schema(description = "Nombre completo del centro")
	private String nombreCompleto;
	
	@Schema(description = "Nombre completo del centro mas la provincia")
	private String centroProvincia;
	


}
