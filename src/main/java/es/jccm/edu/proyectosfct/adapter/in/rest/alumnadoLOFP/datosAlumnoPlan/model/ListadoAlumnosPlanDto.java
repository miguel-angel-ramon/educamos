package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "ListadoAlumnosPlanDto", description = "Información completa sobre los datos del alumno relacionados con el plan")
public class ListadoAlumnosPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Matrícula del alumno")
    private Long xMatricula;

    @Schema(description = "Flag que indica si dispone de PRL")
    private Integer lgPrl;

    @Schema(description = "Flag que indica si requiere adaptaciones")
    private Integer lgAdaptaciones;

    @Schema(description = "Flag que indica si requiere autorizaciones extraordinarias")
    private Integer lgAutorizaciones;

    // Campos adicionales
    @Schema(description = "Nombre completo del alumno")
    private String nombreCompleto;

    @Schema(description = "DNI del alumno")
    private String dni;

    @Schema(description = "NUSS del alumno")
    private String nuss;
}
