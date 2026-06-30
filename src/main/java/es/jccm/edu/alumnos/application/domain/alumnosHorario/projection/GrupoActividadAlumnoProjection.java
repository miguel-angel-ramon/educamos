package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GrupoActividad", description = "Grupos de actividad de un profesor para un año académico")
public interface GrupoActividadAlumnoProjection {
	
	@Schema(description = "Id del grupo de actividad")
	Long getIdGrupoActividad();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "IdMateriaOmg")
	String getIdMateriaOmg();
	
	@Schema(description = "Observación")
	String getObservacion();
	
}

