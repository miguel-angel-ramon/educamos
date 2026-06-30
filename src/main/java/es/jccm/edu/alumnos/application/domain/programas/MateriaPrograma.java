package es.jccm.edu.alumnos.application.domain.programas;

import java.io.Serializable;

import lombok.Data;

@Data
public class MateriaPrograma implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String abreviatura;
	private Long idMateria;
	private String programa;
	private Long idMateriaMatricula;
	private int orden;
	
	
	
}
