package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

/**
 * The Class FicheroAdjunto.
 */
public class FicheroAdjunto implements Serializable{
	private static final long serialVersionUID = 1L;

	/** The id. */
	private long id;
	
	/** The nombre. */
	private String nombre;
	
	/** The tamano. */
	private String tamano;
	
	/** The orden. */
	private int orden;
	
	private byte[] datos;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the tamano.
	 *
	 * @return the tamano
	 */
	public String getTamano() {
		return tamano;
	}

	/**
	 * Sets the tamano.
	 *
	 * @param tamano the new tamano
	 */
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	/**
	 * Gets the orden.
	 *
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Sets the orden.
	 *
	 * @param orden the new orden
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "FicheroAdjunto [id=" + id + ", nombre=" + nombre + ", tamano=" + tamano + ", orden=" + orden + "]";
	}


}
