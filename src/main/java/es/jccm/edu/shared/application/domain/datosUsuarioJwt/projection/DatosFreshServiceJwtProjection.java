package es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AplicacionesUsuarioJwt", description = "Proyección para rescatar los datos necesarios para la llamada al formulario soporte técnico")
public interface DatosFreshServiceJwtProjection {
	
    @Schema(description = "Descripción del Nif")
    String getNif();
    
    @Schema(description = "Identificacion del perfil")
    Long getPerfil();
    
    @Schema(description = "Descripción del código del perfil")
    String getCodigo();    
    
    @Schema(description = "Descripción del teléfono")
    String getTelefono();
    
    @Schema(description = "Descripción correo del recuperación")
    String getCorreo();    


}
