package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materia curso generica", description = "Materia curso generica ")
public class MateriaCursoGenericaDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la materia curso interno")
	private Long id;
	
	
	@Schema(description = "descripcion materia curso interno")
	private String descripcionMateria;
	
}
