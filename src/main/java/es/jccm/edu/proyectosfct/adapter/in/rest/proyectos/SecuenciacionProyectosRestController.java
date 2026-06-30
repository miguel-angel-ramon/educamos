package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos;

import java.io.IOException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.SecuenciacionProyectosDto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.ISecuenciacionProyectosService;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Proyectos FCT", description = "Servicio con las operaciones sobre Proyectos FCT")
public class SecuenciacionProyectosRestController {
	
	@Autowired
	private ISecuenciacionProyectosService secuenciacionProyectosService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
//	/**
//	 * Creación de Modulos de empresas.
//	 *
//	 * @param
//	 * @return the response entity
//	 */
//	//@PreAuthorize("hasAnyRole('P','PRO','C')")
//	@Operation(summary = "Creación de Secuenciacion de proyectos", description = "Este metodo crea Secuenciacion de proyectos", responses = {
//			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
//			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
//			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
//			@ApiResponse(responseCode = "404", description = "No encontrado") })
//	@PostMapping("/createSecuenciacionProyectos")
//	public ResponseEntity<SecuenciacionProyectosDto> createProyecto(
//			@Parameter(description = "Datos de secuenciacion de proyectos", required = true) @RequestBody final SecuenciacionProyectosDto secuenciacionProyectoDto) {
//		try {
//			SecuenciacionProyectos secuenciacionProyectosIn = modelMapper.map(secuenciacionProyectoDto, SecuenciacionProyectos.class);
//
//			secuenciacionProyectosIn = secuenciacionProyectosService.createSecuenciacionProyectos(secuenciacionProyectosIn);
//
//			SecuenciacionProyectosDto secuenciacionProyectosOut = modelMapper.map(secuenciacionProyectosIn, SecuenciacionProyectosDto.class);
//
//			return new ResponseEntity<SecuenciacionProyectosDto>(secuenciacionProyectosOut, HttpStatus.OK);
//
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
//	/**
//	 * actualiza los Datos de proyectos.
//	 *
//	 * @param
//	 * @return the response entity
//	 */
//	//@PreAuthorize("hasAnyRole('P','PRO','C')")
//	@Operation(summary = "Actualiza de  secuenciacion proyectos", description = "Este metodo actualiza secuenciacion proyectos", responses = {
//			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
//			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
//			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
//			@ApiResponse(responseCode = "404", description = "No encontrado") })
//	@PostMapping("/updateSecuenciacionProyecto")
//	public ResponseEntity<SecuenciacionProyectosDto> updateSecuenciacionProyecto(
//			@Parameter(description = "Datos del Proyecto", required = true) @RequestBody final SecuenciacionProyectosDto secProyectoDto) {
//		try {
//			SecuenciacionProyectos secProyectoIn = modelMapper.map(secProyectoDto, SecuenciacionProyectos.class);
//
//			secProyectoIn = secuenciacionProyectosService.updateSecuenciacionProyecto(secProyectoIn);
//
//			SecuenciacionProyectosDto secProyectoOut = modelMapper.map(secProyectoIn, SecuenciacionProyectosDto.class);
//
//			return new ResponseEntity<SecuenciacionProyectosDto>(secProyectoOut, HttpStatus.OK);
//
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	/**
	 * informacion de proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Secuencicacion de proyectos", description = "Este metodo devuelve la secuenciacion de un proyecto que recibe por id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getSecuenciacionProyectoId/{idProyecto}" })
	public ResponseEntity<SecuenciacionProyectosDto> getSecuenciacionProyectoId(@PathVariable("idProyecto") Long idProyecto) {
		
		Optional<SecuenciacionProyectos> secProyecto = secuenciacionProyectosService.getSecuenciacionProyectoId(idProyecto);
		SecuenciacionProyectosDto secProyectoDto = null;
		
		if(secProyecto.isPresent()) {
			secProyectoDto = modelMapper.map(secProyecto.get(), SecuenciacionProyectosDto.class);
		}else {
			secProyectoDto = modelMapper.map(secProyecto, SecuenciacionProyectosDto.class);
		}
		
		
		return new ResponseEntity<SecuenciacionProyectosDto>(secProyectoDto, HttpStatus.OK);
	}
	
//	/**
//	 * Borrado de una Secuencia de proyectos.
//	 *
//	 * @param idSecProy Id de la secuencia del proyecto
//	 * @return the response entity
//	 */
//	//@PreAuthorize("hasAnyRole('P','PRO','C')")
//	@Operation(summary = "Borrado de una secuencia de proyectos", description = "Este metodo borra los datos una secuencia de proyectos",
//			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
//	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
//			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
//			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
//			@ApiResponse(responseCode = "404", description = "No encontrado") })
//	@DeleteMapping("/deleteSecuenciacionProyecto/{idSecProyecto}")
//	public ResponseEntity<HttpStatus> deleteConvenio(
//			@Parameter(description = "Identificador del Convenio", required = true) @PathVariable("idSecProyecto") Long idSecProyecto) {
//		try {
//			secuenciacionProyectosService.deleteSecuenciacionProyectos(idSecProyecto);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	/**
	 * Crea una secuenciacion de proyecto con documento adjunto.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<RodalDocDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Crea una secuenciacion de proyecto con documento adjunto", description = "Crea una secuenciacion de proyecto con documento adjunto", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @RequestMapping(value = "/createSecuenciacionProyectoAdj/{cAnno}/{idCentro}", method = RequestMethod.POST,  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> createSecuenciacionProyectoAdj(@RequestPart SecuenciacionProyectosDto secProyectoDto,
    											     		 @RequestPart(required = false) MultipartFile file, @PathVariable("cAnno") Long cAnno, @PathVariable("idCentro") Long idCentro ) throws RodalExceptionService, InsertarDocFault, IOException {    	
    	
    	SecuenciacionProyectos secProy = modelMapper.map(secProyectoDto, SecuenciacionProyectos.class);
    	ResponseEntity<Object> response = null;
		
    	try {		
			
    		SecuenciacionProyectos secProyNuevo = secuenciacionProyectosService.createDocumentoGC(secProy, file, cAnno, idCentro);  
    		 
    		SecuenciacionProyectosDto secProyDto = modelMapper.map(secProyNuevo, SecuenciacionProyectosDto.class);    		
    		
			response = new ResponseEntity<Object>(secProyDto, HttpStatus.OK);			
				
			} catch (RodalExceptionService e) {
				response = new ResponseEntity<Object>("Error al guardar el documento adjunto.", HttpStatus.BAD_REQUEST);
			}
	         catch (Exception e) {
	        	response = new ResponseEntity<Object>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
			}
	        
	        return response;  
	        
    } 
	
	
	
}
