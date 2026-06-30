package es.jccm.edu.evaluacion.application.domain.materiasProfesor.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MateriaProfesor", description = "Proyección para rescatar las materias de un profesor")
public interface MateriasProfesorProjection {
	
	@Schema(description = "IdMateria")//////
	Long getIdMateria();
	
	@Schema(description = "Abreviatura")
	String getAbreviatura();
	
	@Schema(description = "Descripción")
	String getDescripcion();
	
}
