package es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Estado documentos GC", description = "Descripcion para el modelo de Estados de Documentos Gestión de Centros")
public class EstadoDocumentoGCDto {
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Abreviatura")
	private String dsAbrev;	
	
	@Schema(description = "Nombre")
	private String dsNombre;	
	
	@Schema(description = "Fecha Inicio")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fhInicio;
	
	@Schema(description = "Fecha Inicio")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fhFin;
	
	@Schema(description = "Indica si el estado es un estado final para los documentos")
	private Boolean lgFinal;
	
	@Schema(description = "Texto que se mostrará en pantalla con consideraciones a tener en cuenta en este estado")
	private String txAviso;
	


}
