package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Datos por defecto del docente", description = "Rescata los datos de centro y perfil por defecto del docente")
public class PerfilDefectoUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Oid del Usuario")
    private Long oidUsuario;

    @Schema(description = "Rol por defecto")
    private Long rolDef;

    @Schema(description = "Centro por defecto")
    private Long cenDef;


}
