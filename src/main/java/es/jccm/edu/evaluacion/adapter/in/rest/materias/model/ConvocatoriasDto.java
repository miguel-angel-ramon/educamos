package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "Ultimo años", description = "Proyección para rescatar los ultimos años")
public class ConvocatoriasDto {

    @Schema(description = "Id de la convocatoria del centro")
    Long idConvCentro;

    @Schema(description = "Id convocatoria centroOmc")
    Long idConvCentroOmc;

    @Schema(description = "Estado de la convocatoria de la unidad")
    Long estadoConv;

    @Schema(description = "Descripción del estado de la convocatoria")
    String descEstadoConv;

    @Schema(description = "Tipo de convocatoria, ordinario o extraordinaria")
    Long idTipoConv;

    @Schema(description = "Descripción de la convocatoria")
    String convocatoria;

    @Schema(description = "Fecha inicio de la convocatoria")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
    Date fechaInicio;

    @Schema(description = "Fecha fin de la convocatoria")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
    Date fechaFin;

    @Schema(description = "Nombre de las materias con competencias específicas sin evaluar a algun alumno")
    String nombreMaterias;
}
