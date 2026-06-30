package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "partes", description = "Partes mensuales DTO")
public class PartesMensualesDocumentosGCDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private String id;
	
	@Schema(description = "Descripcion plazo entrega")
	private String descripcion;	

}
