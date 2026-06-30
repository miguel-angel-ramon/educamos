package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Convocatoria Unidad", description = "Proyección para rescatar fecha sesion de convocatoria unidad")
public interface ConvUnidadProjection {
	
	@Schema(description = "Id convocatoria unidad")
	Long getIdConvUnidad();
	
	@Schema(description = "fecha sesión")
	Date getDescripcion();
	
	@Schema(description = "estado")
	String getEstado();
	
	
	

}
