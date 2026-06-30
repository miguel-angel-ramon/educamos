package es.jccm.edu.horarios.application.domain.dependencias.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Materia", description = "Proyección para rescatar lista tipo de dependencias")
public interface TipoDependenciaProjection {
	
	@Schema(description = "Nombre")
	String getNombre();
	
}

