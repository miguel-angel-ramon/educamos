package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno convocatorias", description = "Dto alumno convocatorias de evaluación")
public class AlumnoConvocatoriasDto {

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
    
    @Schema(description = "Id de la promoción")
    Long idPromocion;
	
    @Schema(description = "Id del estado de la promoción")
	Long idEstado;
	
    @Schema(description = "Descripción del estado de la promoción")
	String descripcionEstado;
	
    @Schema(description = "Fecha de la sesión de evaluacion")
	String fechaSesion;

}
