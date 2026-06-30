package es.jccm.edu.documentosGC.adapter.in.rest.tipdocadjuntos;

import java.util.List;
import java.util.stream.Collectors;
import es.jccm.edu.documentosGC.application.ports.in.tipdocadjunto.ITipoDocumentoAdjuntoGCService;
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

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.tipdocadjuntos.model.TipoDocumentoAdjuntoGCDto;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicio Tipo Documentos Adjunto GC", description = "Servicio con las operaciones sobre Tipo Documentos Adjuntos Gestión de Centros")
public class TipoDocumentoAdjuntoGCRestController {

	@Autowired
	private ITipoDocumentoAdjuntoGCService  tipoDocumentoAdjuntoGCService;
	
	@Autowired
	ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipos documentos adjuntos GC", description = "Este metodo los tipos de documentos adjuntos de Gestión de centros ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTiposDocumentosAdjuntosGC/{idTipoDocumento}/{cAnno}")
	public ResponseEntity<List<TipoDocumentoAdjuntoGCDto>> getTiposDocumentosAdjuntosGC(@PathVariable("idTipoDocumento") Long idTipoDocumento, @PathVariable("cAnno") Integer cAnno) {
		
		List<TipoDocumentoAdjuntoGC> tipoDocumentoAdjuntos =  tipoDocumentoAdjuntoGCService.getTipoDocumentoAdjuntosByIdTipoDocumento(idTipoDocumento, cAnno);
		
		List<TipoDocumentoAdjuntoGCDto> tipoDocumentoAdjuntosDto = tipoDocumentoAdjuntos.stream().map(entity -> modelMapper.map(entity, TipoDocumentoAdjuntoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tipoDocumentoAdjuntosDto, HttpStatus.OK);	
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Tipo documento adjunto GC", description = "Este metodo los tipos de documentos de Gestión de centros ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTipoDocumentoAdjuntosGC/{idTipoDocumentoAdjunto}")
	public ResponseEntity<TipoDocumentoAdjuntoGCDto> getTipoDocumentoAdjuntosGC(@PathVariable("idTipoDocumentoAdjunto") Long idTipoDocumentoAdjunto) {
		
		TipoDocumentoAdjuntoGC tipoDocumentoAdjunto =  tipoDocumentoAdjuntoGCService.getTipoDocumentoAdjuntosGCById(idTipoDocumentoAdjunto);
		
		TipoDocumentoAdjuntoGCDto tipoDocumentoAdjuntoDto = modelMapper.map(tipoDocumentoAdjunto, TipoDocumentoAdjuntoGCDto.class);
		
		return new ResponseEntity<>(tipoDocumentoAdjuntoDto, HttpStatus.OK);	
	}
	
	/**
	 * Crea un nuevo tipo documento adjunto.
	 *
	 * @return ResponseEntity<TipoDocumentoGCDto>
	 */
	@PreAuthorize("hasAnyRole('N')")
    @Operation(summary = "Crea un tipo documento adjunto", description = "Este metodo crea un tipo documento adjunto", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @RequestMapping(value = "/createTipoDocumentoAdjunto")
    public ResponseEntity<Object> createTipoDocumento(@Parameter(description = "Tipo Documento Adjunto", required = true) @RequestBody final TipoDocumentoAdjuntoGCDto tipoDocumentoAdjuntoDto){    	
    	
    	TipoDocumentoAdjuntoGC tipoDocumentoAdjunto = modelMapper.map(tipoDocumentoAdjuntoDto, TipoDocumentoAdjuntoGC.class);
    	ResponseEntity<Object> response = null;
		
    	try {		
    		TipoDocumentoAdjuntoGC tipoDocumentoAdjuntoNuevo = tipoDocumentoAdjuntoGCService.createTipoDocumentoAdjunto(tipoDocumentoAdjunto); 		
    		
			response = new ResponseEntity<>(modelMapper.map(tipoDocumentoAdjuntoNuevo, TipoDocumentoAdjuntoGCDto.class) , HttpStatus.OK);	
    	}catch (DataIntegrityViolationException eData) {
    		if(eData.getMessage().contains("DELPHOS.DGC_TIPDOCADJ_UK_DSNOMBRE"))
    			response = new ResponseEntity<>("No puede existir mas de un tipo documento adjunto con el mismo nombre.", HttpStatus.BAD_REQUEST);
    		else if(eData.getMessage().contains("DELPHOS.DGC_TIPDOCADJ_UK_NUORDEN"))
    			response = new ResponseEntity<>("No puede existir mas de un tipo documento adjunto con el mismo orden.", HttpStatus.BAD_REQUEST);
    		else
    			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			if(e.getMessage().contains("EXISTEPRINCIPAL"))
				response = new ResponseEntity<>("No puede existir mas de un tipo documento adjunto principal.", HttpStatus.BAD_REQUEST);
			else
				response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	        
	    return response;      
    } 
    
	 /** Borrado de un Tipo documento adjunto.
	 *
	 * @param id tipo documento adjunto
	 * @return the response entity
	 */ 	
	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Borrado de un tipo de documento adjunto", description = "Este metodo borra los datos de un tipo de documento adjunto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteTipoDocumentoAdjunto/{idTipoDocumentoAdjunto}")
	public ResponseEntity<HttpStatus> deleteTipoDocumentoGC(
			@Parameter(description = "Identificador del tipo de documento adjunto", required = true) @PathVariable("idTipoDocumentoAdjunto") Long idTipoDocumentoAdjunto) {
		try {
			tipoDocumentoAdjuntoGCService.deleteTipoDocumentoAdjunto(idTipoDocumentoAdjunto);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasAnyRole('N')")
	@Operation(summary = "Siguiente orden Tipo documento adjunto GC", description = "Este metodo devuelve el siguiente orden de Tipo documento adjunto GC", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSiguienteOrdenTipoDocumentoAdjuntosGC/{idTipoDocumento}")
	public ResponseEntity<Integer> getSiguienteOrdenTipoDocumentoAdjuntosGC(@PathVariable("idTipoDocumento") Long idTipoDocumento) {
		
		Integer sigOrden =  tipoDocumentoAdjuntoGCService.getSiguienteOrdenTipoDocumentoAdjuntosGC(idTipoDocumento);
		
		return new ResponseEntity<>(sigOrden, HttpStatus.OK);	
	}
			
}
