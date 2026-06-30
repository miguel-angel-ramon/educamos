package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

/**
 * The Class MensajeMiembroColectivo.
 */
public class MensajeMiembroColectivo implements Serializable{
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;
	
	/** The descripcion miembro. */
	private String descripcion;

	public MensajeMiembroColectivo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "MensajeMiembroColectivo [id=" + id + ", descripcion=" + descripcion + "]";
	}
}
