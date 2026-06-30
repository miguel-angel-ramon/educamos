package es.jccm.edu.shared.application.domain.usuarios;

import lombok.Data;

@Data
public class Usuario {

	private String oid;
	
	private String nombre;
	
	private String apellidos;
	
	private String email;
	
	private String login;
	
	private String apellido1;
	
	private String apellido2;
	
	private String nif;
	
	private String strFechaNacimiento;
	
}
