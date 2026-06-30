package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Actividad con un criterio asignado", description = "Dto Detalle de actividades por criterio en valoracion de criterios")
public class ActividadCriterioDTO implements Serializable {

    @Schema(description = "Id de la actividad")
    Long idActividad;

    @Schema(description = "Nombre de la unidad")
    String nombreUnidad;

    @Schema(description = "Abreviatura de la unidad")
    String abrevUnidad;

    @Schema(description = "Nombre de la actividad")
    String nombreActividad;

    @Schema(description = "Abreviatura de la actividad")
    String abrevAct;

    @Schema(description = "Descripcion de la Calificacion")
    String descCal;

    @Schema(description = "Convocatoria de la actividad")
    String convocatoria;

    @Schema(description = "Porcentaje/peso de la act")
    Long porcentaje;


}
