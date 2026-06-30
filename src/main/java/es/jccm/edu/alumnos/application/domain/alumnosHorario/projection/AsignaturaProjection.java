package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Asignatura", description = "Asignaturas rescatados de la BBDD de comunica")
public interface AsignaturaProjection {
	
	@Schema(description = "abreviatura")
	String getAbreviatura();

	@Schema(description = "descripcion")
	String getDescripcion();
	
}
