package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatosHojaSemanal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String jornadas;
	
	private String actividades;	
	
	private String tiempos;	
	
	private String observaciones;
	
	private String nhoras;
	
	private Integer nuDias;


}
