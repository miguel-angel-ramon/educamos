package es.jccm.edu.documentosGC.adapter.in.rest.plazoentrega;

import es.jccm.edu.documentosGC.application.ports.in.plazoentrega.IPlazosEntregaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.plazoentrega.model.PlazosEntregaDto;
import es.jccm.edu.documentosGC.application.domain.plazoentrega.entities.PlazosEntrega;
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
@Tag(name = "Servicio Tipo Documentos GC", description = "Servicio con las operaciones sobre Tipo Documentos Gestión de Centros")
public class PlazosEntregaRestController {

	@Autowired
	private IPlazosEntregaService  plazosEntregaService;
	
	@Autowired
	ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Get plazo entrega", description = "Este metodo devuelve un plazo entrega por su id", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPlazoEntregaById/{idPlazo}")
	public ResponseEntity<PlazosEntregaDto> getPlazoEntregaById(@PathVariable("idPlazo") Long idPlazo) {
				
		PlazosEntrega plazosEntrega =  plazosEntregaService.findById(idPlazo);
		
		PlazosEntregaDto plazosEntregaDto = modelMapper.map(plazosEntrega, PlazosEntregaDto.class);
		
		return new ResponseEntity<>(plazosEntregaDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Get plazo entrega", description = "Este metodo devuelve un plazo entrega por su id", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPlazoEntregaByIdTipoDocumentoAndCAnno/{idTipoDocumento}/{cAnno}")
	public ResponseEntity<PlazosEntregaDto> getPlazoEntregaByIdTipoDocumentoAndCAnno(@PathVariable("idTipoDocumento") Long idTipoDocumento, @PathVariable("cAnno") Integer cAnno) {
				
		PlazosEntrega plazosEntrega =  plazosEntregaService.findByCAnnoAndTipoId(idTipoDocumento, cAnno);
		
		PlazosEntregaDto plazosEntregaDto = modelMapper.map(plazosEntrega, PlazosEntregaDto.class);
		
		return new ResponseEntity<>(plazosEntregaDto, HttpStatus.OK);		
	}
	
	/**
	 * Crea un nuevo plazo de entrega.
	 *
	 * @return ResponseEntity<TipoDocumentoGCDto>
	 */
	@PreAuthorize("hasAnyRole('N')")
    @Operation(summary = "Crea un plazo de entrega", description = "Este metodo crea un plazo de entrega", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @RequestMapping(value = "/createPlazoEntrega")
    public ResponseEntity<Object> createPlazoEntrega(@Parameter(description = "Plazo Entrega", required = true) @RequestBody final PlazosEntregaDto plazosEntregaDto){    	
    	
    	PlazosEntrega plazosEntrega = modelMapper.map(plazosEntregaDto, PlazosEntrega.class);
    	ResponseEntity<Object> response = null;
		
    	try {		
			
    		PlazosEntrega plazosEntregaNuevo = plazosEntregaService.createPlazosEntrega(plazosEntrega); 
    		 
    		PlazosEntregaDto plazosEntregaNuevoDto = modelMapper.map(plazosEntregaNuevo, PlazosEntregaDto.class);    		
    		
			response = new ResponseEntity<>(plazosEntregaNuevoDto, HttpStatus.OK);	
    	}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	        
	    return response;      
    } 
	
}
