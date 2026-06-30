package es.jccm.edu.shared.application.domain.error;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	private int code;
	private String status;
	private String message;
	private String stackTrace;
	private Object data;

	public ApiErrorResponse() {
		timestamp = new Date();
	}

	public ApiErrorResponse(HttpStatus status, String message) {
		this();

		this.code = status.value();
		this.status = status.name();
		this.message = message;
	}

	public ApiErrorResponse(HttpStatus status, String message, String stackTrace) {
		this(status, message);

		this.stackTrace = stackTrace;
	}

	public ApiErrorResponse(HttpStatus status, String message, String stackTrace, Object data) {
		this(status, message, stackTrace);

		this.data = data;
	}

}
