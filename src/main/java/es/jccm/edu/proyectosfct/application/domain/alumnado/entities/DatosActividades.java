package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatosActividades implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String txactividad;	

}
