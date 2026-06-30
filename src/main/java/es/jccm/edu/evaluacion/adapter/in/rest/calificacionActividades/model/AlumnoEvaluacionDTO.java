package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoEvaluacionDTO", description = "DTO Alumno Evaluación")
public class AlumnoEvaluacionDTO {

    @Schema(description = "Id del alumno")
    private Long idAlumno;

    @Schema(description = "Id de la matrícula")
    private Long idMatricula;

    @Schema(description = "Id materia matrícula del alumno")
    private Long idMatMatriAlu;

    @Schema(description = "Id numero escolar")
    private String numEscolar;

    @Schema(description = "Nombre del alumno")
    private String nombre;

    @Schema(description = "Apellidos del alumno")
    private String apellidos;

    @Schema(description = "Foto del alumno")
    private byte[] foto;

    @Schema(description = "Id nota global del alumno")
    private Long idNotAlu;

    @Schema(description = "Id de la calificación")
    private Long idCalifica;

    @Schema(description = "Nota de la competencia")
    private Float nota;

    @Schema(description = "Descripción de la calificación")
    private String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    private String aprueba;

    @Schema(description = "Indica si se ha seleccionado el alumno")
    private boolean mostrar = false;

    @Schema(description = "Competencias del alumno")
    private List<CompetenciaAlumnoDTO> competencias;
}