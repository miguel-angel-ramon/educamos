package es.jccm.edu.horarios.application.domain.horarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VisitasProgramadas", description = "Visitas realizadas por el profesorado tutor legal al alumno")
public interface VisitasProgramadasProjection {

    @Schema(description = "Hora de la visita (formateada)")
    String getHora();

    @Schema(description = "Fecha de la visita")
    Date getFecha();

    @Schema(description = "Nombre completo del profesor que realizó la visita")
    String getProfesor();

    @Schema(description = "Observación asociada a la visita")
    String getObservacion();

    @Schema(description = "Identificador único de la visita")
    Long getXVisprotutleg();
}
