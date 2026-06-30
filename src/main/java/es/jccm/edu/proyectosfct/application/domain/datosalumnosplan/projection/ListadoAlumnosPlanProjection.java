package es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoAlumnosPlanProjection", description = "Proyección para el listado de alumnos relacionados con el plan")
public interface ListadoAlumnosPlanProjection {


    @Schema(description = "Matrícula del alumno")
    Long getXMatricula();

    @Schema(description = "Flag que indica si dispone de PRL")
    Integer getLgPrl();

    @Schema(description = "Flag que indica si requiere adaptaciones")
    Integer getLgAdaptaciones();

    @Schema(description = "Flag que indica si requiere autorizaciones extraordinarias")
    Integer getLgAutorizaciones();

    // Campos adicionales de TLALUMNOS
    @Schema(description = "Nombre completo del alumno")
    String getNombreCompleto();

    @Schema(description = "DNI del alumno")
    String getDni();

    @Schema(description = "NUSS del alumno")
    String getNuss();
}
