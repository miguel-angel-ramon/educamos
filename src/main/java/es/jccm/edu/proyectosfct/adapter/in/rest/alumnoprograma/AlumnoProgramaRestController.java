package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoProgramaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.CentroAlumnosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.UnidadCursoDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.CentroAlumnos;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma.IAlumnoProgramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio con las operaciones sobre Programas FCT")
public class AlumnoProgramaRestController {

	@Autowired
	IAlumnoProgramaService alumnoProgramaService;
	
	@Autowired
	private ModelMapper modelMapper;


	/**
	 * Creación de los Datos de Alumno Convenio Programa.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de Alumno Convenio Programa", description = "Este metodo crea Alumno Convenio Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createAlumnoConvenioPrograma")
	public ResponseEntity<List<AlumnoProgramaDto>> createAlumnoConvenioPrograma(@RequestBody final List<AlumnoProgramaDto> alumnosProgramaDto,
																				@RequestParam("idConvProg") Long idConvProg) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<AlumnoPrograma> alumnosProgramaIn = alumnosProgramaDto.stream().map(x -> modelMapper.map(x, AlumnoPrograma.class)).collect(Collectors.toList());
					
			alumnosProgramaIn = alumnoProgramaService.createAlumnoPrograma(idConvProg,alumnosProgramaIn);
			
			List<AlumnoProgramaDto> alumnosProgramaOut = alumnosProgramaIn.stream().map(x -> modelMapper.map(x, AlumnoProgramaDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<AlumnoProgramaDto>>(alumnosProgramaOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Update alumnos seleccionador", description = "Actualiza los alumnos selccionados de la pantalla Asignar Alumnos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PutMapping("/updateSegSocialAlumProg")
	public ResponseEntity<List<AlumnoDto>> updateSegSocialAlumProg(@RequestBody final List<AlumnoDto> alumSeleccionados) {

		try {

			alumnoProgramaService.updateAlumSeleccionados(alumSeleccionados);

			return new ResponseEntity<List<AlumnoDto>>(alumSeleccionados, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	
	/**
	 * Create convenios programa horario alumnado.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de convenios programa horario personal alumnado", description = "Este metodo crea convenio programa horario personal alumnado", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenioProgramaPeriodosHorariosAlumno")
	public ResponseEntity<Object> createConvenioProgramaPeriodosHorariosAlumno(
			@Parameter(description = "Convenio programa horario alumnado", required = true) @RequestBody final List<ConvProgAluHorPeriodoFctDto> listConvProgAluHorPeriodoFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);						
		
			List<ConvProgAluHorPeriodoFctDto> listConvProgHorPeriodoFctOut = alumnoProgramaService.createConvenioProgramaPeriodosHorariosAlumno(listConvProgAluHorPeriodoFctDto);
		
			response = new ResponseEntity<>(listConvProgHorPeriodoFctOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	/**
	 * Update de los Datos de Alumno Convenio Programa.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Update de Alumno Convenio Programa", description = "Este metodo actualiza Alumno Convenio Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateAlumnoConvenioPrograma")
	public ResponseEntity<AlumnoProgramaDto> updateAlumnoConvenioProyecto(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final AlumnoProgramaDto alumnosProgramaDto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			AlumnoPrograma alumnosProgramaIn = modelMapper.map(alumnosProgramaDto, AlumnoPrograma.class);
					
			alumnosProgramaIn = alumnoProgramaService.updateAlumnoConvenioPrograma(alumnosProgramaIn);
			
			AlumnoProgramaDto alumnosProgramaOut = modelMapper.map(alumnosProgramaIn, AlumnoProgramaDto.class);
			
			return new ResponseEntity<AlumnoProgramaDto>(alumnosProgramaOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Read
	
	/**
	 * Get Alumno Convenio Programa.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
	@Operation(summary = "Alumno Convenio Programa", description = "Este metodo devuelve el Alumno Convenio Programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnoConvenioPrograma/{idConvProgAlu}")
	public ResponseEntity<AlumnoProgramaDto> getAlumnoConvenioPrograma(@PathVariable("idConvProgAlu") Long idConvProgAlu){
		
		AlumnoPrograma AluConvProg = alumnoProgramaService.getAlumnoProgramaById(idConvProgAlu); 
		
	   AlumnoProgramaDto AluConvProgDTO = modelMapper.map(AluConvProg, AlumnoProgramaDto.class);
		
		return new ResponseEntity<AlumnoProgramaDto>(AluConvProgDTO, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Alumnos-ConvenioProgramas", description = "Este metodo devuelve una lista de Alumnos-ConvenioPrograma",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosConveniosProgramas/{idConvenio}")
	public ResponseEntity<List<AlumnoProgramaDto>> getAlumnosConveniosProgramas( @PathVariable("idConvenio") Long idConvenio){
		
		List<AlumnoPrograma> alumnosConvenioProgramas =  alumnoProgramaService.getAlumnosConveniosProgramas(idConvenio);
		
		List<AlumnoProgramaDto> alumnosConvenioProgramasDto = alumnosConvenioProgramas.stream().map(x -> modelMapper.map(x, AlumnoProgramaDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<AlumnoProgramaDto>>(alumnosConvenioProgramasDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Alumnos disponibles", description = "Este metodo devuelve una lista de Alumnos asociado a un centro y oferta generica",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosPrograma/{idCentro}/{idOfertamatrig}/{anno}/{idConvProg}")
	public ResponseEntity<List<AlumnoDto>> getAlumnosPrograma(@PathVariable("idCentro") Long idCentro,
			                                          @PathVariable("idOfertamatrig") Long idOfertamatrig, 
													  @PathVariable("anno") int cAnno,
													  @PathVariable("idConvProg") Long idConvProg){
		
		List<Alumno> alumnos =  alumnoProgramaService.getAlumnosPrograma(idCentro, idOfertamatrig, cAnno, idConvProg);
		
		List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<AlumnoDto>>(alumnosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Alumnos seleccionadops asociados al convenio-programa", description = "Este metodo devuelve una lista de Alumnos seleccionadops asociados al convenio-programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosSeleccionados/{idCentro}/{idOfertamatrig}/{anno}/{idConvProg}")
	public ResponseEntity<List<AlumnoDto>> getAlumnosSeleccionados(@PathVariable("idCentro") Long idCentro, @PathVariable("idOfertamatrig") Long idOfertamatrig, 
															  @PathVariable("anno") int cAnno, @PathVariable("idConvProg") Long idConvProg){
		
		List<Alumno> alumnos =  alumnoProgramaService.getAlumnosSeleccionados(idCentro, idOfertamatrig, cAnno, idConvProg);
		
		List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<AlumnoDto>>(alumnosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Unidades de un curso", description = "Este metodo devuelve una lista de Unidades de un curso",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesCurso/{idCentro}/{idOfertamatrig}/{anno}")
	public ResponseEntity<List<UnidadCursoDto>> getUnidadesCurso(@PathVariable("idCentro") Long idCentro, @PathVariable("idOfertamatrig") Long idOfertamatrig, 
															  @PathVariable("anno") int cAnno){
		
		List<UnidadCurso> unidades =  alumnoProgramaService.getUnidades(idCentro, idOfertamatrig, cAnno);
		
		List<UnidadCursoDto> unidadesDto = unidades.stream().map(x -> modelMapper.map(x, UnidadCursoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<UnidadCursoDto>>(unidadesDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Unidades de un curso", description = "Este metodo devuelve una lista de Unidades de una modalidad",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesModalidad/{idCentro}/{idModalidad}/{anno}/{idTutor}")
	public ResponseEntity<List<UnidadCursoDto>> getUnidadesModalidad(@PathVariable("idCentro") Long idCentro, @PathVariable("idModalidad") Long idModalidad, 
															  @PathVariable("anno") int cAnno,
															  @PathVariable("idTutor") Long idTutor){		
		
		List<UnidadCurso> unidades =  alumnoProgramaService.getUnidadesModalidad(idCentro, idModalidad, cAnno, idTutor);
		
		List<UnidadCursoDto> unidadesDto = unidades.stream().map(x -> modelMapper.map(x, UnidadCursoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<UnidadCursoDto>>(unidadesDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Nombre centro", description = "Este metodo devuelve el nombre completo del centro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNombreCentro/{idCentro}")
	public ResponseEntity<CentroAlumnosDto> getNombreCentro(@PathVariable("idCentro") Long idCentro){
		
	   CentroAlumnos centro = alumnoProgramaService.getNombreCentro(idCentro); 
		
       CentroAlumnosDto centroDTO = modelMapper.map(centro, CentroAlumnosDto.class);
		
		return new ResponseEntity<CentroAlumnosDto>(centroDTO, HttpStatus.OK);
	}

	
	/**
	 * Devuelve el número de alumnos por id convenio-programa
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Contador de alumnos por id convenio-programa", description = "Este metodo devuelve el numero de alumnos por id convenio-programa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountAlumnosByIdConvProg/{idConvProg}")
	public ResponseEntity<Integer> getCountEmpresas(@PathVariable("idConvProg") Long idConvProg){
		return new ResponseEntity<Integer>(alumnoProgramaService.countByconvenioProgramaId(idConvProg), HttpStatus.OK);
	}

    /**
     * Obtiene la fecha de finalización de alta en la SS
     * @param idConvProgAlu
     * @param idMatricula
     * @return
     */
	@Operation(summary = "Obtener fecha finalización de alta en la seguridad social", description = "Este metodo devuelve la fecha de finalización del alta en la seguridad Social",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechaFinAltaSS/{idConvProgAlu}/{idMatricula}")
	public ResponseEntity<Date> getFechaFinAltaSS(@PathVariable("idConvProgAlu") Long idConvProgAlu,
												  @PathVariable("idMatricula") Long idMatricula){

		Date fechaFin = alumnoProgramaService.obtenerFechaFinAltaSS(idConvProgAlu, idMatricula);

		return ResponseEntity.ok(fechaFin);

	}
}
