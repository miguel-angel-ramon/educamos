package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Sistema de calificación", description = "Son las notas posibles según la etapa")
public class SistemaCalificacionCuaDto {

    @Schema(description = "Id calificación")
    Long idCalifica;

    @Schema(description = "Id sistema de calificación")
    Long idSistCal;

    @Schema(description = "Nota seleccionable")
    Long nota;

    @Schema(description = "Descripción de la nota del alumno")
    String descCal;

    @Schema(description = "Si la nota es aprobada o no")
    String aprueba;

    @Schema(description = "Descripción sistema de calificación")
    String descripcion;

    @Schema(description = "Descripción abreviada sistema de calificación")
    String descSis;

    @Schema(description = "Indica si el sistema de calificación es numérico")
    Boolean numerico;
}
