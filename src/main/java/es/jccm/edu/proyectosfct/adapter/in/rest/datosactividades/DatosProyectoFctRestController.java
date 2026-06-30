package es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades;

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

import es.jccm.edu.proyectosfct.adapter.in.rest.datosactividades.model.DatosProyectosFctDto;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;
import es.jccm.edu.proyectosfct.application.ports.in.datosproyectos.IDatosProyectosFctService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Datos Proyecto FCT", description = "Servicio con las operaciones sobre Datos Proyecto FCT")
public class DatosProyectoFctRestController {

	@Autowired
	IDatosProyectosFctService datosProyectosFctService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Creación de los Datos de un proyecto.
	 *
	 * @param datosProgramaFctDto Datos del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de un proyecto", description = "Este metodo crea los datos de un proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createDatosProyecto")
	public ResponseEntity<List<DatosProyectosFctDto>> createDatosProyecto(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final List<DatosProyectosFctDto> listDatosProgramaFctDto) {

		try {
			List<DatosProyectosFct> listDatosProyectoIn = listDatosProgramaFctDto.stream().map(x -> modelMapper.map(x, DatosProyectosFct.class)).collect(Collectors.toList());
			
			listDatosProyectoIn = datosProyectosFctService.createDatosProyecto(listDatosProyectoIn);
			
			List<DatosProyectosFctDto> listDatosProyectoOut = listDatosProyectoIn.stream().map(x -> modelMapper.map(x, DatosProyectosFctDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<DatosProyectosFctDto>>(listDatosProyectoOut, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Borrado de los Datos de un proyecto.
	 *
	 * @param idDatosPrograma Id de los datos del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado Datos proyecto", description = "Este metodo borra los datos de un proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteDatosProyecto/{idDatosProyecto}")
	public ResponseEntity<HttpStatus> deleteDatosProyecto(
			@Parameter(description = "Identificador de los Datos proyecto", required = true) @PathVariable("idDatosProyecto") Long id) {
		try {
			
			datosProyectosFctService.deleteDatosProyecto(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Datos de un proyecto.
	 *
	 * @param idPrograma Id de un proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
	@Operation(summary = "Obtener las actividades de un proyecto", description = "Este metodo devuelve las actividades de un proyecto", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getActividadesProyecto/{idProyecto}")
	public ResponseEntity<List<DatosProyectosFctDto>> getActividadesProyecto(
			@Parameter(description = "Identificador del Programa", required = true) @PathVariable("idProyecto") Long idProyecto) {
		try {
			List<DatosProyectosFct> datosProyecto = datosProyectosFctService.getActividadesProyecto(idProyecto);
			
			List<DatosProyectosFctDto> datosProyectoDto = datosProyecto.stream().map(x -> modelMapper.map(x, DatosProyectosFctDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<DatosProyectosFctDto>>(datosProyectoDto, HttpStatus.OK);
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
	@Operation(summary = "Obtener las actividades de un proyecto", description = "Este metodo devuelve las actividades de un proyecto", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountDatosProyecto/{idProyecto}")
	public ResponseEntity<Integer> getCountDatosProyecto(
			@Parameter(description = "Identificador del Programa", required = true) @PathVariable("idProyecto") Long idProyecto) {
		try {
			return new ResponseEntity<Integer>(datosProyectosFctService.getCountDatosProyecto(idProyecto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

}
