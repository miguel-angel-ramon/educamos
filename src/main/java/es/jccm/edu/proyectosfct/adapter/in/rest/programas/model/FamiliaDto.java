package es.jccm.edu.proyectosfct.adapter.in.rest.programas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FamiliaDto", description = "Descripcion para el modelo de Familias FCT")
public class FamiliaDto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del programa")
	private long id;
	
	@Schema(description = "Descripcion larga familia")
	private String descripcionLarga;
	
	@Schema(description = "Descripcion corta familia")
	private String descripcionCorta;

}
