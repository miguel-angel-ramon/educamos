package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.OfertaMatriculaGenericoDto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a proyecto", description = "Descripcion para el modelo de asignar convenios a proyectos")
public class CursoProyectoDto {

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Horas totales")
	private Integer horasTotales;
	
	@Schema(description = "Horas centro")
	private Integer horasCentro;
	
	@Schema(description = "Horas empresa")
	private Integer horasEmpresa;
	
	@Schema(description = "Practivas")
	private String practivas;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Proyecto")
	private Proyectos proyecto;
	
	@Schema(description = "Id de la oferta generica")
	private OfertaMatriculaGenericoDto idOfertamatrig;
	
}
