package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Convocatoria materia alumno", description = "Proyección para rescatar las convocatorias del curso del alumno")
public interface ConvocatoriaAlumnoEvaluacionProjection {
	
	@Schema(description = "Id de la convocatoria")
	Long getIdConvocatoria();
	
	@Schema(description = "Nombre de la convocatoria")
	String getNombreConvocatoria();
	
	@Schema(description = "Descripcion de la convocatoria")
	String getDescripcionConvocatoria();
	
	@Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
	
	@Schema(description = "Estado de la convocatoria")
	String getEstado();
	
	@Schema(description = "Observaciones de la convocatoria")
	String getObservaciones();
	
	@Schema(description = "¿Es convocatoria final?")
	Boolean getEsFinal();

}
