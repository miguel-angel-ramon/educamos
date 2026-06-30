package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriaCursoGenericaDTO", description = "DTO Materia Curso Genérica")
public class MateriaCursoGenericaDTO {
	
	@Schema(description = "Id de la Materia Curso Genérica")
	private Long id;

	@Schema(description = "Descripción larga de la Materia Curso Genérica")
	private String descripcionMateria;
}