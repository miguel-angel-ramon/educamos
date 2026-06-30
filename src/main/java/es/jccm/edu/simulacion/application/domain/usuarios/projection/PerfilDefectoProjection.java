package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Perfil por Defecto", description = "Rescata los perfiles por defecto del docente")

public interface PerfilDefectoProjection {

    @Schema(description = "Oid del empleado de Delphos")
    Long getOidUsuario();

    @Schema(description = "Rol por defecto del empleado")
    Long getRolDef();

    @Schema(description = "Centro por defecto del empleado")
    Long getCenDef();
}
