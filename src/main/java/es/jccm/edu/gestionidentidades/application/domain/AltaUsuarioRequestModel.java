package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AltaUsuarioRequestModel {
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nif;
	private String strFechaNacimiento; //fecha de nacimiento en formato "dd/MM/yyyy"
	
}
