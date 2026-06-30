package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoProyectosDto", description = "Descripcion para el modelo de proyectos dto")
public class TipoProyectosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del tipo proyecto")
	private Long id;
	
	@Schema(description = "descripcion abreviada tipo proyecto")
	private String ds_abrev;
	
	@Schema(description = "descripción nombre")
	private String ds_nombre;
	

}
