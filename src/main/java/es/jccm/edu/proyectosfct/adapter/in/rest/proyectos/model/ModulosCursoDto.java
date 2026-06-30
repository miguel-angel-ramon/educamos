package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoProyecto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a proyecto", description = "Descripcion para el modelo de asignar convenios a proyectos")
public class ModulosCursoDto implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Horas totales")
	private Integer horasTotales;
	
	@Schema(description = "Horas semanales")
	private Integer horasSemanales;
	
	@Schema(description = "Horas centro")
	private Integer horasCentro;
	
	@Schema(description = "Horas empresa")
	private Integer horasEmpresa;

	@Schema(description = "Valor lógico para saber si es un módulo nuevo")
	private Integer esInsert;

	@Schema(description = "Valor lógico para saber si es un módulo que se elimina")
	private Integer esDelete;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Curso a Proyectos")
	private CursoProyecto cursoProyecto;
	
	@Schema(description = "Id de la materia")
	private MateriaCursoOfertaMatriculaGenericaDto idMateriaomg;
	
}
