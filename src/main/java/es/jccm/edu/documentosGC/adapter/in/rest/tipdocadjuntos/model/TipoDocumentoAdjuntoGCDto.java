package es.jccm.edu.documentosGC.adapter.in.rest.tipdocadjuntos.model;

import es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model.TipoDocumentoGCDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo documento adjunto GC", description = "Descripcion para el modelo de Tipo de Documento adjunto Gestión de Centros")
public class TipoDocumentoAdjuntoGCDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Orden")
	private Integer orden;	
	
	@Schema(description = "Es un tipo documento adjunto principal")
	private Integer principal;
	
	@Schema(description = "Es un tipo documento adjunto firmable")
	private Integer firmable;	
	
	@Schema(description = "Nombre")
	private String nombre;	
	
	@Schema(description = "Descripcion")
	private String descripcion;
	
	@Schema(description = "Tamano del adjunto")
	private Integer tamano;	
	
	@Schema(description = "Anno inicio")
	private Integer annoDesde;
	
	@Schema(description = "Anno fin")
	private Integer annoHasta;
	
	@Schema(description = "Tipo Documento")
	private TipoDocumentoGCDto tipoDocumento;	
}
