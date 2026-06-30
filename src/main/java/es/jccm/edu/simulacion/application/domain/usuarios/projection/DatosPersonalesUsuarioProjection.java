package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosPersonalesUsuario", description = "Proyección para rescatar datos personales rescatados de usuarios para el componente del escritorio")
public interface DatosPersonalesUsuarioProjection {
	
	@Schema(description = "DNI del usuario")
	String getDocumentacion();
	
	@Schema(description = "Dirección")
	String getDireccion();
	
	@Schema(description = "Provincia")
	String getProvincia();
	
	@Schema(description = "Municipio")
	String getMunicipio();
	
	@Schema(description = "Fecha de nacimiento")
	Date getFechaNacimiento();
	
	@Schema(description = "Sexo")
	String getSexo();
	
	@Schema(description = "Usuario LDAP")
	String getUsuarioLDAP();
	
	@Schema(description = "Teléfono")
	String getTelefono1();
	
	@Schema(description = "Teléfono movil")
	String getTelefono2();
	
	@Schema(description = "Teléfono de notificaciones")
	String getTelefonoNotificaciones();
	
	@Schema(description = "Correo")
	String getCorreo();
	
	@Schema(description = "Correo de notificaciones")
	String getCorreoNotificaciones();

	@Schema(description = "Correo LDAP")
	String getCorreoLdap();
	
}

