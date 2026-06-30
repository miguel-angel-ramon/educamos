package es.jccm.edu.proyectosfct.adapter.in.rest.modalidades.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ModalidadDto", description = "Descripcion para el modelo de Modalidades")
public class ModalidadDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id")
	private Long id;
	
	@Schema(description = "Descripcion corta modalidad")
	private String descripcionCorta;
	
	@Schema(description = "Descripcion larga modalidad")
	private String descripcionLarga;

}
