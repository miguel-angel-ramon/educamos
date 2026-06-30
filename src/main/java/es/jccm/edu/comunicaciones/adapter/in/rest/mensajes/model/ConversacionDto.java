package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Conversación", description = "Conversación de un mensaje rescatado para el módulo de escritorio")
public class ConversacionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del mensaje al que pertenece la conversación")
	private Long idMensaje;
	
	@Schema(description = "Bandeja de la conversación")
	private String bandeja;
	
	@Schema(description = "Fecha del mensaje")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private Date fechaMensaje;
	
	@Schema(description = "Asunto del mensaje")
	private String asunto;
	
	@Schema(description = "Remitente del mensaje")
	private String remitente;
	
}
