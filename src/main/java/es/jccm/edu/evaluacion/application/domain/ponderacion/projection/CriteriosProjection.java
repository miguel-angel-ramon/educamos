package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Criterios", description = "Proyección para rescatar los criterios de evaluación")
public interface CriteriosProjection {

	@Schema(description = "Id relacion del criterio")
	Long getIdRelacionCri();

	@Schema(description = "Id del criterio")
	Long getIdCriterio();
	
	@Schema(description = "Descripción")
	String getDescripcionCri();

	@Schema(description = "Abreviatura")
	String getCodigoCri();

	@Schema(description = "Porcentaje")
	Float getPorcentajeCri();

	@Schema(description = "Peso")
	Integer getPesoCri();
	
	@Schema(description = "Tipo de Operación")
	Long getIdTipoOperacion();

	@Schema(description = "Nombre de Tipo de Operación")
	String getNombreTipoOperacion();
}