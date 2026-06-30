package es.jccm.edu.proyectosfct.application.domain.proyectos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado cursos", description = "Descripcion para el modelo de cursos")
public interface CursoModalidadProjection {
	
	@Schema(description = "Nombre del curso")
	String getCurso();
	
	@Schema(description = "Id de la oferta generica")
	Long getIdOfertamatrig();
	
	@Schema(description = "Orden")
	Long getOrden();

	@Schema(description = "Anno del curso actual")
	Integer getCAnno();

}
