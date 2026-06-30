package es.jccm.edu.documentosGC.adapter.in.rest.centrodoc.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CentroDocumentosGCDto", description = "Descripcion del Centro para el modelo de Documentos de Gestión de Centro")
public class CentroDocumentosGCDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;

	@Schema(description = "Codigo Centro")
	private Long codigoCentro;
}
