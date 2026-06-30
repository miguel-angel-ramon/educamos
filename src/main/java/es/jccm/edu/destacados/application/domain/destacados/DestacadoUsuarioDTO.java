package es.jccm.edu.destacados.application.domain.destacados;

import lombok.Data;

@Data
public class DestacadoUsuarioDTO {
	  private Long id;
	    private Long destacado;  // Cambia esto a Long para coincidir con el ID
	    private Long oidUsuario;
	    private Long numeroOrden;
	    private boolean activo;
}
