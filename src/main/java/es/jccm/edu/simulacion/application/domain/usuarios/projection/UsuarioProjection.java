package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Usuario", description = "Proyección para rescatar los datos de un usuario")
public interface UsuarioProjection {
	
	@Schema(description = "Id del usuario")
	Long getIdUsuario();
	
	@Schema(description = "oId del Usuario")
	String getOIdUsuario();
	
	@Schema(description = "Nombre completo")
	String getNombre();
	
}

