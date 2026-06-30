package es.jccm.edu.horarios.application.domain.horarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "HorarioPersonal", description = "Proyección para rescatar el horario personal")
public interface HorarioPersonalProjection {

    @Schema(description = "Id de registro")
    Long getIdRegistro();

    @Schema(description = "Fecha anotación en la agenda personal.")
    Date getFechaAnotacion();

    @Schema(description = "Hora de la anotación")
    String getHora();

    @Schema(description = "Título de la anotación")
    String getTitulo();

    @Schema(description = "Día de la anotación")
    String getDia();
}
