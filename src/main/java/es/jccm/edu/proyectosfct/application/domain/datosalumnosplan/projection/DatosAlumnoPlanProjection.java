package es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosAlumnoPlanProjection", description = "Proyección para los datos del alumno relacionados con el plan")
public interface DatosAlumnoPlanProjection {

    @Schema(description = "Identificador del registro en la tabla FCT_DATOSALU_PLAN")
    Long getIdDatosAluPlan();

    @Schema(description = "Matrícula del alumno")
    Long getXMatricula();

    @Schema(description = "Flag que indica si dispone de PRL")
    Integer getLgPrl();

    @Schema(description = "Flag que indica si dispone de certificación adicional")
    Integer getLgCertificacion();

    @Schema(description = "Descripción de la certificación adicional")
    String getDsCertificacion();

    @Schema(description = "Flag que indica si requiere adaptaciones")
    Integer getLgAdaptaciones();

    @Schema(description = "Descripción de las adaptaciones requeridas")
    String getDsAdaptaciones();

    @Schema(description = "Flag que indica si requiere autorizaciones extraordinarias")
    Integer getLgAutorizaciones();

    @Schema(description = "Descripción de las autorizaciones requeridas")
    String getDsAutorizaciones();

    @Schema(description = "Observaciones relacionadas con el plan del alumno")
    String getDsObservaciones();

    @Schema(description = "Usuario que creó el registro")
    Long getCUsuCreacion();

    @Schema(description = "Fecha de creación del registro")
    java.util.Date getFCreacion();

    @Schema(description = "Usuario que actualizó el registro por última vez")
    Long getCUsuActualiza();

    @Schema(description = "Fecha de la última actualización del registro")
    java.util.Date getFActualiza();

    // Campos adicionales de TLALUMNOS
    @Schema(description = "Nombre completo del alumno")
    String getNombreCompleto();

    @Schema(description = "DNI del alumno")
    String getDni();

    @Schema(description = "NUSS del alumno")
    String getNuss();

    @Schema(description = "Teléfono del alumno")
    String getTelefono();

    @Schema(description = "Correo electrónico del alumno")
    String getEmail();
}
