package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.util.Date;

import lombok.Data;

@Data
public class HistoricoFlujoGastos {
	
	private String nombre;
	
	private String estado;
	
	private Date fecha;
	
	private String actual;
	
	private String observaciones;
	
	private String abreviatura;

}
