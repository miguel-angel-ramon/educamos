package es.jccm.edu.gestionidentidades.application.ports.out.exceptions;


/**
 * @author fjluque
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ModuloAccesoException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5827734220279952896L;

	/**
     * @param message
     */
    public ModuloAccesoException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ModuloAccesoException(String message, Throwable cause) {
        super(message, cause);
    }
}
