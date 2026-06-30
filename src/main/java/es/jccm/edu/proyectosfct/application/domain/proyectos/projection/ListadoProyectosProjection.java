package es.jccm.edu.proyectosfct.application.domain.proyectos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado proyectos", description = "Descripcion para el modelo de proyectos de un centro")
public interface ListadoProyectosProjection {
	
	@Schema(description = "Identificador del identificador del proyecto")
	Long getId();
	
	@Schema(description = "Provincia del proyecto")
	String getDs_provincia();
	
	@Schema(description = "Centro del proyecto")
	String getDs_centro();
	
	@Schema(description = "Tipo del proyecto")
	String getDs_tipo();	
	
	@Schema(description = "Nombre del proyecto")
	String getDs_proyecto();
	
	@Schema(description = "Nombre del proyecto")
	String getDs_tutor();
	
	@Schema(description = "Familia profesional del proyecto")
	String getDs_familia();
	
	@Schema(description = "Modalidad del proyecto")
	String getDs_modalidad();
	
	@Schema(description = "Número de horas del proyecto")
	Integer getNu_horas();
	
	@Schema(description = "Número de alumnos del proyecto")
	Integer getNu_alumnos();
	
	@Schema(description = "Id Tutor")
	Long getIdTutorFct();

	@Schema(description = "Descripción del curso")
	String getDs_curso();

	@Schema(description = "Valor lógico para saber si el proyecto es de la nueva ley lofp")
	Integer getLg_lofp();

	@Schema(description = "Número de actividades asociadas")
	Integer getActividades();

	@Schema(description = "Valor lógico para saber si un plan se puede copiar o no")
	Integer getLg_copiar();

}
