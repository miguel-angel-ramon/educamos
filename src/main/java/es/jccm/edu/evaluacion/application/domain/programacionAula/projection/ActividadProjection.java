package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Actividades unidades de programación")
public interface ActividadProjection {
	
	@Schema(description = "Id actividad")
	Long getId();

	@Schema(description = "")
	String getAbreviatura();

	@Schema(description = "Orden de la actividad")
	String getOrden();
	
	@Schema(description = "")
	String getNombre();

	@Schema(description = "")
	String getDescripcion();

	@Schema(description = "")
	String getDescripcionConvocatoria();

	@Schema(description = "")
	Long getIdUnidadProgramacion();

	@Schema(description = "")
	Long getIdInstrumentoEvaluacion();
	
	@Schema(description = "")
	Long getIdConvCentroOmc();
	
	@Schema(description = "")
	Date getFechaInicio();
	@Schema(description = "")
	String getNombreUnidad();
	
	@Schema(description = "")
	Date getFechaFin();

	@Schema(description = "")
	Integer getLprocedeMoodle();

	@Schema(description = "")
	Boolean getConvocatoriaUnidad();
	
	@Schema(description = "")
	Long getIdProgramacionAula();
	
	@Schema(description = "")
	Long getIdActividadMoodle();
	
}
