package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidad Convocatoria", description = "Proyección para rescatar los datos de las Unidades")
public interface UnidadConvProjection {
	
	@Schema(description = "Id Unidad")
	Long getIdUnidad();
	
	@Schema(description = "nombre Unidad")
	String getNombre();
	
	@Schema(description = "Estado")
	String getEstado();
	
	@Schema(description = "Id ConvUnidad")
	Long getIdConvUnidad();
	
	@Schema(description = "IdOfermatrig")
	Long getIdOfermatrig();
	
	@Schema(description = "IdConvOmc")
	Long getIdConvOmc();
	
	@Schema(description = "nombre etapa")
	String getEtapa();
	
	@Schema(description = "IdEtapa")
	Long getIdCentro();
	
	@Schema(description = "IdEtapa")
	Long getIdEtapa();
	
	@Schema(description = "IdOfermatric")
	Long getIdOfermatric();
	
}
