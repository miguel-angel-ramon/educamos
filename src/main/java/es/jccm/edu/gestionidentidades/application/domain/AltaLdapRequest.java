package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AltaLdapRequest {
	private String documento;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String correoRecuperacion;
	private String sufijoEmail;
	private TipoPersonal tipoPersonal;
	
}
