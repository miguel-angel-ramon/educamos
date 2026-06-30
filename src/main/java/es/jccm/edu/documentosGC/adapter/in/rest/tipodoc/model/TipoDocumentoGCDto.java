package es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo documentos GC", description = "Descripcion para el modelo de Tipos de Documentos Gestión de Centros")
public class TipoDocumentoGCDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Orden")
	private Integer orden;	
	
	@Schema(description = "Abreviatura")
	private String abrev;	
	
	@Schema(description = "Descripcion")
	private String descripcion;	
	
	@Schema(description = "Anual")
	private Integer anual;
	
	@Schema(description = "Anno inicio")
	private Integer annodesde;
	
	@Schema(description = "Anno fin")
	private Integer annohasta;
	
	@Schema(description = "Tipo de documento obligatorio S = Es obligatorio, N = No obligaorio")
	private Integer lgObligatorio;
	
	@Schema(description = "Id Tipo Documento Padre")
	private Long idTipoDocumentoPadre;
	
	//@Schema(description = "Documento padre")
	//private TipoDocumentosGCDto idTipoDocumentoPadre;
	
	//@Schema(description = "listado documentos")
	//private List<TipoDocumentosGCDto> listTipoDocumentoPadre;

}
