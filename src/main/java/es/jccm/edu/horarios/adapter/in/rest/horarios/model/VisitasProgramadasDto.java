package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "VisitasProgramadas", description = "Visitas programadas de tutor legal")
public class VisitasProgramadasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Hora de la visita (formateada como HH:mm)")
    private String hora;

    @Schema(description = "Fecha de la visita")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fecha;

    @Schema(description = "Nombre completo del profesor")
    private String profesor;

    @Schema(description = "Observación asociada a la visita")
    private String observacion;

    @Schema(description = "Identificador único de la visita")
    private Long xVisprotutleg;

}
