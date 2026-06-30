package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "materias", description = "proyeccion para materias programacion aula")
public interface MateriaProgramacionAulaProjection {
	
	@Schema(description = "id materia")
	Long getIdMateriaOmg();
	
	@Schema(description = " descripcion materia")
	String getDescripcionMateria();
	
	@Schema(description = " descripcion materia")
	String getAbreviatura();
	
}

