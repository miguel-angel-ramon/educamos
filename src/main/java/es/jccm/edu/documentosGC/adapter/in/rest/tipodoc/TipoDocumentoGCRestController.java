package es.jccm.edu.documentosGC.adapter.in.rest.tipodoc;

import java.util.List;
import java.util.stream.Collectors;
import es.jccm.edu.documentosGC.application.ports.in.tipodoc.ITipoDocumentosGCService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model.TipoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model.ListadoTipoDocumentosGCDto;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.ListadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicio Tipo Documentos GC", description = "Servicio con las operaciones sobre Tipo Documentos Gestión de Centros")
public class TipoDocumentoGCRestController {

	@Autowired
	private ITipoDocumentosGCService  tiposDocService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos GC", description = "Este metodo los tipos de documentos de Gestión de centros ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDocumentosGC")
	public ResponseEntity<List<TipoDocumentoGCDto>> tiposDocumentosGC() {
		
		List<TipoDocumentosGC> documentos =  tiposDocService.tiposDocumentosGC();
		
		List<TipoDocumentoGCDto> tiposdocumentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, TipoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tiposdocumentosDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Count tipos documentos", description = "Este metodo devuelve el numero de tipos documentos", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountTiposDocumentos")
	public ResponseEntity<Long> getCountTiposDocumentos() {
		
		Long numTipoDocumentos =  tiposDocService.getCountTiposDocumentos();		
		
		return new ResponseEntity<>(numTipoDocumentos, HttpStatus.OK);	
	}
		
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos", description = "Este metodo los tipos de documentos programáticos ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDocumentosProgramativos/{dsAbrev}")
	public ResponseEntity<List<TipoDocumentoGCDto>> tiposDocumentosProgramativos(@PathVariable("dsAbrev") String dsAbrev) {
				
		List<TipoDocumentosGC> documentos =  tiposDocService.tiposDocumentosProgramativos(dsAbrev);
		
		List<TipoDocumentoGCDto> tiposdocumentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, TipoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tiposdocumentosDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos", description = "Este metodo los tipos de documentos programáticos ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDocumentosProgramativos/{dsAbrev}/{cAnno}")
	public ResponseEntity<List<TipoDocumentoGCDto>> tiposDocumentosProgramativos(@PathVariable("dsAbrev") String dsAbrev ,
																					@PathVariable("cAnno")Long cAnno){
		List<TipoDocumentosGC> documentos = tiposDocService.tiposDocumentosProgramativos(dsAbrev, cAnno);
		
		List<TipoDocumentoGCDto> tiposDocumentosDto = documentos.stream()
				.map(entity -> modelMapper.map(entity, TipoDocumentoGCDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(tiposDocumentosDto, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos padre", description = "Este metodo devuelve los tipos de documentos programáticos padres ",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDocumentosProgramativosPadres")
	public ResponseEntity<List<TipoDocumentoGCDto>> tiposDocumentosProgramativosPadres() {
				
		List<TipoDocumentosGC> docPadres =  tiposDocService.tiposDocumentosProgramativosPadres();
		
		List<TipoDocumentoGCDto> docPadresDto = docPadres.stream()
						.map(entity -> modelMapper.map(entity, TipoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(docPadresDto, HttpStatus.OK);	
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos padre", description = "Este metodo devuelve los tipos de documentos programáticos padres ",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTipoDocumentoById/{idTipDoc}")
	public ResponseEntity<TipoDocumentoGCDto> getTipoDocumentoById(@PathVariable("idTipDoc") Long idTipDoc) {
				
		TipoDocumentosGC tipodocumento =  tiposDocService.findById(idTipDoc);
		
		TipoDocumentoGCDto tipodocumentoDto = modelMapper.map(tipodocumento, TipoDocumentoGCDto.class);
		
		return new ResponseEntity<>(tipodocumentoDto, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos", description = "Este metodo los tipos de documentos programáticos sin documento padre ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDocumentosSinPadre/{cAnno}/{dsAbrev}")
	public ResponseEntity<List<ListadoTipoDocumentosGCDto>> tiposDocumentosSinPadre(@PathVariable("cAnno") Long cAnno,
																					@PathVariable("dsAbrev") String dsAbrev) {
				
		List<ListadoTipoDocumentoGC> documentos =  tiposDocService.tiposDocumentosSinPadre(cAnno,dsAbrev);
		
		List<ListadoTipoDocumentosGCDto> tiposdocumentosDto = documentos.stream()
						.map(entity -> modelMapper.map(entity, ListadoTipoDocumentosGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tiposdocumentosDto, HttpStatus.OK);			
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos Programáticos", description = "Este metodo los tipos de documentos programáticos sin documento padre ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTiposDocumentosProgramaticosPadre")
	public ResponseEntity<List<TipoDocumentoGCDto>> getTiposDocumentosProgramaticosPadre() {
				
		List<TipoDocumentosGC> documentos =  tiposDocService.tiposDocumentosProgramaticosPadre();
		
		List<TipoDocumentoGCDto> tiposdocumentosDto = documentos.stream().map(entity -> modelMapper.map(entity, TipoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tiposdocumentosDto, HttpStatus.OK);	
	}
	
	/**
	 * Crea un nuevo tipo documento.
	 *
	 * @return ResponseEntity<TipoDocumentoGCDto>
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
    @Operation(summary = "Crea un tipo documento", description = "Este metodo crea un tipo documento", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @RequestMapping(value = "/createTipoDocumento")
    public ResponseEntity<Object> createTipoDocumento(@Parameter(description = "Tipo Documento", required = true) @RequestBody final TipoDocumentoGCDto tipoDocumentoDto){    	
    	
    	TipoDocumentosGC tipoDocumento = modelMapper.map(tipoDocumentoDto, TipoDocumentosGC.class);
    	ResponseEntity<Object> response = null;
		
    	try {		
			
    		TipoDocumentosGC tipoDocumentoNuevo = tiposDocService.createTipoDocumento(tipoDocumento); 
    		 
    		TipoDocumentoGCDto tipoDocumentoNuevoDto = modelMapper.map(tipoDocumentoNuevo, TipoDocumentoGCDto.class);    		
    		
			response = new ResponseEntity<>(tipoDocumentoNuevoDto, HttpStatus.OK);	
    	}catch (DataIntegrityViolationException eData) {
    		if(eData.getMessage().contains("DELPHOS.DGC_TIPDOC_UK_DSABREV"))
    			response = new ResponseEntity<>("Se ha encontrado dos Tipo documento con la misma abreviatura.", HttpStatus.BAD_REQUEST);
    		else
    			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	        
	    return response;      
    } 

	 /** Borrado de un Tipo documento.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Borrado de un tipo de documento", description = "Este metodo borra los datos de un tipo de documento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteTipoDocumentoGC/{idTipoDocumento}")
	public ResponseEntity<HttpStatus> deleteTipoDocumentoGC(
			@Parameter(description = "Identificador del tipo de documento", required = true) @PathVariable("idTipoDocumento") Long idTipoDocumento) {
		try {
			tiposDocService.deleteTipoDocumentoGC(idTipoDocumento);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipo documento", description = "Este metodo un tipo de documento a partir de su abreviatura ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tipoDocumentoByAbrev/{dsAbrev}")
	public ResponseEntity<TipoDocumentoGCDto> tipoDocumentoByAbrev(@PathVariable("dsAbrev") String dsAbrev) {
				
		TipoDocumentosGC tipo =  tiposDocService.tipoDocumentoByAbrev(dsAbrev);
		
		TipoDocumentoGCDto tipoDto = modelMapper.map(tipo, TipoDocumentoGCDto.class);
		
		return new ResponseEntity<>(tipoDto, HttpStatus.OK);	
	}
	
}
