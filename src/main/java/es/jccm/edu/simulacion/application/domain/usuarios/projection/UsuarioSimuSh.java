package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public interface UsuarioSimuSh {
    
    @Schema(description = "OID")
    Long getOid();

    @Schema(description = "Nombre")
    String getNombre();

    @Schema(description = "Apellido 1")
    String getApellido1();

    @Schema(description = "Apellido 2")
    String getApellido2();



}
