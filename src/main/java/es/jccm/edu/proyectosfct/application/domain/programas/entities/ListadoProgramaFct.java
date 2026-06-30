package es.jccm.edu.proyectosfct.application.domain.programas.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoProgramaFct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	private String tutor;	
	
	private String provincia;	
	
	private Long centro;	
	
	private String familia;
	
	private String ofertaMatriculaGenerico;
	
	private Long idTutorFct;
	
	private String convenio;

}
