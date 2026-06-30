package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Falta Alumno Convocatoria", description = "Proyección para rescatar los datos Falta Alumno")
public interface NotificacionFaltaProjection {
	
	@Schema(description = "Id Notficacion")
    Long getIdNotificacion();

    @Schema(description = "fecha ausencia")
    String getFechau();

    @Schema(description = "Fecha ultimo dia")
    String getFechdia();

    @Schema(description = "Motivo")
    String getMotivo();
    
    @Schema(description = "id Motivo")
    Long getIdMotivo();
    
    @Schema(description = "Observacion")
    String getObservacion();
    
    

}
