package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CriterioAlumnoDTO", description = "DTO Criterios Alumno")
public class CriterioAlumnoDTO {

    @Schema(description = "Id del criterio")
    private Long idCriterio;

    @Schema(description = "Abreviatura del criterio")
    String abreviatura;

    @Schema(description = "Descripción del criterio")
    String descripcion;

    @Schema(description = "Porcentaje del criterio")
    private Float porcentaje;

    @Schema(description = "Id de la operación del criterio")
    private Long idTipoOperacion;

    @Schema(description = "Nombre de la operación del criterio")
    private String nombreTipoOperacion;

    @Schema(description = "Id criterio alumno")
    private Long idCrialu;

    @Schema(description = "Id de la calificacion")
    private Long idCalifica;
    
    @Schema(description = "Id de la actividad a la que pertenece")
    private Long idActividad;

    @Schema(description = "Nota del criterio")
    private Long nota;

    @Schema(description = "Id de la unidad de programación")
    private Long idUnidadProgramacion;
    
    @Schema(description = "Descripción de la nota del alumno")
    private String descCal;

    @Schema(description = "Indica si la nota es aprobada o no")
    private String aprueba;
    
    @Schema(description = "Calificacion criterio actividad alumno")
    private ValoracionCriterioActividadAlumnoDTO valoracion;
    
    @Schema(description = "Peso del criterio en la actividad")
    private Integer peso;

    @Schema(description = "Si proviene de moodle")
    private Integer lprocedeMoodle;

    @Schema(description = "Indica si una nota a sido cambiada")
    private Boolean notaChanged = false;
    
    @Schema(description = "Abreviatura")
    private Long usuCreacion;
}