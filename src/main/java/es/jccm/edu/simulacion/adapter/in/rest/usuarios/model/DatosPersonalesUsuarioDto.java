package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosPersonalesUsuario", description = "Datos personales rescatados de usuarios para el componente del escritorio")
public class DatosPersonalesUsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "DNI del usuario")
	private String documentacion;
	
	@Schema(description = "Dirección")
	private String direccion;
	
	@Schema(description = "Provincia")
	private String provincia;
	
	@Schema(description = "Municipio")
	private String municipio;

	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha de nacimiento")
	private Date fechaNacimiento;
	
	@Schema(description = "Sexo")
	private String sexo;
	
	@Schema(description = "Usuario LDAP")
	private String usuarioLDAP;
	
	@Schema(description = "Teléfono")
	private String telefono1;
	
	@Schema(description = "Teléfono movil")
	private String telefono2;
	
	@Schema(description = "Teléfono de notificaciones")
	private String telefonoNotificaciones;
	
	@Schema(description = "Correo")
	private String correo;
	
	@Schema(description = "Correo de notificaciones")
	private String correoNotificaciones;

	@Schema(description = "Correo LDAP")
	private String correoLdap;
	
	@Schema(description = "Nombre")
	private String nombre;
	
	@Schema(description = "Apellidos")
	private String apellidos;
}
