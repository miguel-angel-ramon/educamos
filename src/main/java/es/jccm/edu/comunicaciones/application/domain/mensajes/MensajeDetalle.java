package es.jccm.edu.comunicaciones.application.domain.mensajes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class MensajeDetalle extends Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idMensajeOrigen;

	private String tituloMensaje;

	private String cuerpoMensaje;

	private String responderTodos;

	private Boolean permiteRespuesta;

	private String procedencia;

	// ---------- Relationships -----------
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Adjunto> ficherosAdjuntos;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Conversacion> conversacion;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<MensajeMiembro> listIdDestinatarios;

}
