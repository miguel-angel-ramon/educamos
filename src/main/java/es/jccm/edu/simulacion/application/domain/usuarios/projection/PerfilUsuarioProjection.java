package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PerfilUsuario", description = "Proyección para rescatar los perfiles de un usuario")
public interface PerfilUsuarioProjection {
	
	@Schema(description = "Id del perfil")
	Long getIdPerfil();
	
	@Schema(description = "Código del perfil")
	String getCodPerfil();
	
	@Schema(description = "Nombre del perfil")
	String getNombrePerfil();
	
}

