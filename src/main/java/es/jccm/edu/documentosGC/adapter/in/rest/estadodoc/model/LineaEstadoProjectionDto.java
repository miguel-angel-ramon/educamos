package es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Linea estado", description = "Descripcion para el modelo línea de estado del documento")
public class LineaEstadoProjectionDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id estado")
	private Long idHistorial;
	
	@Schema(description = "Id estado")
	private Long idEstado;
	
	@Schema(description = "Descripción estado")
	private String nombre;
	
	@Schema(description = "Fecha de registro del estado")
	private Date fhregistro;
	
	@Schema(description = "Estado actual del documento")
	private String lactual;
	
	@Schema(description = "Nivel en la linea del documento")
	private Integer nivel;
	
	@Schema(description = "Nivel final en la linea del documento")
	private Integer lgfinal;
	
	@Schema(description = "El estado es histórico")
	private Integer lhistorico;
	
	@Schema(description = "Abreviatura estado actual del documento")
	private String abrev;

}

