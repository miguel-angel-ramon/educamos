package es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipos bandeja", description = "Tipos documento bandeja de firmas documentos gc")
public class TipoAdjuntoBandejaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Nombre adjunto")
	private String nombre;	
	
	@Schema(description = "Orden del documento")
	private Integer ordendocumento;	
	
	@Schema(description = "Orden del adjunto")
	private Integer ordenadjunto;	

}
