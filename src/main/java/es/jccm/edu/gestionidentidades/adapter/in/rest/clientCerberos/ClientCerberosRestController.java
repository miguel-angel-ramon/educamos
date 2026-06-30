package es.jccm.edu.gestionidentidades.adapter.in.rest.clientCerberos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import es.jccm.edu.gestionidentidades.application.ports.in.ICerberosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@Controller
@RequestMapping("${spring.data.rest.base-path:/api}" + "/gestionidentidades")
public class ClientCerberosRestController {
	
	@Autowired
	ICerberosService cerberosService;
	
    
    @Operation(summary = "Refresca un usuario", description = "Este método borra la caché para refrescar un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PutMapping("/borrarCache")
	public ResponseEntity<?> borrarCache(@RequestParam String username) {
		try{
			cerberosService.refrescarUsuario(username);
			return new ResponseEntity<String>("La petición se ha realizado con éxito",HttpStatus.OK);
		}catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("error", "No está autorizado para realizar esta operación");
	        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("error", "Acceso prohibido");
	        return new ResponseEntity<>(errorMap, HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("error", e.getMessage());
	        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
	    }		
	}
}
