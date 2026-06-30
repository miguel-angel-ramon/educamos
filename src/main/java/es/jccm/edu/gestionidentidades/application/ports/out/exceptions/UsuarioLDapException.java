package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;

public class UsuarioLDapException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8233195161959664274L;

	public UsuarioLDapException(Throwable cause) {
		super(cause);
	}

	public UsuarioLDapException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioLDapException(String message) {
		super(message);
	}

}
