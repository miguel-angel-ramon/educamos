package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Falta Alumno Convocatoria", description = "Proyección para rescatar los datos Falta Alumno")

public interface FaltaAlumnoCentroProjection {
	
	@Schema(description = "Id Falta")
    Long getIdFalasialu();

    @Schema(description = "Tramo del horario")
    String getTramo();

    @Schema(description = "Fecha de la falta")
    String getFecha();
    
    @Schema(description = "id Justicacion")
    Long getIdJustificacion();
    
    @Schema(description = "Motivo")
    String getMotivo();
    
    @Schema(description = "id Motivo")
    String getIdMotivo();
    
    @Schema(description = "id Notificacion")
    Long getIdNotificacion();
    
    @Schema(description = "Notificacion")
    String getNotificacion();

    @Schema(description = "Observacion")
    String getObservacion();
    
    @Schema(description = "Tipo")
    String getTipo();

}
