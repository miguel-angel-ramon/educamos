package es.jccm.edu.documentosGC.adapter.in.rest.estadodoc;

import java.util.List;
import java.util.stream.Collectors;
import es.jccm.edu.documentosGC.application.ports.in.estadodoc.IEstadosDocumentosGCService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.EstadoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.EstadoFlujoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.LineaEstadoProjectionDto;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.ListadoEstadoDocumentoGCDto;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoFlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.ListadoEstadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.LineaEstadoProjection;
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
@Tag(name = "Servicio Estados Documentos GC", description = "Servicio con las operaciones sobre los Estados Documentos Gestión de Centros")
public class EstadoDocumentoGCRestController {
	
	@Autowired
	private IEstadosDocumentosGCService estadosDocService;
	
	@Autowired
	ModelMapper modelMapper;


	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estados documentos GC", description = "Este metodo devuelve los estados de documentos de Gestión de centros ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/estadosDocumentosGC/{idPerfil}/{cAnno}/{dsAbrevPadre}")
	public ResponseEntity<List<EstadoDocumentoGCDto>> estadosDocumentosGC(@PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("cAnno") Long cAnno,
																		  @PathVariable("dsAbrevPadre") String dsAbrevPadre) {
		
		
		List<EstadoDocumentoGC> estados =  estadosDocService.estadosDocumentosGC(idPerfil, cAnno, dsAbrevPadre);
		
		List<EstadoDocumentoGCDto> estadosdocumentosDto = estados.stream()
						.map(entity -> modelMapper.map(entity, EstadoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<EstadoDocumentoGCDto>>(estadosdocumentosDto, HttpStatus.OK);	
		
		
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Id estado según abreviatura", description = "Este metodo devuelve el id del estado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadoId/{abrevEstado}")
	public ResponseEntity<Long> countDocumentosGCAbrev( @PathVariable("abrevEstado") String abrevEstado) {
		return new ResponseEntity<Long>(estadosDocService.getEstadoId(abrevEstado), HttpStatus.OK);
	}	
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estado inicial documentos GC", description = "Este metodo devuelve el estado inicial para los documentos de Gestión de centros ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/estadoInicialDocumentosGC/{idPerfil}/{idTipodoc}/{cAnno}")
	public ResponseEntity<List<EstadoFlujoDocumentoGCDto>> estadoInicialDocumentosGC(@PathVariable("idPerfil") Long idPerfil,
																				@PathVariable("idTipodoc") Long idTipodoc,
																				@PathVariable("cAnno") Long cAnno) {
		
		List<EstadoFlujoDocumentoGC> estados =  estadosDocService.estadoInicialDocumentosGC(idPerfil,idTipodoc,cAnno);
		
		List<EstadoFlujoDocumentoGCDto> estadosdocumentosDto = estados.stream()
						.map(entity -> modelMapper.map(entity, EstadoFlujoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<EstadoFlujoDocumentoGCDto>>(estadosdocumentosDto, HttpStatus.OK);	
		
		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estado actual", description = "Este metodo devuelve el estado actual del documento ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/estadoActualDocumentosGC/{idDocumento}")
	public ResponseEntity<EstadoDocumentoGCDto> estadoActualDocumentosGC(@PathVariable("idDocumento") Long idDocumento) {
		
		EstadoDocumentoGC estado =  estadosDocService.estadoActualDocumentosGC(idDocumento);
		
		EstadoDocumentoGCDto estadoDto = modelMapper.map(estado, EstadoDocumentoGCDto.class);  ;		
		
		return new ResponseEntity<EstadoDocumentoGCDto>(estadoDto, HttpStatus.OK);		
		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Estado detalle", description = "Este metodo devuelve los posibles estados en el detalle de un documento ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/estadosPosiblesDocumentosGC/{idPerfil}/{idTipodoc}/{idDocumento}")
	public ResponseEntity<List<EstadoFlujoDocumentoGCDto>> estadosPosiblesDocumentosGC(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idTipodoc") Long idTipodoc,
																			@PathVariable("idDocumento") Long idDocumento) {
		
		List<EstadoFlujoDocumentoGC> estados =  estadosDocService.estadosPosiblesDocumentosGC(idPerfil,idTipodoc,idDocumento);
		
		List<EstadoFlujoDocumentoGCDto> estadosDto = estados.stream()
				.map(entity -> modelMapper.map(entity, EstadoFlujoDocumentoGCDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EstadoFlujoDocumentoGCDto>>(estadosDto, HttpStatus.OK);		
		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Linea estados documentos GC", description = "Este metodo devuelve una lista con la línea de estados mas directa ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/lineaEstadosDocumentosGC/{idDocumento}/{idTipo}")
	public ResponseEntity<List<LineaEstadoProjectionDto>> lineaEstadosDocumentosGC(@PathVariable("idDocumento") Long idDocumento,
																				   @PathVariable("idTipo") Long idTipo) {
		
		List<LineaEstadoProjection> lineas =  estadosDocService.lineaEstadosDocumentosGC(idDocumento,idTipo);
		
		List<LineaEstadoProjectionDto> lineasDto = lineas.stream()
						.map(entity -> modelMapper.map(entity, LineaEstadoProjectionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<LineaEstadoProjectionDto>>(lineasDto, HttpStatus.OK);	
		
		
	}

	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Estados documentos Programáticos", description = "Este metodo los posibles estados de los documentos programáticos ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/listadoEstadosDocumentosGC/{cAnno}")
	public ResponseEntity<List<ListadoEstadoDocumentoGCDto>> listadoEstadosDocumentosGC(@PathVariable("cAnno") Long cAnno) {
				
		List<ListadoEstadoTipoDocumentoGC> documentos =  estadosDocService.listadoEstadosDocumentosGC(cAnno);
		
		List<ListadoEstadoDocumentoGCDto> tiposdocumentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, ListadoEstadoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<ListadoEstadoDocumentoGCDto>>(tiposdocumentosDto, HttpStatus.OK);	
		
		
	}
	
	/**
	 * Borrado de un estado documento.
	 *
	 * @param idEstado Id del estado
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Borrado de un estado de documento", description = "Este metodo borra los datos de un estado de documento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteEstadoDocumentoGC/{idEstado}")
	public ResponseEntity<HttpStatus> deleteEstadoDocumentoGC(
			@Parameter(description = "Identificador del estado de documento", required = true) @PathVariable("idEstado") Long idEstado) {
		try {
			estadosDocService.deleteEstadoDocumentoGC(idEstado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// Create
	
		/**
		 * Creación de los Datos de un Estado documento.
		 *
		 * @param estadoDocumentoGCDto Datos Estado documento
		 * @return the response entity
		 */
	    @PreAuthorize("hasAnyRole('N')")
		@Operation(summary = "Creación de los Estado documento", description = "Este metodo crea los Estado documento", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
		@PostMapping("/createEstadoDocumento")
		public ResponseEntity<Object> createEstadoDocumento(
				@Parameter(description = "Estado Documento", required = true) @RequestBody final EstadoDocumentoGCDto estadoDocumentoGCDto) {
			
			ResponseEntity<Object> response = null;
			try {
			
				modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
				EstadoDocumentoGC estadoIn = modelMapper.map(estadoDocumentoGCDto, EstadoDocumentoGC.class);
				estadoIn = estadosDocService.createEstadoDocumento(estadoIn);
			
				EstadoDocumentoGCDto estadoOut = modelMapper.map(estadoIn, EstadoDocumentoGCDto.class);
			
			
				response = new ResponseEntity<Object>(estadoOut, HttpStatus.OK);
				
			}catch (DataIntegrityViolationException e) {
				if(e.getMessage().contentEquals("DGC_EST_UK_DSABREV"));{
					response = new ResponseEntity<Object>("Ya existe un estado de documento con esta abreviatura.", HttpStatus.BAD_REQUEST);
				}
			
			}catch (Exception e) {
				response = new ResponseEntity<Object>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
			}
		
			return response;
		}

	    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
		@Operation(summary = "Estado actual", description = "Este metodo devuelve el estado actual del documento ", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/estadoDocumentosGCById/{id}")
		public ResponseEntity<EstadoDocumentoGCDto> findByIdEstadoDocumento(@PathVariable("id") Long id) {
			
			EstadoDocumentoGC estado =  estadosDocService.findById(id);
			
			EstadoDocumentoGCDto estadoDto = modelMapper.map(estado, EstadoDocumentoGCDto.class);  ;		
			
			return new ResponseEntity<EstadoDocumentoGCDto>(estadoDto, HttpStatus.OK);		
			
		}
	
	
	
	

}
