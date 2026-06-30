package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Convocatoria", description = "Proyección para rescatar los datos de las Convocatorias")
public interface ConvocatoriaProjection {
	
	@Schema(description = "Id Convocatoria")
	Long getIdConvocatoria();
	
	@Schema(description = "nombre Convocatoria")
	String getNombre();
	
	@Schema(description = "fecha inicio Convocatoria")
	String getFechaInicio();
	
	@Schema(description = "fecha fin Convocatoria")
	String getFechaFin();
	
	@Schema(description = "tipo Convocatoria")
	String getTipoConvocatoria();
	
	@Schema(description = "estado Convocatoria")
	String getEstado();

}
