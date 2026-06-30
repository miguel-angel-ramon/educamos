package es.jccm.edu.shared.configuration.exception;

import java.net.ConnectException;
import java.nio.file.AccessDeniedException;

import javax.persistence.RollbackException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.NoTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import es.jccm.edu.shared.application.domain.error.ApiErrorResponse;
import es.jccm.edu.shared.application.domain.error.ApiNotFoundException;
import lombok.extern.slf4j.Slf4j;

//feature/HU.ARQ01.DEFLOGSBACK.02
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiErrorResponse> handleRuntimeException(Exception e) {
		log.error("ERROR RuntimeException.",e);
		return handleForINTERNALSERVERERROR(e);
	}

	/*
	 * @ExceptionHandler(NullPointerException.class) public
	 * ResponseEntity<ApiErrorResponse> handleNullPointerException(Exception e) {
	 * return handleForINTERNALSERVERERROR(e);
	 * 
	 * }
	 */

	@ExceptionHandler(ApiNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFoundException(Exception e) {
		log.error("ERROR ApiNotFoundException.",e);
		HttpStatus status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(new ApiErrorResponse(status, e.getMessage()), status);
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ApiErrorResponse> handleScurityException(Exception e) {
		log.error("ERROR SecurityException.",e);
		return handleForFORBIDDEN(e);
	}

	@ExceptionHandler(ConnectException.class)
	public ResponseEntity<ApiErrorResponse> handleConnectException(Exception e) {
		log.error("ERROR ConnectException.",e);
		return handleForINTERNALSERVERERROR(e);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(Exception e) {
		log.error("ERROR MethodArgumentNotValidException.",e);
		return handleForCONFLICT(e);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(Exception e) {
		log.error("ERROR MethodArgumentTypeMismatchException.",e);
		return handleForCONFLICT(e);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(Exception e) {
		log.error("ERROR IllegalArgumentException.",e);
		return handleForCONFLICT(e);
	}

	@ExceptionHandler(RollbackException.class) // no puede probar
	public ResponseEntity<ApiErrorResponse> handleRollbackException(Exception e) {
		log.error("ERROR RollbackException.",e);
		return handleForBADREQUEST(e);
	}

	@ExceptionHandler(NoTransactionException.class)
	public ResponseEntity<ApiErrorResponse> handleNoTransactionException(Exception e) {
		log.error("ERROR NoTransactionException.",e);
		return handleForBADREQUEST(e);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(Exception e) {
		log.error("ERROR AccessDeniedException.",e);
		return handleForFORBIDDEN(e);
	}

	public ResponseEntity<ApiErrorResponse> handleForBADREQUEST(Exception e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(new ApiErrorResponse(status, e.getMessage()), status);
	}

	public ResponseEntity<ApiErrorResponse> handleForCONFLICT(Exception e) {
		HttpStatus status = HttpStatus.CONFLICT;
		return new ResponseEntity<>(new ApiErrorResponse(status, e.getMessage()), status);
	}

	public ResponseEntity<ApiErrorResponse> handleForINTERNALSERVERERROR(Exception e) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(new ApiErrorResponse(status, e.getMessage()), status);
	}

	public ResponseEntity<ApiErrorResponse> handleForFORBIDDEN(Exception e) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		return new ResponseEntity<>(new ApiErrorResponse(status, e.getMessage()), status);
	}

}
