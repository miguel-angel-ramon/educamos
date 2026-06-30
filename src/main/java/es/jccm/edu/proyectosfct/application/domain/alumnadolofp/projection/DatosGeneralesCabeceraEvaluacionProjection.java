package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Datos Generales de la Evaluación", description = "Proyección con información sobre el plan, curso y tutores")
public interface DatosGeneralesCabeceraEvaluacionProjection {

    @Schema(description = "Descripción del plan de formación")
    String getDescripcionPlan();

    @Schema(description = "Nombre del curso asociado")
    String getCurso();

    @Schema(description = "ID del tutor dual")
    Long getXEmpleado();

    @Schema(description = "Nombre completo del tutor dual")
    String getNombreTutorDual();

    @Schema(description = "ID del tutor de la empresa")
    Long getIdEmpleado();

    @Schema(description = "Nombre completo del tutor de la empresa")
    String getNombreTutorEmpresa();

    @Schema(description = "Número total de horas en la empresa asociada")
    Integer getNumHorasTotalesEmpresa();
    
    @Schema(description = "Número total de horas en la empresa calculadas")
    String getNumHorasTotalesCalculadas();
    
    @Schema(description = "Telefono tutor centro")
    String getTlfTutorCen();
    
    @Schema(description = "Telefono tutor empresa")
    String getTlfTutorEmp();
    
    @Schema(description = "Departamento tutor centro")
    String getDepCen();
    
    @Schema(description = "Departamento tutor empresa")
    String getDepTutorEmp();
    
    @Schema(description = "Correo tutor centro")
    String getCorreoCen();
    
    @Schema(description = "Correo tutor empresa")
    String getCorreoEmp();
}
