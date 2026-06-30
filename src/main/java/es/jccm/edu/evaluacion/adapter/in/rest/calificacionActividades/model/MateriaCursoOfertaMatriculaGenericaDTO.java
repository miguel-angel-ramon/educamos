package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriaCursoOfertaMatriculaGenericaDTO", description = "DTO Materia Curso Oferta Matrícula Genérica")
public class MateriaCursoOfertaMatriculaGenericaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Materia Curso Oferta Matrícula Genérica")
	private Long id;

	@Schema(description = "Número de horas")
	private Long nHoras;
	
	@Schema(description = "Número de horas anuales")
	private Long nHorasAnuales;
	
	// ---------- Relationships -----------

	@Schema(description = "Identificador de la Materia Curso")
	private MateriaCursoGenericaDTO materiaCursoGenerica;

}