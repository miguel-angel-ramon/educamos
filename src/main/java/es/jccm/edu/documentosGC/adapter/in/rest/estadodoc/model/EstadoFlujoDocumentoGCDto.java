package es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "EstadoFlujoDocumentoGCDto", description = "Descripcion para el modelo de flujo de estados")
public class EstadoFlujoDocumentoGCDto {
	
	@Schema(description = "Id")
	private Long idFlujo;
	
	@Schema(description = "Abreviatura del estado documento")
	private String dsAbrev;
	
	@Schema(description = "Nombre del estado documento")
	private String dsNombre;
	
	@Schema(description = "Fecha de inicio estado documento")
	private Date fhInicio;
	
	@Schema(description = "Fecha de fin estado documento")
	private Date fhFin;
	
	@Schema(description = "¿Estado final?")
	private Integer lgFinal;
	
	@Schema(description = "Texto de aviso")
	private String txAviso;
	
	@Schema(description = "Requiere adjunto 0/1")
	private Integer adjunto;
	
	

}



