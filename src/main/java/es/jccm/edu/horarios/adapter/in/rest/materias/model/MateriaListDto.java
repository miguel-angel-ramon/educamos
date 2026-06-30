package es.jccm.edu.horarios.adapter.in.rest.materias.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materia", description = "Entidad para rescatar las materias de un profesor")
public class MateriaListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Descripción")
	private String descripcion;
	

}
