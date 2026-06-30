package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Materias valoracion", description = "Proyección para rescatar las materias de valoración")
public interface MateriasValoracionProjection {

    @Schema(description = "Id de la materia")
    Long getIdMateria();

    @Schema(description = "Id de la etapa")
    Long getIdEtapa();

    @Schema(description = "Descripción de la materia")
    String getMateria();

    @Schema(description = "Id oferta de la matrícula")
    Long getIdOfertaMatrig();

    @Schema(description = "")
    Long getIdOfertaMatric();

    @Schema(description = "Indica si la programación didáctica está cerrada")
    Long getCerrada();

    @Schema(description = "Indica si la materia es pendiente")
    Boolean getPendiente();

    @Schema(description = "Id oferta de la matrícula para las asignaturas pendientes")
    Long getIdOfertaMatrigAlumno();

    @Schema(description = "Indica si la materia tiene programaciones didácticas creadas")
    Boolean getHayProgDidac();

}
