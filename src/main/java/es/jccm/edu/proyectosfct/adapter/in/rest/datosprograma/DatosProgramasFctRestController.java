package es.jccm.edu.proyectosfct.adapter.in.rest.datosprograma;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.datosprograma.model.DatosProgramaFctDto;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.DatosProgramaFct;
import es.jccm.edu.proyectosfct.application.ports.in.datosprograma.IDatosProgramaFctService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Datos Programa FCT", description = "Servicio con las operaciones sobre Datos Programa FCT")
public class DatosProgramasFctRestController {

	@Autowired
	IDatosProgramaFctService datosProgramaFctService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Creación de los Datos de un Programa.
	 *
	 * @param datosProgramaFctDto Datos del programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de un Progama", description = "Este metodo crea los datos de un Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createDatosPrograma/{idPrograma}")
	public ResponseEntity<List<DatosProgramaFctDto>> createPrograma(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final List<DatosProgramaFctDto> listDatosProgramaFctDto,
																			@PathVariable("idPrograma") Long idPrograma) {

		try {
			List<DatosProgramaFct> listDatosProgramaIn = listDatosProgramaFctDto.stream().map(x -> modelMapper.map(x, DatosProgramaFct.class)).collect(Collectors.toList());
			
			listDatosProgramaIn = datosProgramaFctService.createDatosPrograma(listDatosProgramaIn,idPrograma);
			
			List<DatosProgramaFctDto> listDatosProgramaOut = listDatosProgramaIn.stream().map(x -> modelMapper.map(x, DatosProgramaFctDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<DatosProgramaFctDto>>(listDatosProgramaOut, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Borrado de los Datos de un Programa.
	 *
	 * @param idDatosPrograma Id de los datos del programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado Datos Programa", description = "Este metodo borra los datos de un Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteDatosPrograma/{idDatosPrograma}")
	public ResponseEntity<HttpStatus> deleteConvenio(
			@Parameter(description = "Identificador de los Datos Programa", required = true) @PathVariable("idDatosPrograma") Long id) {
		try {
			
			datosProgramaFctService.deleteDatosPrograma(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Datos de un programa.
	 *
	 * @param idPrograma Id de un programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
	@Operation(summary = "Obtener las actividades de un programa", description = "Este metodo devuelve las actividades de un programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getActividadesPrograma/{idPrograma}")
	public ResponseEntity<List<DatosProgramaFctDto>> getActividadesPrograma(
			@Parameter(description = "Identificador del Programa", required = true) @PathVariable("idPrograma") Long idPrograma) {
		try {
			List<DatosProgramaFct> datosPrograma = datosProgramaFctService.getActividadesPrograma(idPrograma);
			
			List<DatosProgramaFctDto> datosProgramaDto = datosPrograma.stream().map(x -> modelMapper.map(x, DatosProgramaFctDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<DatosProgramaFctDto>>(datosProgramaDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * Numero de datos programas.
	 *
	 * @param idPrograma Id de un programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener las actividades de un programa", description = "Este metodo devuelve las actividades de un programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountDatosPrograma/{idPrograma}")
	public ResponseEntity<Integer> getCountDatosPrograma(
			@Parameter(description = "Identificador del Programa", required = true) @PathVariable("idPrograma") Long idPrograma) {
		try {
			return new ResponseEntity<Integer>(datosProgramaFctService.getCountDatosPrograma(idPrograma), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

}
