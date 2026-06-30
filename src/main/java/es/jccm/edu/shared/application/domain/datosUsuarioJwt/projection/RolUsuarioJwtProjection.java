package es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AplicacionesUsuarioJwt", description = "Proyección para rescatar las aplicaciones de un usuario")
public interface RolUsuarioJwtProjection {

    @Schema(description = "Id del perfil")
    Long getIdPerfil();

    @Schema(description = "Código del perfil")
    String getCodigoPerfil();

    /*@Schema(description = "Descripción del perfil")
    String getDescripcionPerfil();

    @Schema(description = "UsuarioBD")
    String getUsuarioBD();

    @Schema(description = "Ámbito del perfil")
    Character getAmbitoPerfil();*/

}
