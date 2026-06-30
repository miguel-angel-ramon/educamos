package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Mensaje", description = "Mensajes rescatados para el módulo de escritorio")
public class MensajeDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del mensaje")
	private Long id;
	
	@Schema(description = "Id del usuario destinatario")
	private Long idDestinatarioMensaje;
	
	@Schema(description = "Asunto del mensaje")
	private String asunto;
	
	@Schema(description = "Cuerpo del mensaje")
	private String cuerpoMensaje;
	
	@Schema(description = "Id del usuario remitente")
	private Long idRemitente;
	
	@Schema(description = "Nombre completo del usuario remitente")
	private String remitente;
	
	@Schema(description = "Id del grupo")
	private Long idGrupo;
	
	@Schema(description = "Nombre del grupo")
	private String grupo;
	
	@Schema(description = "Nombre de los destinatario")
	private String destinatarios;
	
	@Schema(description = "Estado de mensaje respondido")
	private Boolean respuesta;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Schema(description = "Fecha del mensaje")
	private Date fechaMensaje;
	
	@Schema(description = "Comprobación de lectura")
	private Boolean leido;
	
	@Schema(description = "Comprobación de ficheros adjuntos")
	private Boolean adjuntos;
	
	@Schema(description = "Comprobación de respuesta")
	private Boolean respondido;
	
	@Schema(description = "Número de destinatarios")
	private Long numeroDestinatarios;
	
	@Schema(description = "Número destinatarios que han leído el mensaje")
	private Long numeroLeidos;
	
	@Schema(description = "Comprobación de borrado para todos")
	private String borradoParaTodos;
	
	@Schema(description = "Foto del remitente")
	private byte[] fotoRemitente;

}
