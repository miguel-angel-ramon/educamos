package es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AplicacionesUsuarioJwt", description = "Proyección para rescatar las aplicaciones de un usuario")
public interface AplicacionUsuarioJwtProjection {

    @Schema(description = "Oid de la aplicación")
    Long getOidAplicacion();

    @Schema(description = "Código de la aplicación")
    String getCodigo();

    /*@Schema(description = "Nombre de la aplicación")
    String getNombre();

    @Schema(description = "Información sobre la aplicación")
    String getInformacion();

    @Schema(description = "Nombre alternativo de la aplicación")
    String getNombreAlternativo();*/

}
