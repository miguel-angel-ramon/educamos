package es.jccm.edu.horarios.adapter.in.rest.dependencias.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Dependencia", description = "Entidad para rescatar las dependencias de un profesor")
public class DependenciaListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Descripción")
	private String descripcion;
	

}
