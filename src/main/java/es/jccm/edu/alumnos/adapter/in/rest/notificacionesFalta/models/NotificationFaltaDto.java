package es.jccm.edu.alumnos.adapter.in.rest.notificacionesFalta.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "NotificationFalta", description = "")
public class NotificationFaltaDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "id Notificacion")
    private Long idNotificacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha ausencia")
    private String fechau;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(description = "ultimo dia ausencia")
    private String fechdia;

    @Schema(description = "Motivo")
    private String motivo;

    @Schema(description = "id Motivo")
    private Long idMotivo;
    
    @Schema(description = "Observacion")
    private String observacion;

}
