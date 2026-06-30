package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioPlataformaRegistroEvent {
	
	private TipoPersonal tipoPersonal;
	private PersonaId personaId;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Sexo sexo;
	private Date fechaNacimiento;
	private String correoExterno;

}
