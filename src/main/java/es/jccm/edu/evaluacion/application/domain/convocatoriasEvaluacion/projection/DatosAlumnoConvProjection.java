package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los datos del alumno de convocatorias de evaluación")
public interface DatosAlumnoConvProjection {

    @Schema(description = "Id del alumno")
    Long getIdAlumno();

    @Schema(description = "Id de la matrícula")
    Long getIdMatricula();

    @Schema(description = "Id materia matrícula del alumno")
    Long getIdMatMatriAlu();

    @Schema(description = "Id del grupo actividad")
    Long getIdGrupoActividad();

    @Schema(description = "Id oferta de la matrícula")
    Long getIdOfertaMatrig();

    @Schema(description = "Id número escolar")
    String getNumEscolar();

    @Schema(description = "Nombre del alumno")
    String getNombre();

    @Schema(description = "Apellidos del alumno")
    String getApellidos();

    @Schema(description = "Nota global del alumno")
    String getNota();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();

    @Schema(description = "Año académico")
    Long getAnno();

    @Schema(description = "Id de la unidad")
    Long getIdUnidad();

    @Schema(description = "Id de la materia")
    Long getIdMateria();

    @Schema(description = "Nombre de la materia")
    String getMateria();

    @Schema(description = "Nombre del curso")
    String getNombreCurso();

    @Schema(description = "Id de la convocatoria del centro omc")
    Long getIdConvCentroOmc();

    @Schema(description = "Nombre de la convocatoria")
    String getConvocatoria();

    @Schema(description = "Estado de la convocatoria")
    String getEstadoConv();
}
