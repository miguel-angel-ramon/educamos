package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosTablaHorariosAnexo2PlamProjection", description = "Fuente de datos para la tabla Horarios para el AnexoIIPlan")
public interface DatosTablaHorariosAnexo2PlanProjection {

    @Schema(description = "Periodo")
    String getPeriodos();

    @Schema(description = "Horas")
    Double getHoras();

    @Schema(description = "Empresa")
    String getEmpresa();
}
