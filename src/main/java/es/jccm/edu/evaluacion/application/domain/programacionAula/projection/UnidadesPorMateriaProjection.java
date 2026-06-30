package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UnidadesPorMateriaProjection", description = "Proyección para rescatar las unidades por materia")
public interface UnidadesPorMateriaProjection {

	@Schema(description = "Id de la Unidad")
	Long getIdUnidad();
	
	@Schema(description = "Nombre que el centro da a la unidad")
	String getNombreUnidad();
	
	@Schema(description = "Id del Centro")
	Long getIdCentro();
}
