package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Historial", description = "Pantalla que muestra el historial de un documento")
public class HistDocumentosGCDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id_documento")
	private Long id;
	
	@Schema(description = "fecha")
	private Date fecha;
	
	@Schema(description = "Usuario")
	private String usuario;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Id rodal del documento")
	private String idRodal;
	
	@Schema(description = "Documento")
	private String documento;
	
	@Schema(description = "Comentarios")
	private String comentarios;
	
	@Schema(description = "Identificador del adjunto")
	private Long idadjunto;
	
	@Schema(description = "¿El documento es firmable?")
	private Integer lgfirmable;
	
	@Schema(description = "¿El documento está firmado?")
	private String lgfirmado;
	

}
