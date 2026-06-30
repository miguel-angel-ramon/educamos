package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.util.Date;

import lombok.Data;

@Data
public class HistoricoFlujoAutorizacion {
	
	private String nombre;
	
	private String estado;
	
	private Date fecha;
	
	private String actual;
	
	private String observaciones;
	
	private String abreviatura;

}
