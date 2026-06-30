package es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoResultadosAsociadosPlanProjection", description = "Listado de resultados asociados a módulos con información adicional")

public interface ListadoResultadosAsociadosPlanProjection {

	@Schema(description = "ID de Aprendizaje")
	Long getX_comesp();

	@Schema(description = "Abreviatura")
	String getAbreviatura();

	@Schema(description = "Descripción del Aprendizaje")
	String getDescripcion();

	@Schema(description = "Número de actividades asociadas")
	String getActividades();

	// Campos calculados
	@Schema(description = "Indicador de Centro (0 si no aplica)")
	Integer getLg_centro();

	@Schema(description = "Indicador de Empresa (0 si no aplica)")
	Integer getLg_empresa();

}