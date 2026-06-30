package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolUsuarioJwt implements Serializable {

	private Long idPerfil;

	private String codigoPerfil;

	/*private String descripcionPerfil;

	private String usuarioBD;

	private Character ambitoPerfil;*/

}
