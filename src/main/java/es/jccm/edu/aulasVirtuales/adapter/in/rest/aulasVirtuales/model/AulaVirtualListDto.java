package es.jccm.edu.aulasVirtuales.adapter.in.rest.aulasVirtuales.model;

import java.io.Serializable;

import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.UsuarioAulaVirtual;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoHorario", description = "Aulas virtuales rescatadas de la BBDD de comunica")
public class AulaVirtualListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del aula")
    private Long idAula;

    @Schema(description = "Nombre del aula")
    private String aula;

    @Schema(description = "Nombre de la materia")
    private String nombreMateria;

    @Schema(description = "Nombre del ciclo")
    private String ciclo;

    @Schema(description = "Url del aula")
    private String urlAula;
    
    @Schema(description = "Id Moodle")
    private Long idMoodle;
    
    @Schema(description = "Id Plataforma")
    private String idPlataforma;
    
    @Schema(description = "Id Curso")
    private Long idCurso;
    
    @Schema(description = "Id MateriaOMG")
    private Long idMateriaOMG;
    
    @Schema(description = "Id OfertaMatrig")
    private Long idOfertaMatrig;
    
    @Schema(description = "Token de plataforma")
    private String tokenPlataforma;
    
    @Schema(description = "Datos del empleado para el segmento del Aula Virtual")
    private UsuarioAulaVirtual usuarioAulaVirtual;

}
