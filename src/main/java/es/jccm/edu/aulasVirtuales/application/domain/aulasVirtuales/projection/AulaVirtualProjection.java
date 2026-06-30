package es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AulaVirtual", description = "Proyección para rescatar los datos de aulas virtuales")
public interface AulaVirtualProjection {

    @Schema(description = "Id del aula")
    Long getIdAula();

    @Schema(description = "Nombre del aula")
    String getAula();

    @Schema(description = "Nombre de la materia")
    String getNombreMateria();

    @Schema(description = "Nombre del ciclo")
    String getCiclo();

    @Schema(description = "Id moodle")
    Long getIdMoodle();

    @Schema(description = "Id Plataforma")
    String getIdPlataforma();
    
    @Schema(description = "Id Curso")
    Long getIdCurso();
    
    @Schema(description = "Id Curso")
    Long getIdMateriaOMG();
    
    @Schema(description = "Id Curso")
    Long getIdOfertaMatrig();
    
    @Schema(description = "Token Plataforma")
    String getTokenPlataforma();

}
