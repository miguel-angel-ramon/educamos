package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import lombok.Data;

@Data
public class NuevoUsuarioPlataformaDesdeDelphos {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoPersonal;
	
	private String dni;
	
	private String tipide;
	
	private String correoExterno;
	
	private String nombre;
	
	private String apellido1;
	
	private String apellido2;
	
	private Sexo sexo;
	
	private Date fechaNacimiento;
	
	private Date fechaTomaPos;
	
	private Integer xCentro;

}
