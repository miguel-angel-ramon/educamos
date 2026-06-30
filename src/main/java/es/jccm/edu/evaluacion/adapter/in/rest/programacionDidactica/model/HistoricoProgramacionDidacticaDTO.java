package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Programaciones Didácticas Históricas", description = "Proyección para rescatar las programaciones didácticas de años anteriores")
public class HistoricoProgramacionDidacticaDTO {

    @Schema(description = "Id de la programación didáctica")
    private Long idProgramacionDidactica;

    @Schema(description = "Año académico")
    private Integer anyo;

    @Schema(description = "Tramo de años del curso académico")
    private String tramo;

    @Schema(description = "Id de materia de curso genérica")
    private Long idMateriaOmg;

    @Schema(description = "Id de materia de curso genérica adaptada al nivel curricular")
    private Long idMateriaOmgAdaptacion;

    @Schema(description = "Nombre de la materia de adaptación")
    private String nombreMateriaAdaptacion;

}
