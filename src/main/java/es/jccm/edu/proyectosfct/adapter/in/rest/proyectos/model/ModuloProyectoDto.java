package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Modulos", description = "Descripcion para el modelo de asignar Modulos a proyectos")
public class ModuloProyectoDto {
	
	@Schema(description = "Id modulo curso")
	private Long id;

	@Schema(description = "Curso del modulo")
	private String curso;
	
	@Schema(description = "Codigo del modulo")
	private String codigo;
	
	@Schema(description = "Id oferta generica")
	private String idOfertamatrig;
	
	@Schema(description = "Id materia generica")
	private String idMateriaomg;
	
	@Schema(description = "Descripcion del modulo")
	private String modulo;	
	
	@Schema(description = "Horas totales")
	private Integer horasTotales;
	
	@Schema(description = "Horas semanales")
	private Integer horasSemanales;
	
	@Schema(description = "Horas centro")
	private Integer horasCentro;
	
	@Schema(description = "Horas empresa")
	private Integer horasEmpresa;

	@Schema(description = "Número de actividades asociadas")
	private Integer actividades;

	@Schema(description = "Flag que indica si el módulo puede o no borrarse")
	private Integer puedeEliminar;
}
