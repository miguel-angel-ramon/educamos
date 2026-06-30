package es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoEstadoDocumentoGCDto", description = "Descripcion para el modelo de listado estados Dto")
public class ListadoEstadoDocumentoGCDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del estado de documento")
	private Long id;

	@Schema(description = "Abreviatura del tipo")
	private String abreviatura;
	
	@Schema(description = "Nombre del tipo")
	private String nombre;
	
	@Schema(description = "Fecha inicio estado")
	private Date fInicio;
	
	@Schema(description = "Fecha fin estado")
	private Date fFin;
	
	@Schema(description = "Es final")
	private String esfinal;
	
	@Schema(description = "Es borrable")
	private Integer noborrable;
	
}
