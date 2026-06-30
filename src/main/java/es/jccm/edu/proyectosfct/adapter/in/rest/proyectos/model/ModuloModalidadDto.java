package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Modulos", description = "Descripcion para el modelo de asignar Modulos a proyectos")
public class ModuloModalidadDto {

	@Schema(description = "Codigo del modulo")
	private String codigo;
	
	@Schema(description = "Id materia generica")
	private Long idMateriaomg;
	
	@Schema(description = "Nombre del modulo")
	private String modulo;
	
	@Schema(description = "Horas anuales")
	private Integer horasAnuales;
	
	@Schema(description = "Horas semanales")
	private Integer horasSemanales;
	
}
