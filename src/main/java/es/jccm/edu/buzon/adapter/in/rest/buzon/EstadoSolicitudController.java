package es.jccm.edu.buzon.adapter.in.rest.buzon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.EstadoSolicitudDTO;
import es.jccm.edu.buzon.application.ports.in.estadoSolicitud.IEstadoSolicitudService;
import es.jccm.edu.buzon.application.services.estadoSolicitud.EstadoSolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/buzon/estadoSolicitud")
//@CrossOrigin
public class EstadoSolicitudController {
	
	@Autowired
	private final IEstadoSolicitudService estadoSolicitudService;	
	
	@Autowired
    public EstadoSolicitudController(EstadoSolicitudService estadoSolicitudService) {
        this.estadoSolicitudService = estadoSolicitudService;
    }
	
	@Operation(summary = "Devuelve el estado de la solicitud del correo de los alumnos", description = "Este método devuelve el estado de la solicitud del correo de los alumnos",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSolicitudes")
	public ResponseEntity<List<EstadoSolicitudDTO>> getSolicitudes() {
		try{
			List<EstadoSolicitudDTO> solicitudesDto = estadoSolicitudService.findAll();
			return new ResponseEntity<List<EstadoSolicitudDTO>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
	    }		
	}
	
	// Método auxiliar para manejar excepciones
		private <T> ResponseEntity<T> handleException(Exception e) {
	        if (e instanceof Unauthorized) {        
	        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        } else if (e instanceof Forbidden) {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        } else { 
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
