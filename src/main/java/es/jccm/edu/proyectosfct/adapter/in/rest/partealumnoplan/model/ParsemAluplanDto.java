package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "ParsemAluplanDto", description = "Información sobre el parte semanal del alumno en el plan")
public class ParsemAluplanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador único del parte semanal")
    private Long idParsemAluplan;

    @Schema(description = "Identificador del rodal asociado al parte semanal")
    private String idParsemRodal;

    @Schema(description = "Nombre del fichero del parte semanal")
    private String txParsemFichero;

    @Schema(description = "Fecha de inicio de la semana")
    private String fInisem;

    @Schema(description = "Fecha de registro del parte semanal")
    private String fRegistro;

    @Schema(description = "Vista del usuario que registra el parte (P: Profesorado, ALU: Alumnado)")
    private String cdVista;

    @Schema(description = "Flag que indica si el parte está o no actualizado. 0 -> no está, 1 -> si esta")
    private Integer lgActualizado;

    @Schema(description = "Usuario que registró el parte")
    private Long xUsuarioCreacion;
}
