package es.jccm.edu.evaluacion.application.ports.out.exceptions;

public class EvaluacionException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EvaluacionException(Throwable cause) {
		super(cause);
	}

	public EvaluacionException(String message) {
		super(message);
	}
	
	public EvaluacionException(String message, Throwable cause) {
		super(message, cause);
	}
}
