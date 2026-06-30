package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Curso", description = "Descripcion para el modelo Materia curso")
public class MateriacDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador curso")
	private Long id;
	
	@Schema(description = "Descripcion curso")
	private String descripcion;

}
