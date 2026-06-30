package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Conversacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idMensaje;
	
	private String bandeja;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaMensaje;
	
	private String asunto;
	
	private String remitente;

}
