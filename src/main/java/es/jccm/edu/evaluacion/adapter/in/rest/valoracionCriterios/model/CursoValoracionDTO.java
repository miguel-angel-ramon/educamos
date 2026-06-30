package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.CursoProgramacionAulaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Curso valoración", description = "DTO para rescatar los cursos que imparte el centro")
public class CursoValoracionDTO {
	
	@Schema(description = "Id de la Oferta Matrícula Genérico")
	private Long idOfertamatrig;
	   
	@Schema(description = "Descripción corta de la Oferta Matrícula Genérico")
	private String descripcionCorta;

}
