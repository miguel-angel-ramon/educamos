package es.jccm.edu.alumnos.application.domain.programas;


import lombok.Data;

@Data
public class MateriaAsociada  {
	
	private String abreviatura;
	
	private Long idMateria;
	
	private Long idPrograma;
	
	private int orden;

}
