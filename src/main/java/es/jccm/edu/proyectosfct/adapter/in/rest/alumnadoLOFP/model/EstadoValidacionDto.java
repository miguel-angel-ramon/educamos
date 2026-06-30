package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Estado de Validación de un alumno en un plan")
public class EstadoValidacionDto {

    @Schema(description = "Usuario asociado a la validación")
    private String usuario;

    @Schema(description = "Nombre completo del empleado")
    private String nombre;

    @Schema(description = "Fecha de registro en formato dd/MM/yyyy")
    private String fecha;

    @Schema(description = "Estado de la validación")
    private String estado;
}
