package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "tldocumentos", description = "tldocumentos")
public class TlDocumentoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Id tipo documento")
	private Long idTipDoc;
	
	@Schema(description = "Documento")
	private String documento;
	
	@Schema(description = "Is activo")
	private String activo;
	
	@Schema(description = "Orden de presentacion")
	private Integer ordenPresentacion;
	
	@Schema(description = "Id documento padre")
	private Long idDocumentoPadre;
	
	@Schema(description = "Is disponible")
	private String disponible;
	
	@Schema(description = "Rama final de presentacion")
	private String ramaFinalPresentacion;
	
	@Schema(description = "Modulo")
	private String modulo;

	@Schema(description = "Is registable")
	private String registable;	
	
	@Schema(description = "Validador del documento")
	private String claValDoc;		
	
	@Schema(description = "Tiene diligencia")
	private String diligencia;		
	
	@Schema(description = "Puede ser emitido desde un centro de adscripcion")
	private String centroAdscripcion;	
	
	@Schema(description = "Puede ser generado desde el modulo de dgc")
	private Integer actaDgc;	

}
