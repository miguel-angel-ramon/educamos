package es.jccm.edu.proyectosfct.adapter.in.rest.rodal.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RodalDoc", description = "Descripcion para el modelo de documento de Rodal")
public class RodalDocDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del documento en rodal")
	private String idDoc;
	
	@Schema(description = "Nombre del documento en rodal")
	private String nombre;

}
