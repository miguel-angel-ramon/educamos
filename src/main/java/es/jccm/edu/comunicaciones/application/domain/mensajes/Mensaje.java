package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long idDestinatarioMensaje;

	private String asunto;

	private String cuerpoMensaje;

	private Long idRemitente;

	private String remitente;

	private Long idGrupo;

	private String grupo;

	private String destinatarios;

	private Boolean respuesta;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaMensaje;

	private Boolean leido;

	private Boolean adjuntos;

	private Boolean respondido;

	private Long numeroDestinatarios;

	private Long numeroLeidos;

	private Boolean borradoParaTodos;
	
	@Lob
	private byte[] fotoRemitente;

}
