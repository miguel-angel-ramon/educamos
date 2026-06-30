package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MensajeDetalle", description = "Detalles de un mensaje rescatado para el módulo de escritorio")
public class MensajeDetalleDto extends MensajeDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del mensaje")
	private Long idMensajeOrigen;
	
	@Schema(description = "Título del mensaje")
	private String tituloMensaje;
	
	@Schema(description = "Cuerpo del mensaje")
	private String cuerpoMensaje;
	
	@Schema(description = "Ficheros adjuntos del mensaje")
	private List<AdjuntoDto> ficherosAdjuntos;
	
	@Schema(description = "Hilo de conversación")
	private List<ConversacionDto> conversacion;
	
	@Schema(description = "Lista de destinatarios")
	private List<MensajeMiembroDto> listIdDestinatarios;
	
	@Schema(description = "Comprobación de respuesta múltiple")
	private String responderTodos;
	
	@Schema(description = "Comprobación de si el mensaje permite respuesta")
	private Boolean permiteRespuesta;
	
	@Schema(description = "Procedencia del mensaje")
	private String procedencia;
	
}
