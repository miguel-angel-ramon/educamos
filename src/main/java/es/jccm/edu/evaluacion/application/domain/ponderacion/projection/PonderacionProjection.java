package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Criterios", description = "Proyección para rescatar los criterios de evaluación")
public interface PonderacionProjection {
	
	@Schema(description = "Id de la ponderación")
	Long getIdPonderacion();
	
	@Schema(description = "Id de la materia")
	Long getIdMateria();

	@Schema(description = "Id del docente")
	Long getIdDocente();

	@Schema(description = "Nombre de la materia")
	String getNombreMateria();

	@Schema(description = "Indica si una ponderación es editable")
	String getEditable();
	
}