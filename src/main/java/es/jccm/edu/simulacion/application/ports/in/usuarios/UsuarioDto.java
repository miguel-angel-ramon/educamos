package es.jccm.edu.simulacion.application.ports.in.usuarios;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Usuario", description = "Datos rescatados de usuarios de un centro para el módulo de simulación de usuarios del escritorio")
public class UsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del usuario")
	private Long idUsuario;
	
	@Schema(description = "oId del Usuario")
	private String oIdUsuario;
	
	@Schema(description = "Nombre completo")
	private String nombre;
	
}
