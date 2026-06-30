package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materia curso oferta generica", description = "Materia curso oferta generica ")
public class MateriaCursoOfertaMatriculaGenericaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la materia")
	private Long id;
	
	@Schema(description = "horas semanales")
	private Long nHoras;
	
	@Schema(description = "horas anuales")
	private Long nHorasAnuales;
	
	
	// ---------- Relationships -----------
	@Schema(description = "Id de la materia curso interno")
	private MateriaCursoGenericaDto materiaCurso;
	
}
