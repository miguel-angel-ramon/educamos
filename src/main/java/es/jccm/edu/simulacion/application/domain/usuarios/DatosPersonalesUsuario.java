package es.jccm.edu.simulacion.application.domain.usuarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class DatosPersonalesUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String documentacion;
	
	private String direccion;
	
	private String provincia;
	
	private String municipio;
	
	private Date fechaNacimiento;
	
	private String sexo;
	
	private String usuarioLDAP;

	private String telefono1;
	
	private String telefono2;
	
	private String telefonoNotificaciones;
	
	private String correo;
	
	private String correoNotificaciones;

	private String correoLdap;

}
