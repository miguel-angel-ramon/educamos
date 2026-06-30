package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Adjunto", description = "Archivo adjunto a un mensaje rescatado para el módulo de escritorio")
public class AdjuntoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del archivo adjunto")
	private Long id;
	
	@Schema(description = "Nombre del archivo adjunto")
	private String nombre;
	
	@Schema(description = "Tamaño del archivo adjunto")
	private String tamano;
	
	@Schema(description = "Posición que ocupa el adjunto en la lista de archivos enviados")
	private Integer orden;
	
	@Schema(description = "Datos del archivo adjunto")
	private  byte[] datos;
	
}
