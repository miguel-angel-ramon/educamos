package es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoTipoDocumentoGCDto", description = "Descripcion para el modelo de listado tipo documentos Dtp")
public class ListadoTipoDocumentosGCDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del tipo de documento")
	private Long id;

	@Schema(description = "Orden de presentacion")
	private Integer orden;
	
	@Schema(description = "Abreviatura del tipo")
	private String abrev;

	@Schema(description = "Descripcion del tipo")
	private String descripcion;
	
	@Schema(description = "Es anual")
	private Integer anual;
	
	@Schema(description = "Anno de inicio")
	private Integer annodesde;
	
	@Schema(description = "Anno de fin")
	private Integer annohasta;
	
	@Schema(description = "Obligatorio")
	private String lgObligatorio;
	
	@Schema(description = "El tipo de documento")
	private Integer noborrable;
	
	@Schema(description = "Nombre padre")
	private String nombrepadre;
	
}

