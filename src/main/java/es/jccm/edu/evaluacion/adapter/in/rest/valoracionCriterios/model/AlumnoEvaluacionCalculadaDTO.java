package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno evaluacion calculada", description = "Dto alumno evaluación calculada")
public class AlumnoEvaluacionCalculadaDTO {

	@Schema(description = "Id del alumno")
    Long idAlumno;

    @Schema(description = "Id de la matrícula")
    Long idMatricula;

    @Schema(description = "Id materia matrícula del alumno")
    Long idMatMatriAlu;

    @Schema(description = "Id numero escolar")
    String numEscolar;

    @Schema(description = "Nombre del alumno")
    String nombre;

    @Schema(description = "Apellidos del alumno")
    String apellidos;
    
    @Schema(description = "Nota de la competencia")
    Double nota;

    @Schema(description = "Descripción de la calificación")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Indica si el alumno se puede ver en la matriz del front o no")
    Long disabled;
    

    @Schema(description = "Foto del alumno")
    private byte[] foto;
    
    @Schema(description = "Indica si se ha seleccionado el alumno")
    boolean mostrar = false;

    @Schema(description = "Indica si el alumno tiene una materia llave sin aprobar")
    boolean materiaLlavePendiente = false;

    @Schema(description = "idMateriaOmg de la materia que hemos seleccionado")
    private Long idMateriaOmg;
    
    NotaGlobalCalculadaAlumnoMateriaTemporalDTO notaGlobalTemporal;
    
    List<ValoracionTemporalCompetenciaEspecificaAlumnoDTO> competencias;
	
}
