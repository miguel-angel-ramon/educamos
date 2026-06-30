package es.jccm.edu.horarios.application.domain.horarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GrupoActividad", description = "Grupos de actividad de un profesor para un año académico")
public interface GrupoActividadProjection {
	
	@Schema(description = "Id del grupo de actividad")
	Long getIdGrupoActividad();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "Abreviatura")
	String getAbreviatura();
	
	@Schema(description = "Id de la unidad")
	Long getIdUnidad();
	
}

