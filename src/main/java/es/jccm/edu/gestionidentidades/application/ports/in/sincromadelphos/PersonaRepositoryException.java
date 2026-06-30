package es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos;

public class PersonaRepositoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778025351023511216L;

	public PersonaRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonaRepositoryException(String message) {
		super(message);
	}

}
