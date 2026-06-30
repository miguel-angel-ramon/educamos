package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoProyectos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ds_provincia;	
	
	private String ds_centro;
	
	private String ds_tipo;
	
	private String ds_proyecto;	
	
	private String ds_tutor;	
	
	private String ds_familia;
	
	private String ds_modalidad;
	
	private Integer nu_horas;
	
	private Integer nu_alumnos;
	
	private Long idTutorFct;

	private String ds_curso;

	private Integer lg_lofp;

	private Integer actividades;

	private Integer lg_copiar;
}
