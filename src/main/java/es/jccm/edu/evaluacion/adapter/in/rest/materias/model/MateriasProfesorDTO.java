package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriasProfesor", description = "Materias de un profesor para el proceso de evaluación")
public class MateriasProfesorDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "IdMateria")
	private Long idMateria;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Descripción")
	private String descripcion;
	

}
