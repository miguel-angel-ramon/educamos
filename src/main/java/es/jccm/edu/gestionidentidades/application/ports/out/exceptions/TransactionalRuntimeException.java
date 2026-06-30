package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;

public class TransactionalRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473956159881323043L;

	public TransactionalRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransactionalRuntimeException(String message) {
		super(message);
	}

}
