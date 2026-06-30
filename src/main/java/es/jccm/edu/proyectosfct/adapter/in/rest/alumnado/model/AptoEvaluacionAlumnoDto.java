package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FCT_EVAALU", description = "Descripcion para el modelo de apto evaluacion alumno")
public class AptoEvaluacionAlumnoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "ID")
	private Long id;
	
	@Schema(description = "Descripcion corta")
	private String descripcionCorta;
	
	@Schema(description = "Descripcion larga")
	private String descripcionLarga;

}
