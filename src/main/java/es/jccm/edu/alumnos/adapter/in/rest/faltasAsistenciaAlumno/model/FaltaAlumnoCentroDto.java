package es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FaltaAsistenciaAlumno", description = "FaltaAsistenciaAlumno rescatados del front")
public class FaltaAlumnoCentroDto {

	
	private static final long serialVersionUID = 1L;

    @Schema(description = "Id Falta")
    private Long idFalasialu;

    @Schema(description = "Tramo del horario")
    private String tramo;
    
    @Schema(description = "id Notificacion")
    private Long idNotificacion;
    
    
    @Schema(description = "id Motivo")
    private Long idMotivo;

   
    @Schema(description = "Justificacion")
    private Long idJustificacion;
    
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    @Schema(description = "Fecha de la falta")
    private String fecha;

    @Schema(description = "Motivo")
    private String motivo;
    
    @Schema(description = "Notificacion")
    private String notificacion;

    @Schema(description = "Observacion")
    private String observacion;

    @Schema(description = "Tipo de falta")
    private String tipo;
    
    
}
