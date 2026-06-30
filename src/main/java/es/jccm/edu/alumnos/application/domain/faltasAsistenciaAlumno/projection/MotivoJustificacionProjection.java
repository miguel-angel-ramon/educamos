package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Motivo Justificacion", description = "Motivo de justificación de una falta de asistencia de un alumno")

public interface MotivoJustificacionProjection {

    @Schema(description = "Id Motivo")
    Long getIdMotivo();

    @Schema(description = "Nombre Motivo")
    String getNombreMotivo();
    
}
