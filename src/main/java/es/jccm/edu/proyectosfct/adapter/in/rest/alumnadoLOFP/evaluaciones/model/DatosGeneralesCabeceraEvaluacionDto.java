package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosGeneralesCabeceraEvaluacionDto", description = "DTO que contiene la información general de la evaluación por empresa")
public class DatosGeneralesCabeceraEvaluacionDto {

    @Schema(description = "ID de la empresa")
    private Long idEmpresa;

    @Schema(description = "Nombre de la empresa")
    private String nombreEmpresa;

    @Schema(description = "Descripción del plan")
    private String descripcionPlan;

    @Schema(description = "Curso")
    private String curso;

    @JsonProperty("xEmpleadoTutorDual")@Schema(description = "ID del empleado tutor dual")
    private Long xEmpleadoTutorDual;

    @Schema(description = "Nombre del tutor dual")
    private String nombreTutorDual;

    @Schema(description = "ID del tutor de empresa")
    private Long idEmpleadoTutorEmpresa;

    @Schema(description = "Nombre del tutor de empresa")
    private String nombreTutorEmpresa;

    @Schema(description = "Número total de horas en la empresa asociada")
    private Integer numHorasTotalesEmpresa;
    
    @Schema(description = "Número total de horas en la empresa calculadas")
    private String numHorasTotalesCalculadas;
    
    @Schema(description = "Telefono tutor centro")
    private String tlfTutorCen;
    
    @Schema(description = "Telefono tutor empresa")
    private String tlfTutorEmp;

    @Schema(description = "Departamento tutor centro")
    private String depCen;

    @Schema(description = "Departamento tutor empresa")
    private String depTutorEmp;

    @Schema(description = "Correo tutor centro")
    private String correoCen;

    @Schema(description = "Correo tutor empresa")
    private String correoEmp;
    
    
    
}
