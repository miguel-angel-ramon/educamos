package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoDetalle", description = "Detalles de los alumnos de un centro, año y curso")
public class AlumnoDetalleDto {
	
	@Schema(description = "Id del usuario en la tabla UsuariosT")
    Long oidUsuario;
	
	@Schema(description = "Nombre completo del alumno")
    String nombreCompleto;

    @Schema(description = "Nombre de la unidad de los alumnos")
    String unidad;

    @Schema(description = "Estado del correo del alumnado")
    Integer estado;

    @Schema(description = "Permiso que tiene el alumnado")
    String codPermiso;

    @Schema(description = "Pendiente de actualización")
    String lPendiente;

}

