package es.jccm.edu.proyectosfct.application.domain.programas.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoAnexoIAlumnadoFct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String numide;	
	
	private String residencia;	
	
	private String franjas;
	
	private String numHoras;
	
	private String fechaIni;
	
	private String fechaFin;
	
	private Integer numDias;

}
