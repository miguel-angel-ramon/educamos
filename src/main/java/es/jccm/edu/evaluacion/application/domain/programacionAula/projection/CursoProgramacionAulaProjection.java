package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Curso", description = "proyeccion para curso programacion aula")
public interface CursoProgramacionAulaProjection {
	
	@Schema(description = "id curso")
	Long getIdOfermatrig();
	
	@Schema(description = " descripcion curso")
	String getDescripcionCorta();
	
}

