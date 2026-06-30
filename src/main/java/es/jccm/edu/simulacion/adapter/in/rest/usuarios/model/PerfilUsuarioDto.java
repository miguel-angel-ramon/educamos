package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PerfilUsuario", description = "Perfil de un usuario rescatado para el módulo de simulación de usuarios del escritorio")
public class PerfilUsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del perfil")
	private Long idPerfil;
	
	@Schema(description = "Código del perfil")
	private String codPerfil;
	
	@Schema(description = "Nombre del perfil")
	private String nombrePerfil;
	
}
