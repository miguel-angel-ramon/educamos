package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Programaciones Didácticas Históricas", description = "Proyección para rescatar las programaciones didácticas de años anteriores")
public interface EvaProgramacionDidacticaHistoricaProjection {

    @Schema(description = "Id de la programación didáctica")
    Long getIdProgramacionDidactica();

    @Schema(description = "Año académico")
    Integer getAnyo();

    @Schema(description = "Tramo de años del curso académico")
    String getTramo();

    @Schema(description = "Id de materia de curso genérica")
    Long getIdMateriaOmg();

    @Schema(description = "Id de materia de curso genérica adaptada al nivel curricular")
    Long getIdMateriaOmgAdaptacion();

    @Schema(description = "Nombre de la materia de adaptación")
    String getNombreMateriaAdaptacion();

}
