package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CabeceraAnexoIX", description = "Fuente de datos para la cabecera del AnexoIX")
public interface HeaderAnexoIXProjection {

    @Schema(description = "Nombre del alumno")
    String getNombreAlumno();

    @Schema(description = "Ciclo")
    String getCiclo();

    @Schema(description = "Grupo")
    String getGrupo();

    @Schema(description = "Regimen")
    String getRegimen();

    @Schema(description = "Fecha")
    String getFecha();

    @Schema(description = "Academico")
    String getAcademico();

    @Schema(description = "Orden")
    Integer getOrden();


    @Schema(description = "Centro")
    String getCentro();

    @Schema(description = "Código")
    String getCodigo();

    @Schema(description = "Nombre del tutor")
    String getNombreTutor();

    @Schema(description = "Nombre de la Empresa")
    String getNombreEmpresa();

    @Schema(description = "Tutor empresa")
    String getTutorEmpresa();

    @Schema(description = "Código de la empresa")
    String getCodigoEmpresa();

    @Schema(description = "Periodo de formación")
    String getPeriodo();

    @Schema(description = "Total Horas")
    String getTotalHoras();

    @Schema(description = "Localidad")
    String getLocalidad();

}
