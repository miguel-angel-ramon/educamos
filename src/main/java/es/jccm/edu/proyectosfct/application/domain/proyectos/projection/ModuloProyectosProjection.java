package es.jccm.edu.proyectosfct.application.domain.proyectos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado proyectos", description = "Descripcion para el modelo de proyectos de un centro")
public interface ModuloProyectosProjection {
	
	@Schema(description = "id modulo curso")
	Long getId();
	
	@Schema(description = "Curso del modulo")
	String getCurso();
	
	@Schema(description = "Codigo del modulo")
	String getCodigo();
	
	@Schema(description = "Id oferta generica")
	String getIdOfertamatrig();
	
	@Schema(description = "Id materia generica")
	String getIdMateriaomg();
	
	@Schema(description = "Nombre del modulo")
	String getModulo();	
	
	@Schema(description = "Horas totales")
	Integer getHorasTotales();
	
	@Schema(description = "Horas semanales")
	Integer getHorasSemanales();
	
	@Schema(description = "Horas centro")
	Integer getHorasCentro();
	
	@Schema(description = "Horas empresa")
	Integer getHorasEmpresa();

	@Schema(description = "Número de actividades asociadas")
	Integer getActividades();

	@Schema(description = "Flag que indica si el módulo puede o no borrarse")
	Integer getPuedeEliminar();

}
