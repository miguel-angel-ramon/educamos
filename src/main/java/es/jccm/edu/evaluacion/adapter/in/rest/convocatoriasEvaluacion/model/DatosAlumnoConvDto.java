package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los datos del alumno de convocatorias de evaluación")
public class DatosAlumnoConvDto {

    @Schema(description = "Id del alumno")
    Long idAlumno;

    @Schema(description = "Id de la matrícula")
    Long idMatricula;

    @Schema(description = "Id materia matrícula del alumno")
    Long idMatMatriAlu;

    @Schema(description = "Id del grupo actividad")
    Long idGrupoActividad;

    @Schema(description = "Id oferta de la matrícula")
    Long idOfertaMatrig;

    @Schema(description = "Nombre del alumno")
    String nombre;

    @Schema(description = "Apellidos del alumno")
    String apellidos;

    @Schema(description = "Nota global del alumno")
    String nota;

    @Schema(description = "Indica si la nota es aprobada o no")
    String aprueba;

    @Schema(description = "Foto del alumno")
    byte[] foto;

    @Schema(description = "Año académico")
    Long anno;

    @Schema(description = "Id de la unidad")
    Long idUnidad;

    @Schema(description = "Id de la materia")
    Long idMateria;

    @Schema(description = "Nombre de la materia")
    String materia;

    @Schema(description = "Nombre del curso")
    String nombreCurso;

    @Schema(description = "Id de la convocatoria del centro omc")
    Long idConvCentroOmc;

    @Schema(description = "Nombre de la convocatoria")
    String convocatoria;

    @Schema(description = "Estado de la convocatoria")
    String estadoConv;

}
