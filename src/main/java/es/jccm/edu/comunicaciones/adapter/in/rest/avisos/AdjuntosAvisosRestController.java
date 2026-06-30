package es.jccm.edu.comunicaciones.adapter.in.rest.avisos;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model.AvisoAdjuntosDto;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.IAdjuntosAvisosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Avisos Escritorio", description = "Servicio para recuperar el módulo de avisos del escritorio")
//@CrossOrigin
public class AdjuntosAvisosRestController {
	
	@Autowired
	private IAdjuntosAvisosService adjuntosAvisosService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Conecta con el proyecto de comunica y trae archivos adjuntos de un aviso.
	 *
	 * @param String idAviso
	 * @return List<AvisoAdjuntosDto>
	 */
	//////@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recuperar adjuntos de un aviso", description = "Este metodo devuelve un objeto List con todos los adjuntos del aviso introducido", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/avisos/adjuntos/lista/{idAviso}")
    public ResponseEntity<List<AvisoAdjuntosDto>> getListaFicherosAdjuntoAviso(@PathVariable("idAviso") String idAviso) {
		
		List<AvisoAdjuntosDto> adjuntosAvisosOut = adjuntosAvisosService.getListaFicherosAdjuntoAviso(idAviso).stream().map(adjuntoAviso -> modelMapper.map(adjuntoAviso, AvisoAdjuntosDto.class)).collect(Collectors.toList());
		    	
		return new ResponseEntity<>(adjuntosAvisosOut, HttpStatus.OK);
    }
	
    /**
	 * Conecta con el proyecto de comunica y un fichero archivos adjunto de un aviso.
	 *
	 * @param String idAdjunto
	 * @return byte[]
	 */
	////@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recupera un adjunto de un aviso", description = "Este metodo devuelve un fichero adjunto de un aviso", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/avisos/adjunto/{idAdjunto}")
    public ResponseEntity<byte[]> getFicheroAdjuntoAviso(@PathVariable("idAdjunto") String idAdjunto) {
		    	
		return new ResponseEntity<>(adjuntosAvisosService.getFicheroAdjuntoAviso(idAdjunto), HttpStatus.OK);
    }
    
    /**
	 * Conecta con el proyecto de comunica y recupera un fichero zip con los archivos adjuntos de un aviso.
	 *
	 * @param List<String> idsAdjuntos
	 * @return byte[]
	 */
	////@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recupera un fichero zip con los adjuntos de un aviso", description = "Este metodo devuelve un fichero zip con los adjuntos de un aviso", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/avisos/adjuntos/all")
    public ResponseEntity<byte[]> getFicherosAdjuntosZIP(@RequestBody List<String> idsAdjuntos) {
    	
    	return new ResponseEntity<>(adjuntosAvisosService.getFicherosAdjuntosZIPAviso(idsAdjuntos), HttpStatus.OK);
    }
}