package es.jccm.edu.comunicaciones.application.ports.in.restablecerClaveUseCase;

public class RestablecerClaveException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -163759928198393938L;

	public RestablecerClaveException(String message) {
		super(message);
	}

	public RestablecerClaveException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
