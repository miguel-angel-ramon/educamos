package es.jccm.edu.trazaerrores.application.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.jccm.edu.trazaerrores.application.domain.entities.ErrorCliente;
import es.jccm.edu.trazaerrores.application.ports.in.ITrazaErroresService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrazaErroresService implements ITrazaErroresService {
	@Override
	public void creaTrazaErrorFrontEnd(ErrorCliente errorCliente) {
		String message = formateadorMensaje(errorCliente);
		switch (errorCliente.getLogLevel().toLowerCase()) {
		case "error":
			log.error(message);
			break;
		case "warn":
			log.warn(message);
			break;
		case "debug":
			log.debug(message);
			break;
		case "trace":
			log.trace(message);
			break;
		case "info":
			log.info(message);
			break;
		default:
			throw new IllegalArgumentException(message);
		}

	}

	@Override
	@Async
	public CompletableFuture<Boolean> creaTrazaErrorFrontEndAsincrono(ErrorCliente errorCliente) {
		String message = formateadorMensaje(errorCliente);

		switch (errorCliente.getLogLevel().toLowerCase()) {
		case "error":
			log.error(message);
			break;
		case "warn":
			log.warn(message);
			break;
		case "debug":
			log.debug(message);
			break;
		case "trace":
			log.trace(message);
			break;
		case "info":
			log.info(message);
			break;
		default:
			return CompletableFuture.completedFuture(false);
		}
		return CompletableFuture.completedFuture(true);
	}

	public String formateadorMensaje(ErrorCliente errorClienteDTO) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb.append("Error remitido en fecha ");
		sb.append(errorClienteDTO.getTimestamp() != null ? errorClienteDTO.getTimestamp().toString() : "*");
		sb.append(" enviado por el usuario ");
		sb.append(errorClienteDTO.getUserName() != null ? errorClienteDTO.getUserName() : "*");
		sb.append(" desde la ");
		sb.append(errorClienteDTO.getIp() != null ? errorClienteDTO.getIp() : "*");
		sb.append(" ip utilizando el navegador ");
		sb.append(errorClienteDTO.getBrowser() != null ? errorClienteDTO.getBrowser() : "*");
		sb.append(". El error se ha producido en el módulo ");
		sb.append(errorClienteDTO.getModuleName() != null ? errorClienteDTO.getModuleName() : "*");
		sb.append(". La traza del error es la siguiente:");
		sb.append(errorClienteDTO.getStackTrace() != null ? errorClienteDTO.getStackTrace() : "*");
		if (errorClienteDTO.getParams() != null && !errorClienteDTO.getParams().isEmpty()) {
			for (String param : errorClienteDTO.getParams()) {
				sb2.append(param);
				sb2.append(",");
			}
			sb2.deleteCharAt(sb2.length() - 1);
			sb.append("Y por último los parámetros recopilados en el error son: ");
			sb.append(sb2);
		}
		return sb.toString();
	}
}
