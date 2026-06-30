package es.jccm.edu.trazaerrores.application.ports.in;

import java.util.concurrent.CompletableFuture;

import es.jccm.edu.trazaerrores.application.domain.entities.ErrorCliente;

public interface ITrazaErroresService {
	void creaTrazaErrorFrontEnd(ErrorCliente errorCliente);

	CompletableFuture<Boolean> creaTrazaErrorFrontEndAsincrono(ErrorCliente errorCliente);

}
