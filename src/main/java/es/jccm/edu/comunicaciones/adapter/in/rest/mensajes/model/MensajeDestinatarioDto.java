package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MensajeDestinatario", description = "Destinatarios rescatados para el módulo de escritorio")
public class MensajeDestinatarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del destinatario")
	private Integer idDestinatario;
	
	@Schema(description = "Nombre completo del destinatario")
	private String nombreCompleto;
	
	@Schema(description = "Destinatario es marcado como leído")
	private Boolean isLeido;

}
