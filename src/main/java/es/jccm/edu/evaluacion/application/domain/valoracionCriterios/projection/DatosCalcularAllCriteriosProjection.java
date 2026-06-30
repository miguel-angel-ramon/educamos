package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosCalcularAllCriterios", description = "Todos los datos necesarios para calcular todos los criterios")
public interface DatosCalcularAllCriteriosProjection {

	@Schema(description = "Id del criterio")
	Long getIdPonderacion();

	@Schema(description = "Id programación de aula")
	Long getIdProgramacionAula();

	@Schema(description = "Año académico")
	Long getAnno();

	@Schema(description = "Id sistema de calificación")
	Long getIdSistemaCalifica();
}
