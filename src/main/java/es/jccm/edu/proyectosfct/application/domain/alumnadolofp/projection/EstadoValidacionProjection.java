package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Estado de Validación", description = "Proyección para el estado de validación de un plan")
public interface EstadoValidacionProjection {

    @Schema(description = "Usuario asociado a la validación")
    String getUsuario();

    @Schema(description = "Nombre completo del empleado")
    String getNombre();

    @Schema(description = "Fecha de registro")
    String getFecha();

    @Schema(description = "Estado de la validación")
    String getEstado();
}