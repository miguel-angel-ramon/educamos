package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import lombok.Data;

@Data
public class DatosUsuarioLdap {
	
	private String atributoUid;
	
	private String atributoNombre;
	
	private String atributoApellido;
	
	private String atributoCnumide;	
	
	private String correo;
	

}
