package es.jccm.edu.shared.application.domain.error;

public class ApiNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiNotFoundException() {
		super();
	}

	public ApiNotFoundException(String message) {
		super(message);
	}

}
