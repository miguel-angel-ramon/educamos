package es.jccm.edu.proyectosfct.adapter.in.rest.programas.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ElementoSelectDto", description = "Descripcion para el modelo Select")
public class ElementoSelectDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private long id;
	
	@Schema(description = "Descripcion")
	private String descripcion;

}
