package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Lista tipo adjuntos", description = "Descripcion para el modelo de tipos de documentos adjuntos")
public class TipoAdjuntosListDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Tipo de adjunto")
	private Long idTipo;	
	
	@Schema(description = "Orden del adjunto")
	private Integer orden;	
	
	@Schema(description = "¿Es tipo de adjunto principal?")
	private Integer principal;	
	
	@Schema(description = "Nombre")
	private String nombre;	
	
	@Schema(description = "Descripcion")
	private String descripcion;	
	
	@Schema(description = "¿Es tipo firmable?")
	private Integer firmable;	
	
	@Schema(description = "Tamaño maximo del tipo de adjunto")
	private Integer tamano;	
	
	@Schema(description = "Año inicial de vigencia de tipo de adjunto")
	private Integer annodesde;	
	
	@Schema(description = "Año final de vigencia de tipo de adjunto")
	private Integer annohasta;	
	
	
	

}
