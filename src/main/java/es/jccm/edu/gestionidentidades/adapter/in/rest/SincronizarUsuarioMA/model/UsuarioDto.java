package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.gestionidentidades.application.domain.Persona;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Usuario", description = "Usuario de la plataforma")
public class UsuarioDto implements Serializable{

	private static final long serialVersionUID = 8752052544294658161L;

	private Long id;
	
	private Persona persona;

	private String login;

	private String clave;

	private String credencialesUnSoloUso;

	private String activo;

	private String bloqueado;

	private Long intentosFallidosAcceso;

	private Long oidAplicacionFavorita;

	private Date caducidad;

	private String correoE;

	private String modoAlta;

	private Date fLopd;

	private String cerberos;

	private String secretKey2FA;

}
