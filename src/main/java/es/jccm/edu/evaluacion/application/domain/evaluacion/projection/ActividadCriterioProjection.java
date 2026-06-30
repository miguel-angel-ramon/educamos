package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Actividad Criterio", description = "Proyección para rescatar todas las actividades que tengan un criterio concreto, junto con su nota")
public interface ActividadCriterioProjection {

    @Schema(description = "Id de la actividad")
    Long getIdActividad();

    @Schema(description = "Nombre de la unidad")
    String getNombreUnidad();

    @Schema(description = "Abreviatura de la unidad")
    String getAbrevUnidad();

    @Schema(description = "Nombre de la actividad")
    String getNombreActividad();

    @Schema(description = "Abreviatura de la actividad")
    String getAbrevAct();

    @Schema(description = "Descripcion de la Calificacion")
    String getDescCal();

    @Schema(description = "Convocatoria de la actividad")
    String getConvocatoria();

    @Schema(description = "Porcentaje/peso de la act")
    Long getPorcentaje();

}
