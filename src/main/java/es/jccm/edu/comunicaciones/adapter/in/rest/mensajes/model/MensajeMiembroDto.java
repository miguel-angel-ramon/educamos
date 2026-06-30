package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MensajeMiembro", description = "Destinatario de un mensaje rescatado para el módulo de escritorio")
public class MensajeMiembroDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del destinatario")
	private Long id;
	
	@Schema(description = "Descripción")
	private String descripcion;
	
}
