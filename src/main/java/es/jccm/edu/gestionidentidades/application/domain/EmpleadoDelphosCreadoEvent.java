package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmpleadoDelphosCreadoEvent {
	private TipoPersonal tipoPersonal;

	private PersonaId personaId;
	private String nombre;
	private String apellido1;
	private String apellido2;

	private Sexo sexo;
	private Date fechaNacimiento;
	
	private String centro;
	private String correoExterno;
	private int xusuario;
	private boolean centroPerteneceAccesoEducamos;
	private String fTomapos;
	
	private boolean equipoDirectivo;
	private String listaCargos;
	private String tipoPersonalEmpleado;
	private boolean tutorUnidad;
	private String cursoTutorUnidad;
	private String departamento;
	private String unidadOrganizativa;
	
	private String ptotraemp;
	private boolean comisioncoordinacionpedagojica;
	
}
