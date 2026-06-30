package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "CursoAcademico", description = "")
public interface CursoAcademicoProjection {
	
	@Schema(description = "Año académico")
	Integer getAnno();
	
	@Schema(description = "")
	Date getFechaInicio();
	
	@Schema(description = "")
	Date getFechaFinal();
	
}

