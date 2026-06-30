package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.comunicaciones.application.ports.in.mensajes.IAdjuntosMensajesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Mensajes Escritorio", description = "Servicio para recuperar el módulo de mensajes del escritorio")

public class AdjuntosMensajesRestController {
	
	@Autowired
	private IAdjuntosMensajesService adjuntosMensajesService;
	
	/**
	 * Conecta con el proyecto de comunica y un fichero archivos adjunto de un mensaje.
	 *
	 * @param String idAdjunto
	 * @return byte[]
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recupera un adjunto de un mensaje", description = "Este metodo devuelve un fichero adjunto de un mensaje", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/mensajes/adjunto/{idAdjunto}")
    public ResponseEntity<byte[]> getFicheroAdjuntoMensaje(@PathVariable("idAdjunto") String idAdjunto) {
		    	
		return new ResponseEntity<>(adjuntosMensajesService.getFicheroAdjuntoMensaje(idAdjunto), HttpStatus.OK);
    }
    
    /**
	 * Conecta con el proyecto de comunica y recupera un fichero zip con los archivos adjuntos de un mensaje.
	 *
	 * @param List<String> idsAdjuntos
	 * @return byte[]
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recupera un fichero zip con los adjuntos de un mensaje", description = "Este metodo devuelve un fichero zip con los adjuntos de un mensaje", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/mensajes/adjuntos/all")
    public ResponseEntity<byte[]> getFicherosAdjuntosZIP(@RequestBody List<String> idsAdjuntos) {
    	
    	return new ResponseEntity<>(adjuntosMensajesService.getFicherosAdjuntosZIPMensaje(idsAdjuntos), HttpStatus.OK);
    }

}