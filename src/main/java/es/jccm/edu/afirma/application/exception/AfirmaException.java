package es.jccm.edu.afirma.application.exception;

public class AfirmaException extends Exception {

  private static final long serialVersionUID = 1L;

  // Constructores por defecto sin args

  public AfirmaException() {
    super();
  }

  public AfirmaException(String message) {
    super(message);
  }

  public AfirmaException(Throwable cause) {
    super(cause);
  }

  public AfirmaException(String message, Throwable cause) {
    super(message, cause);
  }
}
