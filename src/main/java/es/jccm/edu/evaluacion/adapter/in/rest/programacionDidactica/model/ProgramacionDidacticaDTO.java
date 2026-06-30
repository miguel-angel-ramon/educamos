package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaDepartamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.ElementCollection;

@Data
@Schema(name = "ProgramacionDidacticaDTO", description = "DTO Programación Didáctica")
public class ProgramacionDidacticaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la Programación Didáctica")
    private Long id;
    
    @Schema(description = "Id de la Materia")
    private Long materiaomg;
    
    @Schema(description = "Id del Curso")
    private Long ofertamatrig;
    
    @Schema(description = "Id del Centro")
    private Long centro;
    
    @Schema(description = "Año en que se imparte el Curso")
    private Integer anno;
    
    @Schema(description = "Indica si es un alumno ACNEAE")
    private Integer acneae;
    
    @Schema(description = "Indica si la Programación Didáctica está cerrada")
    private Integer cerrada;
    
    @Schema(description = "Id del Nivel Curricular")
    private Long niveadap;

    @Schema(description = "nombre del Nivel Curricular")
    private String nombreAdapt;

    @Schema(description = "Id materia omg acnee")
    private Long idMateriaOmgAdap;
    
    @Schema(description = "Ponderación asociada a la Programación Didáctica")
    private es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto ponderacion;
    
    @Schema(description = "Nombre de la Materia")
    private String nombreMateria;
    
    @Schema(description = "Nombre del Curso")
    private String nombreCurso;
    
    @Schema(description = "Nombre del Nivel Curricular")
    private String nombreNivelCurricular;
    
    @Schema(description = "Lista de Unidades de Programación asociadas a la Programación Didáctica")
    private List<UnidadProgramacionDTO> unidadesProgramacion;

    @Schema(description = "Id rodal")
    private String idRodal;

    @Schema(description = "Nombre del fichero")
    private String nombreFichero;

    @Schema(description = "departamento")
    private EvaDepartamento departamento;

    @ElementCollection(targetClass=HistorialResponsableProgramacionDidacticaDTO.class)
    private List<HistorialResponsableProgramacionDidacticaDTO> listaEditores;

}
