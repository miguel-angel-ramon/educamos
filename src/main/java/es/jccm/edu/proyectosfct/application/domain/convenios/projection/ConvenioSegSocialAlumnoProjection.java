package es.jccm.edu.proyectosfct.application.domain.convenios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ConvenioSegSocialAlumnoProjection {

    @Schema(description = "Identificador del Convenio Proyecto Alumno")
    Long getIdConvProyAlu();

    @Schema(description = "Nombre de la empresa asociada")
    String getNombreEmpresa();

    @Schema(description = "Id de la empresa asociada")
    Integer getIdEmpresa();

    @Schema(description = "Indica si el centro asume la responsabilidad de Seguridad Social")
    Integer getCentroSS();

    @Schema(description = "Indica si el alumno tiene relación con Erasmus (0: no, 1: sin beca, 2: con beca)")
    Integer getErasmus();

    @Schema(description = "Identificador del proyecto asociado")
    Long getIdProyecto();
}
