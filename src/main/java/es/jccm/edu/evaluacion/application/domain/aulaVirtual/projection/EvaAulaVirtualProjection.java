package es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AulaVirtual", description = "Proyección para rescatar los datos de aulas virtuales")
public interface EvaAulaVirtualProjection {

    @Schema(description = "Id del aula")
    Long getId();

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
    
    @Schema(description = "Id Curso (cadena)")
    String getCursoString();
    
    @Schema(description = "Id Materia OMG")
    Long getIdMateriaOMG();
    
    @Schema(description = "Id Oferta Matrig")
    Long getIdOfertaMatrig();
    
    @Schema(description = "Token Plataforma")
    String getTokenPlataforma();

}
