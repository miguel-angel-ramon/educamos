package es.jccm.edu.gestionidentidades.application.ports.in.login;

/**
 * Representa un problema que impide que el usuario se logue en la aplicación
 * 
 * @author jesus
 *
 */
public enum ProblemaLogin {

	USUARIO_BLOQUEADO("El usuario esta bloqueado"), USUARIO_DESACTIVADO("El usuario esta desactivado"),
	CLAVE_CADUCADA("La clave del usuario está caducada"), USUARIO_NO_REGISTRADO("Usuario o clave incorrectos"),
	ERROR_INTERNO("Error interno"), USUARIO_PRIMER_ACCESO_PROFESOR("Usuario primer acceso profesor"),
	USUARIO_PRIMER_ACCESO_CIUDADANO("Usuario primer acceso ciudadano");

	private String descripcion;

	ProblemaLogin(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
