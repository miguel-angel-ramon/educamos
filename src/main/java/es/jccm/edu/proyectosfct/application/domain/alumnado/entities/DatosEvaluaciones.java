package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatosEvaluaciones implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String actividades;
	
	private String realizada;	
	
	private String norealizada;	
	
	private String capacidades;
	
	private String criterios;
	
	private String adquirida;
	
	private String area;
	
	private String noadquirida;
	
	private String observaciones;


}
