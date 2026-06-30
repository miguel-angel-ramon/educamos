package es.jccm.edu.shared.configuration.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiBackendException extends IOException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Errors.
	 */
	private final List<String> errors;

	/**
	 * Instantiates a new ws backend exception.
	 *
	 * @param message the message
	 * @param errors the errors
	 */
	public ApiBackendException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}
	
	/**
	 * Instantiates a new ws backend exception.
	 *
	 * @param message the message
	 * @param errors the errors
	 */
	public ApiBackendException(String message) {
		super(message);
		this.errors = new ArrayList<>();
	}

	/**
	 * Gets errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

}
