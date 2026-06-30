package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosCalcularAllCriterios", description = "Todos los datos necesarios para calcular todos los criterios")
public interface AlumnosActividadCalcularAllCriteriosProjection {

	@Schema(description = "Id matricula Alumno")
	Long getIdMatricula();

	@Schema(description = "Id matmatricula Alumno")
	Long getIdMatMatriAlu();

}
