package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materias valoracion", description = "Proyección para rescatar las materias de valoración")
public class MateriasValoracionDto {

    @Schema(description = "Id de la materia")
    Long idMateria;

    @Schema(description = "Id de la etapa")
    Long idEtapa;

    @Schema(description = "Descripción de la materia")
    String materia;

    @Schema(description = "Id oferta de la matrícula")
    Long idOfertaMatrig;

    @Schema(description = "")
    Long idOfertaMatric;

    @Schema(description = "Indica si la programación didáctica está cerrada")
    Long cerrada;

    @Schema(description = "Indica si la materia es pendiente")
    Boolean pendiente;

    @Schema(description = "Id oferta de la matrícula para las asignaturas pendientes")
    Long idOfertaMatrigAlumno;

    @Schema(description = "Indica si la materia tiene programaciones didácticas creadas")
    Boolean hayProgDidac;
}
