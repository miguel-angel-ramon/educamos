package es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoAltasSegSociProg", description = "Listado de altas de la Seguridad Social de Programas")
public interface ListadoSegSocialCotizaMesProjection {

    @Schema(description = "Número de días Inte")
    Integer getNuDiasInte();

    @Schema(description = "Nombre alumno")
    String getNombreAlumno();

    @Schema(description = "Número de días reales")
    Integer getNuDiasReal();

    @Schema(description = "Número de días Nacu")
    Integer getNuDiasNacu();

    @Schema(description = "Fecha en la que se envía a la seguridad social")
    String getFechaEnvio();

    @Schema(description = "Boolean para indicar la validación por el centro")
    String getValCen();

    @Schema(description = "Dni del alumno")
    String getDni();

    @Schema(description = "Registro correcto con el registro de empresa gestora")
    Integer getEsCorrecto();

    @Schema(description = "Nombre convenio alumno")
    Long getIdAluCon();

    @Schema(description = "Número del mes")
    Integer getNuMes();

    @Schema(description = "Centro")
    String getCentro();

    @Schema(description = "warnings que indican los errores que puede haber en los datos enviados")
    String getDsWarnings();

    @Schema(description = "Tipo empresa")
    String getTipoEmpresa();

    @Schema(description = "Número de la Seguridad Social del alumno")
    String getNuss();

    @Schema(description = "Unidad")
    String getUnidad();

    @Schema(description = "Nombre empresa")
    String getNombreEmpresa();

    @Schema(description = "Tutor")
    String getTutor();

    @Schema(description = "Boolean para indicar la validación por el delegado")
    String getValDel();
    
    @Schema(description = "Cif empresa")
    String getCif();
    
    @Schema(description = "Codigo centro")
    String getCcodigo();
    
    @Schema(description = "Enviado")
    String getEnviado();
    
    @Schema(description = "Curso")
    String getCurso();
    
    @Schema(description = "Puede rechazar")
    Integer getPuederechazar();
    
    @Schema(description = "Nombre alumno")
    Long getId();

    @Schema(description = "Boolean para indicar la valicadión por el tutor")
    String getValTut();

    @Schema(description = "aluConBeca")
    Integer getLgErasBec();

    @Schema(name = "diasInteEra")
    Integer getDiasInteEra();

    @Schema(name = "tipoErasmus")
    String getTipoErasmus();

    @Schema(name = "Fecha inico de las practicas del alumno")
    String getInicio();

    @Schema(name = "Fecha fin de las practicas del alumno")
    String getFin();

    @Schema(name = "Dias disponibles para cotizar")
    Integer getDiasRestantes();

    @Schema(name = "Número que indica si muestra o no aviso por no haber rellenado el mes anterior de cotizaciones")
    Integer getAvisoMes();

    @Schema(name = "Número que bloquea o no la edición de las cotizaciones")
    Integer getBloqueoMes();

    @Schema(description = "Indica si existe un alta correcta en el mes/año indicado")
    String getEsCorrectoAlta();
}
