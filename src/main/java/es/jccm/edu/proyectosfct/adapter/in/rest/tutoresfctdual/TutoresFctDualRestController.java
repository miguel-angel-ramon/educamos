package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.DatosSustitutoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.ListadoTutoresFctDualDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.ListadoTutoresFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ITutoresFctDualService;
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
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Tutores", description = "Servicio con las operaciones sobre Tutores Fct o Dual")
public class TutoresFctDualRestController {
	
	@Autowired
	private ITutoresFctDualService tutoresFctDualService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Create
	
	/**
	 * Creación de los Datos de un Tutor.
	 *
	 * @param tutorDto Datos del tutor
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de un Tutor", description = "Este metodo crea los datos de un Tutor", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })	
	@PostMapping("/createTutorFctDual")
	public ResponseEntity<TutorFctDualDto> createTutorFctDual(
			@Parameter(description = "Datos del Tutor", required = true) @RequestBody final TutorFctDualDto tutorDto) {
		
		TutorFctDual tutorIn = modelMapper.map(tutorDto, TutorFctDual.class);
		
		tutorIn = tutoresFctDualService.createTutorFctDual(tutorIn);
		
		TutorFctDualDto tutorOut = modelMapper.map(tutorIn, TutorFctDualDto.class);
		
		return new ResponseEntity<TutorFctDualDto>(tutorOut, HttpStatus.OK);
	}
	
	// Read
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Tutores", description = "Este metodo devuelve una lista con todos los Tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllTutoresFctDual")
	public ResponseEntity<List<TutorFctDualDto>> getAllTutoresFctDual(){
		
		List<TutorFctDualDto> tutoresOut = tutoresFctDualService.getAllTutoresFctDual().stream().map(x -> modelMapper.map(x, TutorFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TutorFctDualDto>>(tutoresOut, HttpStatus.OK);
		
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','CFT','FCT')")
	@Operation(summary = "Lista de Tutores", description = "Este metodo devuelve una lista con todos los Tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllTutoresFctDual/{idCentro}")
	public ResponseEntity<List<ListadoTutoresFctDualDto>> getAllTutoresFctDual(
			@Parameter(description = "Identificador del centro", required = true) @PathVariable(name = "idCentro", required = false) Long idCentro){		
		
		List<ListadoTutoresFctDual> tutoresOut = tutoresFctDualService.getAllTutoresFctDualCentro(idCentro);
		
		List<ListadoTutoresFctDualDto> empresasOutDto = tutoresOut.stream().map(x -> modelMapper.map(x, ListadoTutoresFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoTutoresFctDualDto>>(empresasOutDto, HttpStatus.OK);
	
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Tutores", description = "Este metodo devuelve una lista con todos los Tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllTutoresDelegacion/{idPerfil}/{idCentro}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<ListadoTutoresFctDualDto>> getAllTutoresDelegacion(
		@Parameter(description = "Identificador del perfil", required = true) @PathVariable(name = "idPerfil", required = false) Long idPerfil,
		@Parameter(description = "Identificador del centro", required = true) @PathVariable(name = "idCentro", required = false) Long idCentro,
		@Parameter(description = "Identificador del centro filtro", required = true) @PathVariable(name = "idCentroCombo", required = false) Long idCentroCombo,
		@Parameter(description = "Identificador de la provincia", required = true) @PathVariable(name = "idProvincia", required = false) Long idProvincia,
		@RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		List<ListadoTutoresFctDual> tutoresOut = tutoresFctDualService.getAllTutoresDelegacion(datosUsuario.getXUsuarioDelphos(),
																							   idPerfil,
																							   idCentro,
																							   idCentroCombo,
																							   idProvincia);
		
		List<ListadoTutoresFctDualDto> empresasOutDto = tutoresOut.stream().map(x -> modelMapper.map(x, ListadoTutoresFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoTutoresFctDualDto>>(empresasOutDto, HttpStatus.OK);
	
	}
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Tutores por id", description = "Este metodo devuelve una lista con el tutor que se envía como parámetro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllTutoresIdFctDual/{idCentro}/{idTutor}")
	public ResponseEntity<List<ListadoTutoresFctDualDto>> getAllTutoresIdFctDual(
			@Parameter(description = "Identificador del centro", required = true) @PathVariable(name = "idCentro", required = false) Long idCentro,
			@Parameter(description = "Identificador del tutor", required = true) @PathVariable(name = "idTutor", required = false) Long idTutor){		
		
		List<ListadoTutoresFctDual> tutoresOut = tutoresFctDualService.getAllTutoresIdFctDual(idCentro, idTutor);
		
		List<ListadoTutoresFctDualDto> empresasOutDto = tutoresOut.stream().map(x -> modelMapper.map(x, ListadoTutoresFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoTutoresFctDualDto>>(empresasOutDto, HttpStatus.OK);
	
	}
	
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Tutores", description = "Este metodo devuelve una lista con todos los Tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllTutorFctDual/{idCentro}")
	public ResponseEntity<List<TutorFctDualDto>> getAllTutorFctDual(
			@Parameter(description = "Identificador del centro", required = true) @PathVariable(name = "idCentro", required = false) Long idCentro){		
		
		List<TutorFctDual> tutoresOut = tutoresFctDualService.getAllTutorFctDualCentro(idCentro);
		
		List<TutorFctDualDto> empresasOutDto = tutoresOut.stream().map(x -> modelMapper.map(x, TutorFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TutorFctDualDto>>(empresasOutDto, HttpStatus.OK);
	
	}
	
	
	/**
	 * Page de tutores.
	 *
	 * @param name Nombre del tutor a filtrar
	 * @param page Pagina que se desea devolver
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener datos de un Tutor", description = "Este metodo devuelve los datos de un Tutor", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresFctDualByName/{name}/{page}")
	public ResponseEntity<Page<TutorFctDualDto>> getTutoresFctDualByName(
			@Parameter(description = "Identificador del Tutor", required = true) @PathVariable(name = "name", required = false) String name,
			@Parameter(description = "Página que se quiere obtener", required = true) @PathVariable(name = "page", required = false) Integer page){
		
		List<TutorFctDual> tutoresList = tutoresFctDualService.getTutoresFctDualByName(name, page).getContent();
		
		List<TutorFctDualDto> tutoresDtoList = tutoresList.stream().map(x -> modelMapper.map(x, TutorFctDualDto.class)).collect(Collectors.toList());
		
		Pageable paging = PageRequest.of(page, 10, Sort.by("nombreEmpresa"));
		
		return new ResponseEntity<Page<TutorFctDualDto>>(new PageImpl<TutorFctDualDto>(tutoresDtoList, paging, 10), HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de un Tutor", description = "Este metodo devuelve los datos de un tutor", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getTutorById/{idTutor}")
		public ResponseEntity<TutorFctDualDto> getTutorById(
				@Parameter(description = "Identificador del Conveio", required = true) @PathVariable("idTutor") Long idTutor) {
			
		TutorFctDualDto tutorOut = modelMapper.map(tutoresFctDualService.getById(idTutor), TutorFctDualDto.class);
			
			return new ResponseEntity<TutorFctDualDto>(tutorOut, HttpStatus.OK);
		}
	
	// Update
	
	/**
	 * Actualización de los Datos de un Tutor.
	 *
	 * @param tutorDto Datos del tutor
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualización de los datos de un Tutor", description = "Este metodo actualiza los datos de un Tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateTutorFctDual")
	public ResponseEntity<TutorFctDualDto> updateFctDual(@Parameter(description = "Datos del Tutor", required = true) @RequestBody final TutorFctDualDto tutorDto) {
				
		TutorFctDual tutorIn = modelMapper.map(tutorDto, TutorFctDual.class);
		
		tutorIn = tutoresFctDualService.updateTutorFctDual(tutorIn);
		
		TutorFctDualDto tutorOut = modelMapper.map(tutorIn, TutorFctDualDto.class);
		
		return new ResponseEntity<TutorFctDualDto>(tutorOut, HttpStatus.OK);
	}
	
	// Delete
	
	/**
	 * Borrado de un Tutor.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado de un Tutor", description = "Este metodo borra los datos de un Tutor",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteTutorFctDual/{id}")
	public ResponseEntity<HttpStatus> deleteTutorFctDual(@Parameter(description = "Identificador del Tutor", required = true) @PathVariable("id") Long id) {
		try {
			tutoresFctDualService.deleteTutorFctDual(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Borrado de un Tutor.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Programas de un Tutor", description = "Este metodo devuelve los programas de una tutor",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTieneDependenciasTutorById/{id}")
	public ResponseEntity<Boolean> getTieneDependenciasTutorById(@Parameter(description = "Identificador del Tutor", required = true) @PathVariable("id") Long id) {
		try {						
			return new ResponseEntity<Boolean>(tutoresFctDualService.getTieneDependenciasTutorById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener datos de un Tutor", description = "Este metodo devuelve los datos de un tutor dado el xEmpleado y tomapos", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getTutorByEmpleado/{xEmpleado}/{fTomapos}")
		public ResponseEntity<TutorFctDualDto> getTutorByEmpleado(
				@Parameter(description = "Identificador del xEmpleado", required = true) @RequestHeader(Constants.AUTHORIZATION) String jwt,
				@Parameter(description = "Fecha de tomapos empleado", required = true) @PathVariable("fTomapos") String fTomapos) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
		TutorFctDualDto tutorOut = modelMapper.map(tutoresFctDualService.getTutorByEmpleado(datosUsuario.getIdEmpleadoDelphos(),fTomapos), TutorFctDualDto.class);		
			
		return new ResponseEntity<TutorFctDualDto>(tutorOut, HttpStatus.OK);
		}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Es tutor sustituto", description = "Este metodo devuelve el id_tutorfctdual a partir del x_empleadoy y f_tomapos del tutor que lo sustituye", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getIdTutorSustituido/{xEmpleado}/{fTomapos}")
		public ResponseEntity<DatosSustitutoDto> getIdTutorSustituido(
				@Parameter(description = "Identificador del xEmpleado", required = true) @RequestHeader(Constants.AUTHORIZATION) String jwt,
				@Parameter(description = "Fecha de tomapos empleado", required = true) @PathVariable("fTomapos") String fTomapos) {			
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		DatosSustitutoDto datosSustituto = modelMapper.map(tutoresFctDualService.getIdTutorSustituido(datosUsuario.getIdEmpleadoDelphos(),fTomapos), DatosSustitutoDto.class);	
			
		return new ResponseEntity<DatosSustitutoDto>(datosSustituto, HttpStatus.OK);
		}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Es tutor sustituto", description = "Este metodo devuelve el id_tutorfctdual a partir del x_empleadoy y f_tomapos del tutor sustituido", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getTutorSustituto/{xEmpleado}/{fTomapos}")
		public ResponseEntity<DatosSustitutoDto> getTutorSustituto(
				@Parameter(description = "Identificador del xEmpleado", required = true) @RequestHeader(Constants.AUTHORIZATION) String jwt,
				@Parameter(description = "Fecha de tomapos empleado", required = true) @PathVariable("fTomapos") String fTomapos) {			
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		DatosSustitutoDto datosSustituto = modelMapper.map(tutoresFctDualService.getTutorSustituto(datosUsuario.getIdEmpleadoDelphos(),fTomapos), DatosSustitutoDto.class);	
			
		return new ResponseEntity<DatosSustitutoDto>(datosSustituto, HttpStatus.OK);
		}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','ALU','CFT')")
	@Operation(summary = "Codigo de un perfil", description = "Este metodo devuelve el código de un id perfil dado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getCodigoPerfil/{idPerfil}")
		public ResponseEntity<String> getCodigoPerfil(				
				@Parameter(description = "identificador del perfil del usuario", required = true) @PathVariable("idPerfil") Long idPerfil) {			
			
		  return new ResponseEntity<String>(tutoresFctDualService.getCodigoPerfil(idPerfil), HttpStatus.OK);
		}
	
	

}
