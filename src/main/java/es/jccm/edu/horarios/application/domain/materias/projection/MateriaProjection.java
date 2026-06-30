package es.jccm.edu.horarios.application.domain.materias.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Materia", description = "Proyección para rescatar las materias de un profesor")
public interface MateriaProjection {
	
	@Schema(description = "Abreviatura")
	String getabreviatura();
	
	@Schema(description = "Descripción")
	String getDescripcion();
	
}

