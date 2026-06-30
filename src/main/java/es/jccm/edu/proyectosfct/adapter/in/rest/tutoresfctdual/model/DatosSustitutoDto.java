package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Datos de un sustituto", description = "Descripcion para el modelo que determina los datos necesarios para un sustituto")
public class DatosSustitutoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del sustituto")
	private Long id;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "Identificador del profesor al que sustituye")
	private Long idTutorFctDual;
	
	@Schema(description = "Identificador del profesor sustituto")
	private Long idTutorFctDualSus;
	
	

}
