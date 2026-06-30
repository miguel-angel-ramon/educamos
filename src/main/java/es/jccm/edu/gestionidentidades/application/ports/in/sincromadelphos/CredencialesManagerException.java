package es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos;

public class CredencialesManagerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7637811847785841752L;

	public CredencialesManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CredencialesManagerException(String message) {
		super(message);
	}

}
