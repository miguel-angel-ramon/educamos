package es.jccm.edu.documentosGC.adapter.in.rest.centrodoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CentroDocInspeccionDto", description = "Descripcion del Centro para el inspector en documentos programaticos")
public class CentroDocInspeccionDto {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "x_centro")
	private Long x_centro;

	@Schema(description = "descripcion")
	private String descripcion;
}
