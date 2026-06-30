package es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipos bandeja", description = "Tipos documento bandeja de firmas documentos gc")
public class TipoDocumentoBandejaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Abreviatura tipo")
	private String abreviatura;	
	
	@Schema(description = "Descripcion tipo")
	private String descripcion;	
	
	@Schema(description = "Orden del tipo")
	private Integer orden;	
	
	@Schema(description = "Orden del tipo padre")
	private Integer ordenpadre;	

}


