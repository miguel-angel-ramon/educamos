package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "MensajeDetalle", description = "Detalles de un mensaje rescatado para el módulo de escritorio")
public class MensajeEnviarDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** The id mensaje origen. */
	private Long idMensajeOrigen;
	
	/** The cuerpo mensaje. */
	private String cuerpoMensaje;
	
	/** The adjuntos. */
	private List<FicheroAdjunto> ficherosAdjuntos;
	
	/** The conversacion. */
	private List<MensajeConversacionPrevia> conversacion;
	
	/** The id destinatario. */
	private List<MensajeMiembroColectivo> listIdDestinatarios;
	
	/** The permite respuesta. */
	private Boolean permiteRespuesta;
	
	private String procedencia;
	
	/** The id. */
	private Long id;
	
	/** The id destinatario mensaje. */
	private Long idDestinatarioMensaje;
	
	/** The asunto. */
	private String asunto;
	
	/** The id remitente. */
	private Long idRemitente;
	
	/** The remitente. */
	private String remitente;
	
	/** The id grupo. */
	private Long idGrupo;
	
	/** The grupo. */
	private String grupo;
	
	/** The respuesta. */
	private Boolean respuesta;
	
	/** The fecha mensaje. */
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaMensaje; //Fecha de envío para enviados, fecha de recepción para recibidos. Pero la fecha es única (sólo se guarda una fecha)
	
	/** The leido. */
	private Boolean leido;
	
	/** The adjuntos. */
	private Boolean adjuntos;
	
	/** The destinatarios. */
	private List<MensajeMiembroColectivo> destinatarios;
	
	/** The respondido. */
	private Boolean respondido;
	
	/** The numero destinatarios. */
	private Integer numeroDestinatarios;
	
	/** The numero leidos. */
	private Integer numeroLeidos;
	
	/** The borrado para todos. */
	private Boolean borradoParaTodos;
	
}
