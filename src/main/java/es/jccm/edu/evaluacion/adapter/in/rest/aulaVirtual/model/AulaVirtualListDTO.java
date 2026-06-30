package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AulaVirtualListDTO", description = "DTO Aulas virtuales rescatadas de la BBDD de comunica")
public class AulaVirtualListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del aula")
    private Long id;

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
    
    @Schema(description = "Id Curso (Formato cadena)")
    private String cursoString;
    
    @Schema(description = "Id MateriaOMG")
    private Long idMateriaOMG;
    
    @Schema(description = "Id OfertaMatrig")
    private Long idOfertaMatrig;
    
    @Schema(description = "Token de plataforma")
    private String tokenPlataforma;
    
    @Schema(description = "Datos del empleado para el segmento del Aula Virtual")
    private UsuarioAulaVirtualDTO usuarioAulaVirtual;
    
    @Schema(description = "Alumnos pertenecientes al Aula Virtual")
    private List<AlumnoDTO> alumnos;
    
    @Schema(description = "Alumnos pertenecientes a la Prog. de Aula")
    private List<AlumnoDTO> alumnosProgAula;
}
