package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class MensajeConversacionPrevia.
 */
public class MensajeConversacionPrevia implements Serializable{
	private static final long serialVersionUID = 1L;

	/** The id mensaje. */
	private Long idMensaje;
	
	/** The bandeja. */
	private String bandeja;
	
	/** The fecha mensaje. */
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "es-ES", timezone = "Europe/Madrid")	
	private Date fechaMensaje;
	
	/** The asunto. */
	private String asunto;
	
	/** The remitente. */
	private String remitente;

	/**
	 * Gets the id mensaje.
	 *
	 * @return the id mensaje
	 */
	public Long getIdMensaje() {
		return idMensaje;
	}

	/**
	 * Sets the id mensaje.
	 *
	 * @param idMensaje the new id mensaje
	 */
	public void setIdMensaje(Long idMensaje) {
		this.idMensaje = idMensaje;
	}

	/**
	 * Gets the bandeja.
	 *
	 * @return the bandeja
	 */
	public String getBandeja() {
		return bandeja;
	}

	/**
	 * Sets the bandeja.
	 *
	 * @param bandeja the new bandeja
	 */
	public void setBandeja(String bandeja) {
		this.bandeja = bandeja;
	}

	/**
	 * Gets the fecha mensaje.
	 *
	 * @return the fecha mensaje
	 */
	public Date getFechaMensaje() {
		return fechaMensaje;
	}

	/**
	 * Sets the fecha mensaje.
	 *
	 * @param fechaMensaje the new fecha mensaje
	 */
	public void setFechaMensaje(Date fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}

	/**
	 * Gets the asunto.
	 *
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Sets the asunto.
	 *
	 * @param asunto the new asunto
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * Gets the remitente.
	 *
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}

	/**
	 * Sets the remitente.
	 *
	 * @param remitente the new remitente
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "MensajeConversacionPrevia [idMensaje=" + idMensaje + ", bandeja=" + bandeja + ", fechaMensaje="
				+ fechaMensaje + ", asunto=" + asunto + ", remitente=" + remitente + "]";
	}
	
	
}
