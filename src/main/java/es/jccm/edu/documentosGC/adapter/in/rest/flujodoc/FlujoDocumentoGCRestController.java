package es.jccm.edu.documentosGC.adapter.in.rest.flujodoc;

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

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.EstadoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.flujodoc.model.FlujoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.perfiles.model.PerfilGCDto;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.perfiles.entities.PerfilGC;
import es.jccm.edu.documentosGC.application.ports.in.flujodoc.IFlujoDocumentosGCService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicio Flujo Documentos GC", description = "Servicio con las operaciones sobre los flujos de estados de Documentos Gestión de Centros")
public class FlujoDocumentoGCRestController {
	
	@Autowired
	private IFlujoDocumentosGCService  flujoDocService;
	
	@Autowired
	ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Count documentos sin adjuntos", description = "Este metodo devuelve numero documentos sin adjuntos a un flujo", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/countDocumentosSinAdjuntosByIdFlujo/{idFlujo}")
	public ResponseEntity<Integer> countDocumentosSinAdjuntosByIdFlujo(@PathVariable("idFlujo") Long idFlujo) {
		
		Integer count =  flujoDocService.countDocumentosSinAdjuntosByIdFlujo(idFlujo);
		
		return new ResponseEntity<>(count, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Count documentos adjuntos", description = "Este metodo devuelve numero documentos adjuntos a un flujo", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/countDocumentosAdjuntosByIdFlujo/{idFlujo}")
	public ResponseEntity<Integer> countDocumentosAdjuntosByIdFlujo(@PathVariable("idFlujo") Long idFlujo) {
		
		Integer count =  flujoDocService.countDocumentosAdjuntosByIdFlujo(idFlujo);
		
		return new ResponseEntity<>(count, HttpStatus.OK);		
	}

	/*@Operation(summary = "Flujo siguiente documento", description = "Este metodo devuelve la información del siguente flujo del documento ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/flujoSiguienteDocumentosGC/{idEstadoOri}/{idEstadoDes}/{idPerfil}/{idTipoDoc}")
	public ResponseEntity<FlujoDocumentoGCDto> flujoSiguienteDocumentosGC(@PathVariable("idEstadoOri") Long idEstadoOri,
																		  @PathVariable("idEstadoDes") Long idEstadoDes,
																		  @PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("idTipoDoc") Long idTipoDoc) {
		
		FlujoDocumentoGC flujo =  flujoDocService.flujoSiguienteDocumentosGC(idEstadoOri,idEstadoDes,idPerfil,idTipoDoc);
		
		FlujoDocumentoGCDto flujoDto = modelMapper.map(flujo, FlujoDocumentoGCDto.class);  ;		
		
		return new ResponseEntity<FlujoDocumentoGCDto>(flujoDto, HttpStatus.OK);		
		
	} */

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estados Flujo", description = "Este metodo devuelve todos los Estados Flujo de un tipo de documento", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosFlujosByIdTipoDocumento/{idTipoDocumento}")
	public ResponseEntity<List<FlujoDocumentoGCDto>> getEstadosFlujosByIdTipoDocumento(@PathVariable("idTipoDocumento") Long idTipoDocumento) {
		
		List<FlujoDocumentoGC> estadosFlujo =  flujoDocService.getEstadosFlujosByIdTipoDocumento(idTipoDocumento);
		
		List<FlujoDocumentoGCDto> estadosFlujoDto = estadosFlujo.stream().map(entity -> modelMapper.map(entity, FlujoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(estadosFlujoDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estados Flujo Origen", description = "Este metodo devuelve todos los Estados Origen", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosOrigen")
	public ResponseEntity<List<EstadoDocumentoGCDto>> getEstadosOrigen() {
		
		List<EstadoDocumentoGC> estados =  flujoDocService.getEstadosOrigen();
		
		List<EstadoDocumentoGCDto> estadosDto = estados.stream().map(entity -> modelMapper.map(entity, EstadoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(estadosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estados Flujo Destino", description = "Este metodo devuelve todos los Estados Destino", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosDestino/{idEstadoOrigen}")
	public ResponseEntity<List<EstadoDocumentoGCDto>> getEstadosDestino(@PathVariable("idEstadoOrigen") Long idEstadoOrigen) {
		
		List<EstadoDocumentoGC> estados =  flujoDocService.getEstadosDestino(idEstadoOrigen);
		
		List<EstadoDocumentoGCDto> estadosDto = estados.stream().map(entity -> modelMapper.map(entity, EstadoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(estadosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Perfiles Estados Flujo", description = "Este metodo devuelve todos los Perfiles Estados Flujo", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPerfilesEstadosFlujo")
	public ResponseEntity<List<PerfilGCDto>> getPerfilesEstadosFlujo() {
		
		List<PerfilGC> perfiles =  flujoDocService.getPerfilesEstadosFlujo();
		
		List<PerfilGCDto> perfilesDto = perfiles.stream().map(entity -> modelMapper.map(entity, PerfilGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(perfilesDto, HttpStatus.OK);		
	}
	
	/**
	 * Creación de los flujos de estado.
	 *
	 * @param FlujoDocumentoGCDto
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Creación de los Estado documento", description = "Este metodo crea los Estado documento", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createEstadoFlujoDocumento/{idTipoDocumento}")
	public ResponseEntity<Object> createEstadoFlujoDocumento(
			@Parameter(description = "Estado Flujo Documento", required = true) @RequestBody final List<FlujoDocumentoGCDto> flujoDocumentoGCListDto,
			@PathVariable("idTipoDocumento") Long idTipoDocumento) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<FlujoDocumentoGC> flujoDocumentoGCListIn = flujoDocumentoGCListDto.stream().map(entity -> modelMapper.map(entity, FlujoDocumentoGC.class)).collect(Collectors.toList());
		
			flujoDocumentoGCListIn = flujoDocService.createEstadoFlujoDocumento(flujoDocumentoGCListIn, idTipoDocumento);
		
			List<FlujoDocumentoGCDto> flujoDocumentoGCListOut = flujoDocumentoGCListIn.stream().map(entity -> modelMapper.map(entity, FlujoDocumentoGCDto.class)).collect(Collectors.toList());
		
		
			response = new ResponseEntity<>(flujoDocumentoGCListOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	
	/**
	 * Delete flujos de estado.
	 *
	 * @param FlujoDocumentoGCDto
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Delete Flujos Estado documento", description = "Este metodo delete Flujos Estado documento", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteEstadoFlujoDocumento")
	public ResponseEntity<Object> deleteEstadoFlujoDocumento(
			@Parameter(description = "Estado Flujo Documento", required = true) @RequestBody final List<FlujoDocumentoGCDto> flujoDocumentoGCListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<FlujoDocumentoGC> flujoDocumentoGCListIn = flujoDocumentoGCListDto.stream().map(entity -> modelMapper.map(entity, FlujoDocumentoGC.class)).collect(Collectors.toList());
		
			flujoDocService.deleteEstadoFlujoDocumento(flujoDocumentoGCListIn);
		
			response = new ResponseEntity<>(flujoDocumentoGCListIn, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}

}
