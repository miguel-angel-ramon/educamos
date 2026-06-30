package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Sistema Calificacion", description = "Sistema de calificación de un grupo actividad")
public interface ListCalificacionesProjection {

	@Schema(description = "Id calificacion")
	Long getIdCalifica();
	
	@Schema(description = "Descripcion de calificacion")
	String getCalificacion();
	
	@Schema(description = "Abreviatura")
	String getAbreviatura();

	@Schema(description = "Aprueba Materia")
	String getApruebaMateria();
	
}
