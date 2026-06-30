package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a proyecto", description = "Descripcion para el modelo de asignar convenios a proyectos")
public class CursoModalidadDto {

	@Schema(description = "Nombre del curso")
	private String curso;
	
	@Schema(description = "Id de la oferta generica")
	private Long idOfertamatrig;
	
	@Schema(description = "Orden")
	private Long orden;

	@Schema(description = "Anno del curso actual")
	private Integer cAnno;
	
}
