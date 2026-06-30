package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.InformacionProyectosDto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.InformacionProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IInformacionProyectosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Datos Proyectos FCT", description = "Servicio con las operaciones sobre Datos Proyectos FCT")
public class InformacionProyectosRestController {
	
	@Autowired
	private IInformacionProyectosService infoProyectosService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * informacion de proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "informacion de proyecto", description = "Este metodo devuelve el informacion de proyecto que recibe por id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getInfoProyectoId/{idProyecto}" })
	public ResponseEntity<InformacionProyectosDto> getInfoProyectoId(@PathVariable("idProyecto") Long idProyecto) {
		
		Optional<InformacionProyectos> infoProyecto = infoProyectosService.getInfoProyectoId(idProyecto);
		InformacionProyectosDto infoProyectoDto = null;
		
		if(infoProyecto.isPresent()) {
			infoProyectoDto = modelMapper.map(infoProyecto.get(), InformacionProyectosDto.class);
		}else {
			infoProyectoDto = modelMapper.map(infoProyecto, InformacionProyectosDto.class);
		}
		
		
		return new ResponseEntity<InformacionProyectosDto>(infoProyectoDto, HttpStatus.OK);
	}
	
	
	/**
	 * Creación de los Datos de proyectos.
	 *
	 * @param 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de Informacion de proyectos", description = "Este metodo crea Informacion de proyectos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createInfoProyecto/{anno}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<InformacionProyectosDto> createDatosProyecto(
			@Parameter(description = "Informacion del Proyecto", required = true) @RequestPart final InformacionProyectosDto informacionProyectosDto,
																				  @RequestPart(required = false) MultipartFile file,
																		          @PathVariable("anno") Long anno) {
		try {
			InformacionProyectos infoProyectoIn = modelMapper.map(informacionProyectosDto, InformacionProyectos.class);
			
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			infoProyectoIn = infoProyectosService.createInfoProyecto(infoProyectoIn, file, anno);

			InformacionProyectosDto infoProyectoOut = modelMapper.map(infoProyectoIn, InformacionProyectosDto.class);
			
			return new ResponseEntity<InformacionProyectosDto>(infoProyectoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * count actividades proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "count actividades proyecto", description = "Este metodo devuelve count actividades proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/countActividadesProyecto/{idProyecto}" })
	public ResponseEntity<Integer> countActividadesProyecto(@PathVariable("idProyecto") Long idProyecto) {
		
		Integer numActividades = infoProyectosService.countActividadesProyecto(idProyecto);		
		
		return new ResponseEntity<Integer>(numActividades, HttpStatus.OK);
	}
	
	

}
