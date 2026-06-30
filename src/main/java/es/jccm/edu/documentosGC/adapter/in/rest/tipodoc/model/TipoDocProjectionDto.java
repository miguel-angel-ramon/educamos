package es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo documentos GC", description = "Descripcion para el modelo de Tipos de Documentos Gestión de Centros")
public class TipoDocProjectionDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Número de orden")
	private Integer orden;
	
	@Schema(description = "Abreviatura estado")
	private String abrev;
	
	@Schema(description = "Descripcion estado")
	private String descripcion;
	
	

}
