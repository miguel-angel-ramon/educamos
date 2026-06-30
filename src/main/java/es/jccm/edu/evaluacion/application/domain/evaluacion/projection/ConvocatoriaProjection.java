package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Ultimo años", description = "Proyección para rescatar los ultimos años")
public interface ConvocatoriaProjection {

    @Schema(description = "Id de la convocatoria del centro")
    Long getIdConvCentro();

    @Schema(description = "Id convocatoria centroOmc")
    Long getIdConvCentroOmc();

    @Schema(description = "Estado de la convocatoria de la unidad")
    Long getEstadoConv();

    @Schema(description = "Descripción del estado de la convocatoria")
    String getDescEstadoConv();

    @Schema(description = "Tipo de convocatoria, ordinario o extraordinaria")
    Long getIdTipoConv();

    @Schema(description = "Descripción de la convocatoria")
    String getConvocatoria();

    @Schema(description = "Fecha inicio de la convocatoria")
    Date getFechaInicio();

    @Schema(description = "Fecha fin de la convocatoria")
    Date getFechaFin();

    @Schema(description = "Nombre de las materias con competencias específicas sin evaluar a algun alumno")
    String getNombreMaterias();
}
