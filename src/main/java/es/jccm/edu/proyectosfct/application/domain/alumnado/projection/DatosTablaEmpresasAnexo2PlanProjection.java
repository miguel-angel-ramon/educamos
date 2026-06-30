package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosTablaEmpresasAnexoIIPlanProjection", description = "Fuente de datos para la tabla empresas para el AnexoIIPlan")
public interface DatosTablaEmpresasAnexo2PlanProjection {

    @Schema(description = "Denominación")
    String getDenominacion();

    @Schema(description = "Email del empleado")
    String getMailemp();

    @Schema(description = "Cif")
    String getCif();

    @Schema(description = "Nombre del tutor")
    String getNombretut();

    @Schema(description = "Mail del tutor")
    String getMailtut();

    @Schema(description = "Telefono de la empresa")
    String getTlfemp();
    
    @Schema(description = "Dni del tutor")
    String getDnitut();

}
