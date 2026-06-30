package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "PardiaAluplanDto", description = "Información sobre el parte diario del alumno en el plan")
public class PardiaAluplanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador único del parte diario")
    private Long idPardiaAluplan;

    @Schema(description = "ID del convenio proyecto asociado al alumno")
    private Long idConvenioProyectoAlumno;

    @Schema(description = "ID del parte semanal asociado al alumno")
    private Long idParsemAluplan;

    @Schema(description = "Fecha del parte diario")
    private Date fDia;

    @Schema(description = "Observaciones del parte diario")
    private String txObservaciones;

    @Schema(description = "Número de horas del parte diario")
    private Integer nuHoras;
}
