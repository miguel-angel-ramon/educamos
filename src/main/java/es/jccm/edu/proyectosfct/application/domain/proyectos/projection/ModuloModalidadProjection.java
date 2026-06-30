package es.jccm.edu.proyectosfct.application.domain.proyectos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado cursos", description = "Descripcion para el modelo de cursos")
public interface ModuloModalidadProjection {
	
	@Schema(description = "Codigo del modulo")
	String getCodigo();
	
	@Schema(description = "Id materia generica")
	Long getIdMateriaomg();
	
	@Schema(description = "Nombre del modulo")
	String getModulo();
	
	@Schema(description = "Horas anuales")
	Integer getHorasAnuales();
	
	@Schema(description = "Horas semanales")
	Integer getHorasSemanales();
}
