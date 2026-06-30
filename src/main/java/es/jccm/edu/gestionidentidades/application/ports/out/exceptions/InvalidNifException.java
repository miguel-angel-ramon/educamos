package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;

/**
 * 
 * @author jesus
 *
 */
public class InvalidNifException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800981621084371339L;

	public InvalidNifException(Throwable cause) {
		super(cause);
	}

	public InvalidNifException(String message) {
		super(message);
	}
	

}
