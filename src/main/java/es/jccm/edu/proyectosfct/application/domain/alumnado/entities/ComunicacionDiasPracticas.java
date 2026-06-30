package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import lombok.Data;

@Data
public class ComunicacionDiasPracticas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "Número de días efectivos de prácticas formativas")
	private Date fechaComunicacionDatos;	
	
	@Column (name = "Dni, Nie o pasaporte")
	private String dni;
	
	@Column (name = "Número de la Seguridad Social")
	private String tNuss;
	
	@Column (name = "Apellidos")
	private String apellidos;
	
	@Column (name = "Nombre")
	private String nombre;	
	
	@Column(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasRealizados;
	
	@Column(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasInc;
	
	@Column(name = "Número de días efectivos de prácticas formativas")
	private Integer numDiasEmb;
	
}
