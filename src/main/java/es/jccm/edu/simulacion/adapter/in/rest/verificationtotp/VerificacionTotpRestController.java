package es.jccm.edu.simulacion.adapter.in.rest.verificationtotp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.annotations.Totp;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.BasePath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(BasePath.FixmeSimulacionVeriftotpBasePath)
@Tag(name = "Servicio verificacion totp", description = "Servicio para la verificacion totp")
@CrossOrigin
public class VerificacionTotpRestController {

	
	@Totp
	@Operation(summary = "Verificacion doble factor", description = "Este método verifica correctamente el doble factor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/verificacionDobleFactor")
	public ResponseEntity<Boolean> verificacionDobleFactor(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestHeader(Constants.TOTP2FA) String code) {

		log.info("Se inicia proceso de verificación con doble factor");
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
}