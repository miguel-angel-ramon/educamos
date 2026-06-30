package es.jccm.edu.proyectosfct.application.domain.programasPFE.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DescargaAnexo V", description = "Descarga del anexo V")
public interface AnexoVProjection {

    @Schema(description = "Centro educativo")
    String getCentro();

    @Schema(description = "Código del centro")
    String getCodigo();

    @Schema(description = "CIF del centro")
    String getNif();

    @Schema(description = "Tipo de centro")
    String getTipo();

    @Schema(description = "Tipo de curso")
    String getTipoCurso();

    @Schema(description = "Alcance ")
    Integer getAlcance();
    @Schema(description = "Número de alumnos")
    Integer getNAlumnos();

    @Schema(description = "Ampliación")
    Integer getAmpliacion();

    @Schema(description = "Horas de PFE")
    Integer getHorasPFE();

    @Schema(description = "Reparto de horas por Curso")
    Integer getHorasCur();

    @Schema(description = "Horas complementarias PFE")
    String getHorasComPFE();

    @Schema(description = "Horas complementarias curso")
    String getHorasConcur();

    @Schema(description = "Modulos")
    String getModulos();

    @Schema(description = "autorizacion")
    Integer getAutorizacion();

    @Schema(description = "Descripción organismos colaboradores")
    String getDsOrg();

    @Schema(description = "Descripción características de contrato en alternancia")
    String getDsCara();

    @Schema(description = "Descripción condiciones de la beca")
    String getDsBeca();

    @Schema(description = "Descripción módulo")
    String getDsMod();

    @Schema(description = "Flag autorizacion 400")
    Integer getLgAut400();

    @Schema(description = "Flag autorizacion formación complementaria")
    Integer getLgAutForCom();

    @Schema(description = "Flag autorización formación complentaria cursos de especialización")
    Integer getLgAutCurEsp();

    @Schema(description = "Flag autorización ampliación a tres cursos ciclos GB,GM,GS cuya oferta se realize en régimen intensivo")
    Integer getLgAut3Cur();

    @Schema(description = "Flag autorización para el cambio de régimen de oferta de general a intensiva")
    Integer getLgAutCamReg();

    @Schema(description = "Flag autorización para realizar FCT en centros educativos que imparte la propia enseñanza objeto de la autorización.")
    Integer getLgAutProens();
    
    @Schema(description = "Flag que indica si la modalidad es General o intensiva (1 General, 2 Intensiva")
    Integer getLgModalidad();

    @Schema(description = "Flag que indica intervalo de formación diario")
    Integer getLgDiario();

    @Schema(description = "Flag que indica intervalo de formación semanal")
    Integer getLgSemanal();

    @Schema(description = "Flag que indica intervalo de formación mensual")
    Integer getLgMensual();

    @Schema(description = "Flag que indica intervalo de formación otros")
    Integer getLgOtros();

    @Schema(description = "Flag que indica el tipo de ciclo")
    Integer getLgTipoCiclo();

}
