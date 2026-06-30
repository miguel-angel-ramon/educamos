package es.jccm.edu.shared.adapter.in.rest.segsocial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/segsocial")
public class SegSocialRestController {

	@Autowired
	@Qualifier("clientSegSocialRestTemplate")
	private IClientSegSocial segsocialsevice;	

	/**
	 * Creación de los Datos de Alumno Convenio Programa.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	@Operation(summary = "Creación de los datos de una plantilla para formulario", description = "Este metodo crea los datos de una plantilla para formulario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getToken")
	public ResponseEntity<String> getToken() {
		
		try {
			System.out.println("Entra");
			return new ResponseEntity<String>(segsocialsevice.getToken(), HttpStatus.OK);
		}
		catch (Unauthorized e) {

	        return new ResponseEntity<String>("No está autorizado para realizar esta operación", HttpStatus.UNAUTHORIZED);
	    }catch (Exception ignored) {
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Creación de los Datos de Alumno Convenio Programa.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	@Operation(summary = "Creación de los datos de una plantilla para formulario", description = "Este metodo crea los datos de una plantilla para formulario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCompanias")
	public ResponseEntity<String> getCompanias() {
		
		try {
			System.out.println("Entra");
			segsocialsevice.getCompanias();
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		catch (Unauthorized e) {

	        return new ResponseEntity<String>("No está autorizado para realizar esta operación", HttpStatus.UNAUTHORIZED);
	    }catch (Exception ignored) {
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
