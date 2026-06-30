package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosCabeceraAnexoII", description = "Fuente de datos para la cabecera del AnexoII")
public interface DatosCabeceraAnexo2PlanProjection {
    @Schema(description = "Regimen")
    String getRegimen();

    @Schema(description = "Fecha")
    String getFecha();

    @Schema(description = "Academico")
    String getAcademico();

    @Schema(description = "Orden")
    Integer getOrden();

    @Schema(description = "Ciclo")
    String getCiclo();

    @Schema(description = "Grupo")
    String getGrupo();

    @Schema(description = "Nombre")
    String getNombre();

    @Schema(description = "Dni")
    String getDni();

    @Schema(description = "Nuss")
    String getNuss();

    @Schema(description = "Email")
    String getEmail();

    @Schema(description = "Telefono")
    String getTelefono();

    @Schema(description = "Nacimiento")
    String getNacimiento();

    @Schema(description = "Basico")
    Integer getBasico();

    @Schema(description = "Certificacion")
    Integer getCertificacion();
    @Schema(description = "Especificar")
    String getEspecificar();

    @Schema(description = "Centro")
    String getCentro();
    @Schema(description = "Mailcen")
    String getMailcen();

    @Schema(description = "Primer dia")
    String getCodigo();

    @Schema(description = "Tutor")
    String getTutor();
    @Schema(description = "Mailtut")
    String getMailtut();
    @Schema(description = "Tfntut")
    String getTfntut();
    @Schema(description = "Adaptaciones")
    Integer getAdaptaciones();
    @Schema(description = "Dsadaptaciones")
    String getDsadaptaciones();

    @Schema(description = "Autorizaciones")
    Integer getAutorizaciones();

    @Schema(description = "Autorizaciones")
    String getDsautorizaciones();

    @Schema(description = "Observaciones")
    String getObservaciones();

    @Schema(description = "Diario")
    Integer getDiario();

    @Schema(description = "Semanal")
    Integer getSemanal();

    @Schema(description = "Mensual")
    Integer getMensual();

    @Schema(description = "Otros")
    Integer getOtros();

    @Schema(description = "Varias")
    Integer getVarias();

    @Schema(description ="Horas Totales")
    Integer getHorasConvenio();


}
