package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.AdjuntosListDetalleDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ContadoresInicioDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ConvocatoriaReunionListDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.CursoProjectionDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DetalleAdjuntosDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DocumentoGCNuevoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DocumentosGCListDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.HistDocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.InformacionEstadoPojectionDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ListNombreAdjuntosDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.NuevoParteDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ParteGeneradoDocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.PartesMensualesDocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.PlazosEntregaDocumentosGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.TipoAdjuntosListDto;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.AdjuntosListDetalle;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ContadoresInicio;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaReunionList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.CursoProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DetalleAdjuntos;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DetalleAdjuntosRequest;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentoGCNuevo;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGCList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.HistDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.NuevoParte;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ParteGeneradoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PartesMensualesDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PlazosEntregaDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.TipoAdjuntosList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.InformacionEstadoPojection;
import es.jccm.edu.documentosGC.application.ports.in.documentosGC.IDocumentosGCService;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalExceptionDgc;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
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
@Tag(name = "Servicio Documentos GC", description = "Servicio con las operaciones sobre Documentos Gestión de Centros")
public class DocumentosGCRestController {
	
	@Autowired
	private IDocumentosGCService documentosGCProgramaticosService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "contadores inicio", description = "Este metodo devuelve el total de contadores para el menú de inicio", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getContadoresInicio/{idCentro}/{anno}/{idPerfil}/{xEmpleado}/{fTomapos}")
	public ResponseEntity<List<ContadoresInicioDto>> getContadoresInicio(@PathVariable("idCentro") Long idCentro,
																	     @PathVariable("anno") Integer anno,
																	     @PathVariable("idPerfil") Long idPerfil,
																	     @PathVariable("xEmpleado") Long xEmpleado,
																	     @PathVariable("fTomapos") String fTomapos) {
		
		List<ContadoresInicio> contadores =  documentosGCProgramaticosService.getContadoresInicio(idCentro,anno,idPerfil,xEmpleado,fTomapos);
		
		List<ContadoresInicioDto> contadoresDto = contadores.stream()
						.map(entity -> modelMapper.map(entity, ContadoresInicioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ContadoresInicioDto>>(contadoresDto, HttpStatus.OK);	
				
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado de documentos centro", description = "Este metodo devuelve un listado con los docuemntos de gestion de centros", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllDocumentosGC/{idCentro}/{anno}/{idTipo}/{idEstado}/{idProvincia}/{idMunicipio}/{abrev}/{idPerfil}/{xEmpleado}")
	public ResponseEntity<List<DocumentosGCListDto>> getAllDocumentosGC(@PathVariable("idCentro") Long idCentro, 
																		@PathVariable("anno") Integer anno,
																		@PathVariable("idTipo") Long idTipo,
																		@PathVariable("idEstado") Long idEstado,
																		@PathVariable("idProvincia") Long idProvincia,
																		@PathVariable("idMunicipio") Long idMunicipio,
																		@PathVariable("abrev") String abrev,
																		@PathVariable("idPerfil") Long idPerfil,
																		@PathVariable("xEmpleado") Long xEmpleado) {
		
		List<DocumentosGCList> documentos =  documentosGCProgramaticosService.getAllDocumentosGC(idCentro,
																								 anno,
																								 idTipo,
																								 idEstado,
																								 idProvincia,
																								 idMunicipio,
																								 abrev,
																								 idPerfil,
																								 xEmpleado);
		
		List<DocumentosGCListDto> documentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentosGCListDto>>(documentosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado de documentos centro zona", description = "Este metodo devuelve un listado con los docuemntos de gestion de centros inspector zona", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllDocumentosGCZona/{idCentro}/{anno}/{idTipo}/{idEstado}/{idProvincia}/{idMunicipio}/{idPerfil}/{idUsuario}/{abrev}/{xEmpleado}")
	public ResponseEntity<List<DocumentosGCListDto>> getAllDocumentosGCZona(@PathVariable("idCentro") Long idCentro, 
																		    @PathVariable("anno") Integer anno,
																		    @PathVariable("idTipo") Long idTipo,
																		    @PathVariable("idEstado") Long idEstado,
																		    @PathVariable("idProvincia") Long idProvincia,
																		    @PathVariable("idMunicipio") Long idMunicipio,
																		    @PathVariable("idPerfil") Long idPerfil,
																		    @RequestHeader(Constants.AUTHORIZATION) String jwt,
																		    @PathVariable("abrev") String abrev,
																		    @PathVariable("xEmpleado") Long xEmpleado) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		List<DocumentosGCList> documentos =  documentosGCProgramaticosService.getAllDocumentosGCZona(idCentro,
																									 anno,
																									 idTipo,
																									 idEstado,
																									 idProvincia,
																									 idMunicipio,
																									 idPerfil,
																									 datosUsuario.getXUsuarioDelphos(),
																									 abrev,
																									 xEmpleado);
		
		List<DocumentosGCListDto> documentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentosGCListDto>>(documentosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado de documentos centro", description = "Este metodo devuelve un listado con los docuemntos de acta de reuniones", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllDocumentosAR/{idCentro}/{anno}/{idTipo}/{idEstado}/{xEmpleado}")
	public ResponseEntity<List<DocumentosGCListDto>> getAllDocumentosAR(@PathVariable("idCentro") Long idCentro, 
																		@PathVariable("anno") Integer anno,
																		@PathVariable("idTipo") Long idTipo,
																		@PathVariable("idEstado") Long idEstado,
																		@PathVariable("xEmpleado") Long xEmpleado) {
		
		List<DocumentosGCList> documentos =  documentosGCProgramaticosService.getAllDocumentosAR(idCentro,
																								 anno,
																								 idTipo,
																								 idEstado,
																								 xEmpleado);
		
		List<DocumentosGCListDto> documentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentosGCListDto>>(documentosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado de documentos inspector centro", description = "Este metodo devuelve un listado con los docuemntos de gestion de centros inspector centro", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })

	@GetMapping("/getAllDocumentosGCInspectorCentro/{idCentro}/{anno}/{idTipo}/{idEstado}/{idProvincia}/{idMunicipio}/{xEmpleado}/{fTomapos}/{abrev}")
	public ResponseEntity<List<DocumentosGCListDto>> getAllDocumentosGCInspectorCentro(@PathVariable("idCentro") Long idCentro, 
																					   @PathVariable("anno") Integer anno,
																		               @PathVariable("idTipo") Long idTipo,
																		               @PathVariable("idEstado") Long idEstado,
																		               @PathVariable("idProvincia") Long idProvincia,
																					   @PathVariable("idMunicipio") Long idMunicipio,
																					   @RequestHeader(Constants.AUTHORIZATION) String jwt,
																		               @PathVariable("fTomapos") String fTomapos,
																		               @PathVariable("abrev") String abrev) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<DocumentosGCList> documentos =  documentosGCProgramaticosService.getAllDocumentosGCInspectorCentro(idCentro,anno,idTipo,idEstado,idProvincia, idMunicipio, datosUsuario.getIdEmpleadoDelphos(),fTomapos,abrev);
		
		List<DocumentosGCListDto> documentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentosGCListDto>>(documentosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado de documentos centro provincial", description = "Este metodo devuelve un listado con los docuemntos de gestion de centros inspector provincial", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllDocumentosGCProvincial/{idCentro}/{anno}/{idTipo}/{idEstado}/{idProvincia}/{idMunicipio}/{idPerfil}/{idUsuario}/{abrev}/{idCentroProvincia}/{xEmpleado}")
	public ResponseEntity<List<DocumentosGCListDto>> getAllDocumentosGCProvincial(@PathVariable("idCentro") Long idCentro, 
																		          @PathVariable("anno") Integer anno,
																				  @PathVariable("idTipo") Long idTipo,
																				  @PathVariable("idEstado") Long idEstado,
																				  @PathVariable("idProvincia") Long idProvincia,
																				  @PathVariable("idMunicipio") Long idMunicipio,
																				  @PathVariable("idPerfil") Long idPerfil,
																				  @RequestHeader(Constants.AUTHORIZATION) String jwt,
																				  @PathVariable("abrev") String abrev,
																				  @PathVariable("idCentroProvincia") Long idCentroProvincia,
																				  @PathVariable("xEmpleado") Long xEmpleado) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<DocumentosGCList> documentos =  documentosGCProgramaticosService.getAllDocumentosGCProvincial(idCentro,
																									       anno,
																									       idTipo,
																									       idEstado,
																									       idProvincia,
																									       idMunicipio,
																									       idPerfil,
																									       datosUsuario.getXUsuarioDelphos(),
																									       abrev,
																									       idCentroProvincia,
																									       xEmpleado);
		
		List<DocumentosGCListDto> documentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentosGCListDto>>(documentosDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Historico de un documento",description = "este listado devuelve el historico de los estados de un documento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getHistDocumentosGC/{id_documento}")
	public ResponseEntity<List<HistDocumentosGCDto>> getHistDocumentosGC(@PathVariable("id_documento") Long idDocumento){
		
		List<HistDocumentosGC> documento = documentosGCProgramaticosService.getHistDocumentosGC(idDocumento);
		
		List<HistDocumentosGCDto> documentoHist = documento.stream()
				.map(entity -> modelMapper.map(entity,HistDocumentosGCDto.class)).collect(Collectors.toList());
		return new ResponseEntity<List<HistDocumentosGCDto>>(documentoHist, HttpStatus.OK);	
	}

	
	/**
	 * Crea un nuevo documento de centro.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<RodalDocDto>
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
    @Operation(summary = "Crea documento de centro", description = "Este metodo crea un documento de centro", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @RequestMapping(value = "/createDocumentoGC", method = RequestMethod.POST,  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> createDocumentoGC(@RequestPart DocumentoGCNuevoDto documentoGCNuevoDto,
    											    @RequestPart(required = false) List<MultipartFile> files
    											    ) throws  InsertarDocFault, IOException {    	
    	
    	DocumentoGCNuevo documento = modelMapper.map(documentoGCNuevoDto, DocumentoGCNuevo.class);
    	ResponseEntity<Object> response = null;
    	List<Long> listAdjuntos = new ArrayList<Long>();
		
    	try {		
			
    		DocumentosGC documentoNuevo = documentosGCProgramaticosService.createDocumentoGC(documento, files, listAdjuntos);  
    		 
    		DocumentosGCDto documentoNuevoDto = modelMapper.map(documentoNuevo, DocumentosGCDto.class);
    		documentoNuevoDto.setIdAdjuntosFirmantes(listAdjuntos);
    		
			response = new ResponseEntity<Object>(documentoNuevoDto, HttpStatus.OK);			
				
			} catch (RodalExceptionDgc e) {
				response = new ResponseEntity<Object>("Error al guardar el documento adjunto.", HttpStatus.BAD_REQUEST);
			}
	         catch (Exception e) {
	        	response = new ResponseEntity<Object>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
			} 
	        
	        return response;  
	        
    }

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Documento por Id", description = "Este metodo devuelve la informacion de un documento por si Id", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDocumentoId/{idDocumento}")
	public ResponseEntity<DocumentosGCDto> getDocumentoId(@PathVariable("idDocumento") Long idDocumento) {
		
		DocumentosGC documento =  documentosGCProgramaticosService.getDocumentoId(idDocumento);
		
		DocumentosGCDto documentoDto = modelMapper.map(documento, DocumentosGCDto.class);    
		
		return new ResponseEntity<DocumentosGCDto>(documentoDto, HttpStatus.OK);		
	}

	@Operation(summary = "Datos estado documento", description = "Este metodo devuelve la informacion completa de un estado concreto del documento", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getInformacionEstadoDocumento/{idDocumento}/{idHistorial}")
	public ResponseEntity<List<InformacionEstadoPojectionDto>> getInformacionEstadoDocumento(@PathVariable("idDocumento") Long idDocumento,
														  			     @PathVariable("idHistorial") Long idHistorial) {
		
		List<InformacionEstadoPojection> documento =  documentosGCProgramaticosService.getInformacionEstadoDocumento(idDocumento,idHistorial);
		
		List<InformacionEstadoPojectionDto> documentoDto = documento.stream().map
					(entity -> modelMapper.map(entity, InformacionEstadoPojectionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<InformacionEstadoPojectionDto>>(documentoDto, HttpStatus.OK);	
		
		/*InformacionEstadoPojectionDto documentoDto = null;
		
		if (documento != null) documentoDto = modelMapper.map(documento, InformacionEstadoPojectionDto.class); 		
		
		return new ResponseEntity<InformacionEstadoPojectionDto>(documentoDto, HttpStatus.OK); */		
	}
	
	
	// Delete
	
		/**
		 * Borrado de un documento.
		 *
		 * @param idDocumento Id del documento
		 * @return the response entity
		 */
	    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Borrado de un Documento", description = "Este metodo borra los datos de un Documento",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@DeleteMapping("/deleteDocumento/{idDocumento}")
		public ResponseEntity<HttpStatus> deleteConvenio(
				@Parameter(description = "Identificador del Documento", required = true) @PathVariable("idDocumento") Long idDocumento) {
			try {
				documentosGCProgramaticosService.deleteDocumento(idDocumento);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Operation(summary = "Datos estado documento", description = "Este metodo devuelve la informacion completa de un estado concreto del documento", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getConvocatorias/{idCentro}/{anno}/{idTipo}")
		public ResponseEntity<List<ConvocatoriaReunionListDto>> getConvocatorias(@PathVariable("idCentro") Long idCentro, @PathVariable("anno") Long anno,
																		 @PathVariable("idTipo") Long idTipo) {
			
			List<ConvocatoriaReunionList> convocatoriaReuniones =  documentosGCProgramaticosService.getConvocatorias(idCentro,anno,idTipo);
			
			List<ConvocatoriaReunionListDto> convocatoriaReunionesDto = convocatoriaReuniones.stream().map(entity -> modelMapper.map(entity, ConvocatoriaReunionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ConvocatoriaReunionListDto>>(convocatoriaReunionesDto, HttpStatus.OK);		
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Documentos Plazo entrega", description = "Este metodo devuelve el numero de documentos en plazo de entrega", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getNumeroDocumentosSinCrear/{idCentro}/{anno}/{pdsAbrev}")
		public ResponseEntity<Integer> getNumeroDocumentosSinCrear(@PathVariable("idCentro") Long idCentro, 
												                   @PathVariable("anno") Long anno,
												                   @PathVariable("pdsAbrev") String pdsAbrev) {
			
			Integer nEntrega =  documentosGCProgramaticosService.getNumeroDocumentosSinCrear(idCentro,anno,pdsAbrev);			
			
			if (nEntrega >= 0) {
				return new ResponseEntity<Integer>(nEntrega, HttpStatus.OK);
			} else {
				return new ResponseEntity<Integer>(nEntrega, HttpStatus.CONFLICT);				
			}					
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Documentos Plazo entrega", description = "Este metodo devuelve el numero de documentos en plazo de entrega", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getDocumentosNoCreados/{idCentro}/{anno}/{pdsAbrev}")
		public ResponseEntity<List<PlazosEntregaDocumentosGCDto>> getDocumentosNoCreados(@PathVariable("idCentro") Long idCentro, 
												                                         @PathVariable("anno") Long anno,
												                                         @PathVariable("pdsAbrev") String pdsAbrev) {
			
			List<PlazosEntregaDocumentosGC> plazos =  documentosGCProgramaticosService.getDocumentosNoCreados(idCentro,anno,pdsAbrev);			
			
			List<PlazosEntregaDocumentosGCDto> plazosDto = plazos.stream().map(entity -> modelMapper.map(entity, PlazosEntregaDocumentosGCDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<PlazosEntregaDocumentosGCDto>>(plazosDto, HttpStatus.OK);	
			
			
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Mensajes Documentos Plazo entrega finalizado", description = "Este metodo devuelve los mensajes de documentos en plazo de entrega finalizado", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getMensajesPlazosEntregaFinalizado/{idCentro}/{anno}/{pdsAbrev}")
		public ResponseEntity<List<PlazosEntregaDocumentosGCDto>> getMensajesPlazosEntregaFinalizado(@PathVariable("idCentro") Long idCentro, 
												             							             @PathVariable("anno") Long anno,
												             							             @PathVariable("pdsAbrev") String pdsAbrev) {
			
			List<PlazosEntregaDocumentosGC> plazos =  documentosGCProgramaticosService.getMensajesPlazosEntregaFinalizado(idCentro,anno,pdsAbrev);			
			
			List<PlazosEntregaDocumentosGCDto> plazosDto = plazos.stream().map(entity -> modelMapper.map(entity, PlazosEntregaDocumentosGCDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<PlazosEntregaDocumentosGCDto>>(plazosDto, HttpStatus.OK);	
			
			
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Lista Partes mensuales", description = "Este metodo devuelve la lista de partes mensuales", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getPartesMensuales/{idCentro}/{anno}")
		public ResponseEntity<List<PartesMensualesDocumentosGCDto>> getPartesMensuales(@PathVariable("idCentro") Long idCentro, 
												             					       @PathVariable("anno") Long anno) {
			
			List<PartesMensualesDocumentosGC> partes =  documentosGCProgramaticosService.getPartesMensuales(idCentro,anno);			
			
			List<PartesMensualesDocumentosGCDto> partesDto = partes.stream().map(entity -> modelMapper.map(entity, PartesMensualesDocumentosGCDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<PartesMensualesDocumentosGCDto>>(partesDto, HttpStatus.OK);	
			
			
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Lista Partes generados", description = "Este metodo devuelve la lista de partes generados",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getParteGenerados/{cAnno}/{xCentro}")
		public ResponseEntity<List<ParteGeneradoDocumentosGCDto>> getParteGenerados(@PathVariable("cAnno") Long cAnno, 
												             					       @PathVariable("xCentro") Long xCentro) {
			
			List<ParteGeneradoDocumentosGC> partes =  documentosGCProgramaticosService.getParteGenerados(cAnno,xCentro);			
			
			List<ParteGeneradoDocumentosGCDto> partesDto = partes.stream().map(entity -> modelMapper.map(entity, ParteGeneradoDocumentosGCDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ParteGeneradoDocumentosGCDto>>(partesDto, HttpStatus.OK);	
			
			
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Lista meses del curso", description = "Este metodo devuelve la lista meses del curso", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getMesCurso/{cAnno}")
		public ResponseEntity<List<CursoProjectionDto>> getMesCurso(@PathVariable("cAnno") Long cAnno) {
			
			List<CursoProjection> partes =  documentosGCProgramaticosService.getMesCurso(cAnno);			
			
			List<CursoProjectionDto> partesDto = partes.stream().map(entity -> modelMapper.map(entity, CursoProjectionDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<CursoProjectionDto>>(partesDto, HttpStatus.OK);
			
			
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "numero de generacion de partes", description = "Este metodo devuelve el numero de documentos en plazo de entrega", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getNumeroPartesCreados/{idCentro}/{anno}/{mes}")
		public ResponseEntity<Integer> getNumeroPartesCreados(@PathVariable("idCentro") Long idCentro, 
												                   @PathVariable("anno") Long anno,
												                   @PathVariable("mes") Long mes) {
			
			Integer nEntrega =  documentosGCProgramaticosService.getNumeroPartesCreados(idCentro,anno,mes);
			return new ResponseEntity<Integer>(nEntrega, HttpStatus.OK);
					
		}
		
		
		/**
		 * Creación de los Datos de proyectos.
		 *
		 * @param 
		 * @return the response entity
		 */
		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "Creación de un parte Mensual", description = "Este metodo crea partes mensuales", responses = {
				@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@PostMapping("/createParte")
		public ResponseEntity<Integer> createParte(
				@Parameter(description = "Datos del parte", required = true) @RequestBody final NuevoParteDto parteDto) {
			try {
				NuevoParte parte = modelMapper.map(parteDto, NuevoParte.class);
				
				Integer retorno = documentosGCProgramaticosService.createParte(parte);

				
				return new ResponseEntity<Integer>(retorno, HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "lita de adjuntos", description = "Este metodo devuelve la lista de adjuntos a cargar", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getInformacionTiposAdjuntosDoc/{anno}/{idTipDoc}/{idPerfil}/{idFlujo}")
		public ResponseEntity<List<TipoAdjuntosListDto>> getInformacionTiposAdjuntosDoc(@PathVariable("anno") Long anno, 
												                      		  		    @PathVariable("idTipDoc") Long idTipDoc,
												                      		            @PathVariable("idPerfil") Long idPerfil,
												                      		            @PathVariable("idFlujo") Long idFlujo) {
			
			List<TipoAdjuntosList> tipos =  documentosGCProgramaticosService.getInformacionTiposAdjuntosDoc(anno,idTipDoc,idPerfil,idFlujo);			
			
			List<TipoAdjuntosListDto> tiposDto = tipos.stream().map(entity -> modelMapper.map(entity, TipoAdjuntosListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<TipoAdjuntosListDto>>(tiposDto, HttpStatus.OK);			
					
		}

		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "lita de adjuntos", description = "Este metodo devuelve la lista de adjuntos a cargar", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getInformacionAdjuntosDetalle/{idDocumento}/{idPerfil}")
		public ResponseEntity<List<AdjuntosListDetalleDto>> getInformacionAdjuntosDetalle(@PathVariable("idDocumento") Long idDocumento, 
											                      		                  @PathVariable("idPerfil") Long idPerfil) {
			
			List<AdjuntosListDetalle> adjuntos =  documentosGCProgramaticosService.getInformacionAdjuntosDetalle(idDocumento,idPerfil);			
					
			List<AdjuntosListDetalleDto> adjuntosDto = adjuntos.stream().map(entity -> modelMapper.map(entity, AdjuntosListDetalleDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<AdjuntosListDetalleDto>>(adjuntosDto, HttpStatus.OK);			
					
		}	
		
		/**
		 * Crea un nuevo documento de centro.
		 *
		 * @param MultipartFile
		 * @return ResponseEntity<RodalDocDto>
		 */
		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	    @Operation(summary = "Actualiza adjuntos documento", description = "Este metodo actualiza los adjuntos a un histórico de documentos", 
	    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	    @RequestMapping(value = "/updateAdjuntosDocumentoGC", method = RequestMethod.POST,  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Object> updateAdjuntosDocumentoGC(@RequestPart DetalleAdjuntosDto detalleAdjuntosDto,
	    														 @RequestPart(required = false) List<MultipartFile> files
	    											    		) throws RodalExceptionDgc, InsertarDocFault, IOException {    	
	    	
	    	List<DetalleAdjuntosRequest> detalleAdjuntos = detalleAdjuntosDto.getAdjuntos().stream().map(entity -> modelMapper.map(entity, DetalleAdjuntosRequest.class)).collect(Collectors.toList());
	    	    	
	        DetalleAdjuntos adjuntos = modelMapper.map(detalleAdjuntosDto, DetalleAdjuntos.class);
	        adjuntos.setAdjuntos(detalleAdjuntos);
	        
	    	ResponseEntity<Object> response = null;
			
	    	try {		
				
	    		Integer result = documentosGCProgramaticosService.updateAdjuntosDocumentoGC(adjuntos, files, detalleAdjuntosDto.getIdEmpleado());  
	    		 
				response = new ResponseEntity<Object>(result, HttpStatus.OK);			
					
				} catch (RodalExceptionDgc e) {
					response = new ResponseEntity<Object>("Error al guardar el documento adjunto.", HttpStatus.BAD_REQUEST);
				}
	    	    catch (Exception e) {
		        	response = new ResponseEntity<Object>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
				} 
		        
		        return response;  
		        
	    }
		
		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "adjunto firmable", description = "Este metodo devuelve si el adjunto es firmable o no", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getAdjuntoFirmable/{idAdjunto}/{idEmpleado}")
		public ResponseEntity<String> getAdjuntoFirmable(@PathVariable("idAdjunto") Long idAdjunto,
														 @PathVariable("idEmpleado") Long idEmpleado) {
			
			return new ResponseEntity<String>(documentosGCProgramaticosService.getAdjuntoFirmable(idAdjunto,idEmpleado), HttpStatus.OK);
					
		}
		
		@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
		@Operation(summary = "nombre documento ", description = "Este metodo una lista de valores para a partir de una lista de documentos recibida ", 
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@PostMapping("/getNombreDocumentoEnUso") 
		public ResponseEntity<List<Integer>> getNombreDocumentoEnUso(
				      @Parameter(description = "Datos lista nombre", required = true) @RequestBody final ListNombreAdjuntosDto lista) 
		{
			
			return new ResponseEntity<List<Integer>>(documentosGCProgramaticosService.getNombreDocumentoEnUso(lista), HttpStatus.OK);
					
		}


}
		

