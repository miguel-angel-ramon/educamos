package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Curso valoración", description = "Proyección para rescatar los cursos que imparte el centro")
public interface CursoValoracionProjection {
	
	@Schema(description = "Id de la Oferta Matrícula Genérico")
	Long getIdOfertamatrig();
	   
	@Schema(description = "Descripción corta de la Oferta Matrícula Genérico")
	String getDescripcionCorta();


}
