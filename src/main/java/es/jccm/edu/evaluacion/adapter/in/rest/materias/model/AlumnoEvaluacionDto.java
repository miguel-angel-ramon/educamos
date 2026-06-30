package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Alumno evaluacion", description = "Dto alumno evaluación")
public class AlumnoEvaluacionDto {

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

    @Schema(description = "Foto del alumno")
    private byte[] foto;

    @Schema(description = "Id nota global del alumno")
    Long idNotAlu;

    @Schema(description = "Id de la calificación")
    Long idCalifica;

    @Schema(description = "Nota de la competencia")
    Float nota;

    @Schema(description = "Descripción de la calificación")
    String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;
    
    @Schema(description = "Si es o no acneae")
    Long disabled;

    @Schema(description = "Indica si se ha seleccionado el alumno")
    boolean mostrar = false;

    @Schema(description = "Indica si el alumno tiene una materia llave sin aprobar")
    boolean materiaLlavePendiente = false;

    @Schema(description = "idMateriaOmg de la materia que hemos seleccionado")
    private Long idMateriaOmg;

    @Schema(description = "Competencias del alumno")
    List<CompetenciaAlumnoDto> competencias;
}
