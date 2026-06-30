package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Profesor", description = "Descripcion para el modelo Profesor")
public class ProfesorActaEvaluacionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de la convocatoria unidad")
	private Long id;
	
	@Schema(description = "Nombre del profesor")
	private String nombre;
	
	@Schema(description = "Nombre del departamento del profesor")
	private String nombreDepartamento;
	
}
