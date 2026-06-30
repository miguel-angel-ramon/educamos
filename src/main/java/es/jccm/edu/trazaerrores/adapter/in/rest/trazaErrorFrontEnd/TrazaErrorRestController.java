package es.jccm.edu.trazaerrores.adapter.in.rest.trazaErrorFrontEnd;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.trazaerrores.adapter.in.rest.BasePath;
import es.jccm.edu.trazaerrores.adapter.in.rest.trazaErrorFrontEnd.model.ErrorClienteDTO;
import es.jccm.edu.trazaerrores.application.domain.entities.ErrorCliente;
import es.jccm.edu.trazaerrores.application.ports.in.ITrazaErroresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TrazaerroresBasePath)
@Tag(name = "Traza Errores", description = "...")
@CrossOrigin
public class TrazaErrorRestController {

	@Autowired
	private ITrazaErroresService trazaErroresService;
	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "...", description = "...", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json")) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/creaerrorfrontend")
	public void creaTrazaErrorFrontEnd(@RequestBody ErrorClienteDTO errorClienteDTO) {

		trazaErroresService.creaTrazaErrorFrontEnd(modelMapper.map(errorClienteDTO, ErrorCliente.class));
	}

	@SuppressWarnings("rawtypes")
	@Operation(summary = "...", description = "...", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json")) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/creaerrorfrontendasincrono")
	public CompletableFuture<ResponseEntity> creaTrazaErrorFrontEndAsincrono(
			@RequestBody ErrorClienteDTO errorClienteDTO) {
		return trazaErroresService.creaTrazaErrorFrontEndAsincrono(modelMapper.map(errorClienteDTO, ErrorCliente.class))
				.<ResponseEntity>thenApply(ResponseEntity::ok);
	}
}
