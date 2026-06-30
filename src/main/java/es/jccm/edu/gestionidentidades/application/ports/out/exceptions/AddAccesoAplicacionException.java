package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;

public class AddAccesoAplicacionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2203541079053352732L;

	public AddAccesoAplicacionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddAccesoAplicacionException(String message) {
		super(message);
	}


}
