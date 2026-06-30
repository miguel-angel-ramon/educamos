package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "ValresAprAluEmpDto", description = "Información de la evaluación del alumno por la empresa")
public class ValresAprAluEmpDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador único de la evaluación")
    private Long idValresAprAluEmp;

    @Schema(description = "Identificador de la empresa")
    private Long xEmpresa;

    @Schema(description = "Identificador de la competencia específica")
    private Long xComesp;

    @Schema(description = "Calificación otorgada")
    private Long xCalifica;

    @Schema(description = "Identificador de la matrícula del alumno")
    private Long xMatMatricula;

    @Schema(description = "Comentarios o motivación de la calificación")
    private String txMotivacion;
}
