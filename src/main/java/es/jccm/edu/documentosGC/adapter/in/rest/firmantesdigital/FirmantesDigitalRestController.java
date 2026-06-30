package es.jccm.edu.documentosGC.adapter.in.rest.firmantesdigital;


import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DetalleFirmanteDto;
import es.jccm.edu.documentosGC.adapter.in.rest.firmantesdigital.model.AdjuntosFirmantesDto;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.DetalleFirmante;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.AdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.FirmanteRequest;
import es.jccm.edu.documentosGC.application.ports.in.firmantesdigital.IFirmantesDigitalService;
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
@Tag(name = "Firmantes digital", description = "Servicio con las operaciones sobre los firmantes digitales de las actas documentosgc")
public class FirmantesDigitalRestController {
	
	@Autowired
	private IFirmantesDigitalService firmantesDigitalService;
	
	@Autowired
	ModelMapper modelMapper;
	
	/**
	 * Creacion de los Datos de para la tabla DGC_ADJUNTO_FIRMANTES.
	 *
	 * @param estadoDocumentoGCDto Datos Estado documento
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Creacionn datos DGC_ADJUNTO_FIRMANTES", description = "Este metodo crea los datos DGC_ADJUNTO_FIRMANTES", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createAdjuntosFirmantes/{idAdjunto}/{conTutor}")
	public ResponseEntity<Object> createAdjuntosFirmantes(@RequestBody final List<FirmanteRequest> firmantes,
														  @PathVariable("idAdjunto") Long idAdjunto,
														  @PathVariable("conTutor") String conTutor) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<AdjuntosFirmantes> adjuntosFirmantesPrimariaIn = null ;
			
			if (conTutor.equals("P")) {
				
				adjuntosFirmantesPrimariaIn = firmantesDigitalService.createAdjuntosFirmantesPartes(firmantes, 
																							  		idAdjunto);				
				
			} else {
				
				adjuntosFirmantesPrimariaIn = firmantesDigitalService.createAdjuntosFirmantes(firmantes, 
																							  idAdjunto,
																							  conTutor);
				
			}
			
			List<AdjuntosFirmantesDto> adjuntosFirmantesPrimariaOut = adjuntosFirmantesPrimariaIn.stream().map(entity -> modelMapper.map(entity, AdjuntosFirmantesDto.class)).collect(Collectors.toList());		
		
			response = new ResponseEntity<>(adjuntosFirmantesPrimariaOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}	
	
	/**
	 * Actualiza un fichero Rodal a partir de su ID.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<RodalDocDto>
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
    @Operation(summary = "Actualizar documento", description = "Este metodo actualiza un documento recibido en rodal", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/actualizarDocumentoFirmado/{idAdjunto}/{idPerfil}/{idEmpleado}/{idUsuario}")
    public ResponseEntity<Boolean> actualizarDocumentoFirmado(@Parameter(description = "Identificador del adjunto", required = true) @PathVariable("idAdjunto") Long idAdjunto,
    														  @Parameter(description = "Identificador del perfil", required = true) @PathVariable("idPerfil") Long idPerfil,
    														  @Parameter(description = "Identificador del empleado", required = true) @PathVariable("idEmpleado") Long idEmpleado,    														  
    														  @Parameter(description = "Identificador del usuario", required = true) @PathVariable("idUsuario") Long idUsuario,
    														  @RequestParam(value = "tipoFirma", required = false) String tipoFirma,
    		                                                  HttpServletResponse response) {
		
		try {
			
			if(StringUtils.isEmpty(tipoFirma)) {
				tipoFirma = "D";
			}
			
			return new ResponseEntity<Boolean>(firmantesDigitalService.actualizarDocumentoFirmado(idAdjunto,idPerfil, idEmpleado,idUsuario, tipoFirma), HttpStatus.OK);
			
	        } catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
    }
    

	/**
	 * Devuelve una lista con el detalle de los firmantes a partir de un idAdjunto.
	 *
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
    @Operation(summary = "Detalle firmantes", description = "Este metodo devuelve el detalle de los firmantes", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDetalleFirmantes/{idAdjunto}")
    public ResponseEntity<List<DetalleFirmanteDto>> getDetalleFirmantes(@Parameter(description = "Identificador del adjunto", required = true) @PathVariable("idAdjunto") Long idAdjunto) {

		try {				

			List<DetalleFirmante> firmantes = firmantesDigitalService.getDetalleFirmantes(idAdjunto);			

			List<DetalleFirmanteDto> firmantesDto = firmantes.stream().map(entity -> modelMapper.map(entity, DetalleFirmanteDto.class)).collect(Collectors.toList());		

			return new ResponseEntity<>(firmantesDto, HttpStatus.OK);


	        } catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
    }

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
    @Operation(summary = "Obtener entorno de la firma", description = "Este metodo devuelve el entorno de la firma", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getEntornoFirma")
    public ResponseEntity<List<String>> getEntornoFirma(){
    	try {
			return new ResponseEntity<>(firmantesDigitalService.getEntornoFirma(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    	
    }
    
	@Operation(summary = "Numero firmantes adjunto", description = "Este metodo devuelve el numero de personas que han firmado un idAdjunto", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNumeroFirmados/{idAdjunto}")
	public ResponseEntity<Integer> getNumeroFirmados(@PathVariable("idAdjunto") Long idAdjunto) {
		
		return new ResponseEntity<Integer>(firmantesDigitalService.getNumeroFirmados(idAdjunto), HttpStatus.OK);
		
	}
    
    
    

}
