package es.jccm.edu.proyectosfct.adapter.in.rest.programas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "InfoAdicionalPlanDto", description = "Información completa sobre la información adicional del plan")
public class InfoAdicionalPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador del registro en la tabla FCT_INFO_ADICIONAL_PLAN")
    private Long id;

    @Schema(description = "Identificador del proyecto relacionado")
    private Long idProyecto;

    @Schema(description = "Descripción general de la información adicional")
    private String descripcion;

    @Schema(description = "Detalles sobre el calendario y horario")
    private String calendarioHorario;

    @Schema(description = "Resultado previsto del aprendizaje")
    private String resultadoPrevisto;

    @Schema(description = "Contenidos a desarrollar durante la formación")
    private String contenidosDesarrollar;

    @Schema(description = "Actividades formativas planeadas")
    private String actividadesFormativas;

    @Schema(description = "Mecanismo de coordinación y seguimiento")
    private String mecanismoCoordinacionSeguimiento;
}