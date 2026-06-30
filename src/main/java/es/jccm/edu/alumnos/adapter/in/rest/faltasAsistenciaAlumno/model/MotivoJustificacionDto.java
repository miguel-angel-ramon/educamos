package es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Motivo Justificacion", description = "Motivo de justificación de una falta de asistencia de un alumno")
public class MotivoJustificacionDto {

    @Schema(description = "Id Motivo justificación")
    private Long idMotivo;

    @Schema(description = "Nombre Motivo justificación")
    private String nombreMotivo;
    
}
