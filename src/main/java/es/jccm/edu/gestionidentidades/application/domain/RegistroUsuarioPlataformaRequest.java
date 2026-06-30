package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.domain.Sexo;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistroUsuarioPlataformaRequest {
	
	private TipoPersonal tipoPersonal;
	private PersonaId personaId;
	private String correoExterno;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Sexo sexo;
	private Date fechaNacimiento;
	private String identificacion;
	private String tipide;
	private Integer xCentro;
	private Date fechaTomaPos;
	
	public RegistroUsuarioPlataformaRequest(TipoPersonal tipoPersonal, PersonaId personaId, String correoExterno,
			String nombre, String apellido1, String apellido2, Sexo sexo, Date fechaNacimiento, String identificacion,
			String tipide, Date fechaTomaPos) {
		super();
		this.tipoPersonal = tipoPersonal;
		this.personaId = personaId;
		this.correoExterno = correoExterno;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
		this.identificacion = identificacion;
		this.tipide = tipide;
		this.fechaTomaPos = fechaTomaPos;
	}

	public RegistroUsuarioPlataformaRequest(TipoPersonal tipoPersonal, PersonaId personaId, String correoExterno,
			String nombre, String apellido1, String apellido2, Sexo sexo, Date fechaNacimiento, String identificacion,
			String tipide) {
		super();
		this.tipoPersonal = tipoPersonal;
		this.personaId = personaId;
		this.correoExterno = correoExterno;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
		this.identificacion = identificacion;
		this.tipide = tipide;
	}
	
	

}
