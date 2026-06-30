package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
@Schema(description = "DTO para representar los datos de Seguridad Social asociados a un alumno y su plan")
public class DatosSeguridadSocialAlumnoPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador del Convenio Proyecto Alumno")
    private Long idConvProyAlu;

    @Schema(description = "Nombre de la empresa asociada")
    private String empresa;

    @Schema(description = "id de la empresa asociada")
    private Integer idEmpresa;

    @Schema(description = "Indica si el centro asume la responsabilidad de Seguridad Social (0: No, 1: Sí)")
    private Integer centroSS;

    @Schema(description = "Indica si el alumno tiene relación con Erasmus (0: No, 1: Sin beca, 2: Con beca)")
    private Integer erasmus;
    
    @Schema(description = "Identificador del id convProy")
    private Long idProyecto;

}