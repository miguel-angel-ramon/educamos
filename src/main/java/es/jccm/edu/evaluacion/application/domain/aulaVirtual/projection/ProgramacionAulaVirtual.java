package es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Programación aula virtual", description = "Proyección para rescatar los datos de las programaciones de las aulas virtuales")
public interface ProgramacionAulaVirtual {

    @Schema(description = "Id programación de aula")
    Long getIdProgAula();

    @Schema(description = "Id programación didáctica")
    String getIdProgDidac();

    @Schema(description = "Nombre de la programación")
    String getNombre();

    @Schema(description = "Id aula virtual")
    String getIdAula();

}
