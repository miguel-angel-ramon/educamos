package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;

/**
 * 
 * @author jesus
 *
 */
public class ResultSetException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800981621084371339L;

	public ResultSetException(Throwable cause) {
		super(cause);
	}

	public ResultSetException(String message) {
		super(message);
	}
	

}
