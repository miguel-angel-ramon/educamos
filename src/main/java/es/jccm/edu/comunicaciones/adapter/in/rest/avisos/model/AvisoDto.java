package es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Aviso", description = "Avisos rescatados para el módulo de escritorio")
public class AvisoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del aviso")
	private Long idAviso;
	
	@Schema(description = "Id del colectivo")
	private Long idColectivo;
	
	@Schema(description = "Nombre del colectivo")
	private String colectivo;
	
	@Schema(description = "Título del aviso")
	private String titulo;
	
	@Schema(description = "Contenido del aviso")
	private String texto;
	
	@Schema(description = "Fecha de inicio de vigencia")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaInicioVigencia;
	
	@Schema(description = "Fecha de fin de vigencia")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaFinVigencia;
	
	@Schema(description = "Organismo de procedencia")
	private String procedencia;

}
