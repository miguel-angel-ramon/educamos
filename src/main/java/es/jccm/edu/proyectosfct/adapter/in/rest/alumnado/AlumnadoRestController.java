package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.AlumnadoAltasBajasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.AlumnadoAltasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.AlumnadoNSSDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.AptoEvaluacionAlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ComunicacionDiasPracticasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.EvaAluActProgDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.EvaAluActProyDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.FechaSemanaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ListadoAlumnadoTutorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ParsemActProgDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ParsemActProyDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ParsemAluProgDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ParsemAluProyDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.UnidadCursoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.CursoModalidadDto;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltasBajas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoNSS;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AptoEvaluacionAlumno;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ComunicacionDiasPracticas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.FechaSemana;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ListadoAlumnadoTutor;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProy;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;
import es.jccm.edu.proyectosfct.application.ports.in.alumnado.IAlumnadoService;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
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
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio con las operaciones sobre Programas FCT")
public class AlumnadoRestController {

	@Autowired
	IAlumnadoService alumnadoService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	IParamsFCTService iParamsFCTService;


	/**
	 * Creación de los Datos de Parsem Alumno Programa.
	 *
	 * @param parsemAluProgDto 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU')")
	@Operation(summary = "Creación de Parsem Alumno Programa", description = "Este metodo crea un registro en la tabla Parsem Alumno Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createParsemAluProg/{fechaIni}/{nuDias}")
	public ResponseEntity<ParsemAluProgDto> createParsemAluProg(
			@Parameter(description = "Alumno Programa", required = true) @RequestBody final ParsemAluProgDto parsemAluProgDto,
			@PathVariable("fechaIni") String fechaIni,
			@PathVariable("nuDias") Integer nuDias) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			ParsemAluProg parsemAluProgIn = modelMapper.map(parsemAluProgDto, ParsemAluProg.class);
			
			parsemAluProgIn = alumnadoService.createParsemAluProg(parsemAluProgIn,fechaIni, nuDias);
			
			ParsemAluProgDto parsemAluProgOut = modelMapper.map(parsemAluProgIn, ParsemAluProgDto.class);
			
			return new ResponseEntity<ParsemAluProgDto>(parsemAluProgOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos de Parsem Actividades Programa.
	 *
	 * @param pParsemActProgDto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU')")
	@Operation(summary = "Creación de Parsem Actividades Programa", description = "Este metodo crea Parsem Actividades Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createParsemActProg/{idParsemAluProg}")
	public ResponseEntity<List<ParsemActProgDto>> createParsemActProg(
			@Parameter(description = "Actividades Programa", required = true) @RequestBody final List<ParsemActProgDto> parsemActProgDto,
			@PathVariable("idParsemAluProg") Long idParsemAluProg) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<ParsemActProg> parsemActProgIn = parsemActProgDto.stream().map(x -> modelMapper.map(x, ParsemActProg.class)).collect(Collectors.toList());
					
			parsemActProgIn = alumnadoService.createParsemActProg(parsemActProgIn, idParsemAluProg);
			
			List<ParsemActProgDto> parsemActProgOut = parsemActProgIn.stream().map(x -> modelMapper.map(x, ParsemActProgDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ParsemActProgDto>>(parsemActProgOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Read
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Parsem Alumno Programa", description = "Este metodo devuelve el Parsem Alumno Programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getParsemAluProgByIdConvProgAlu/{idConvProgAlu}/{fechaIni}")
	public ResponseEntity<ParsemAluProgDto> getParsemAluProgByIdConvProgAlu(@PathVariable("idConvProgAlu") Long idConvProgAlu,
																			@PathVariable("fechaIni") String fechaIni){
		
		ParsemAluProg parsemAluProg = alumnadoService.getParsemAluProgByIdConvProgAlu(idConvProgAlu,fechaIni); 
		
		ParsemAluProgDto parsemAluProgDTO = modelMapper.map(parsemAluProg, ParsemAluProgDto.class);
		
		return new ResponseEntity<ParsemAluProgDto>(parsemAluProgDTO, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Parsem Alumno Programa", description = "Este metodo devuelve el Parsem Alumno Programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getParsemAluProgByIdParsemAluProg/{idParsemAluProg}")
	public ResponseEntity<ParsemAluProgDto> getParsemAluProgByIdParsemAluProg(@PathVariable("idParsemAluProg") Long idParsemAluProg){
		
		ParsemAluProg parsemAluProg = alumnadoService.getParsemAluProgByIdParsemAluProg(idParsemAluProg); 
		
		ParsemAluProgDto parsemAluProgDTO = modelMapper.map(parsemAluProg, ParsemAluProgDto.class);
		
		return new ResponseEntity<ParsemAluProgDto>(parsemAluProgDTO, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Lista de Parsem Actividades Programa", description = "Este metodo devuelve una lista de Parsem Actividades Programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllParsemActProg/{idParsemAluProg}")
	public ResponseEntity<List<ParsemActProgDto>> getAllParsemActProg( @PathVariable("idParsemAluProg") Long idParsemAluProg){
		
		List<ParsemActProg> parsemActProg =  alumnadoService.getAllParsemActProg(idParsemAluProg);
		
		List<ParsemActProgDto> parsemActProgsDto = parsemActProg.stream().map(x -> modelMapper.map(x, ParsemActProgDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ParsemActProgDto>>(parsemActProgsDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Lista de Fecha semana", description = "Este metodo devuelve una lista de Fechas semana",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechasSemana/{idConvProgAlu}")
	public ResponseEntity<List<FechaSemanaDto>> getFechasSemana( @PathVariable("idConvProgAlu") Long idConvProgAlu){
		
		List<FechaSemana> fechaSemana =  alumnadoService.getFechasSemana(idConvProgAlu);
		
		List<FechaSemanaDto> fechaSemanaDto = fechaSemana.stream().map(x -> modelMapper.map(x, FechaSemanaDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<FechaSemanaDto>>(fechaSemanaDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Parsem Alumno Programa", description = "Este metodo devuelve el Parsem Alumno Programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosAlumno/{idMatricula}")
	public ResponseEntity<AlumnoDto> getAlumno(@PathVariable("idMatricula") Long idMatricula){
		
		Alumno alumno = alumnadoService.getAlumno(idMatricula); 
		
		AlumnoDto alumnoDTO = modelMapper.map(alumno, AlumnoDto.class);
		
		return new ResponseEntity<AlumnoDto>(alumnoDTO, HttpStatus.OK);
	}		
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 * @throws ParseException 
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Listado alumnos tutor", description = "Este metodo devuelve una listado de alumnos de un tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoAlumnosTutor/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<ListadoAlumnadoTutorDto>> getListadoAlumnosTutor(@PathVariable("idTutorfctdual") Long idTutorfctdual,  
																				@PathVariable("idCentro") Long idCentro, 
																				@PathVariable("cAnno") Integer cAnno, 
																				@PathVariable("tipoEmpresa") Integer tipoEmpresa, 
																				@PathVariable("idEmpresa") Long idEmpresa, 
																				@PathVariable("idOfertamatrig") Long idOfertamatrig, 
																				@PathVariable("idUnidad") Long idUnidad,
																				@PathVariable("idPerfil") Long idPerfil,
																				@PathVariable("idCentroCombo") Long idCentroCombo,
																				@PathVariable("idProvincia") Long idProvincia,
																				@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<ListadoAlumnadoTutor> listadoAlumnadoTutor =  alumnadoService.getListadoAlumnosTutor(idTutorfctdual, 
																								  idCentro, 
																								  cAnno, 
																								  tipoEmpresa, 
																								  idEmpresa, 
																								  idOfertamatrig, 
																								  idUnidad,
																								  idPerfil,
																								  idCentroCombo,
																								  idProvincia,
																								  datosUsuario.getXUsuarioDelphos(),
																								  xEmpleadoComunica);
		
		List<ListadoAlumnadoTutorDto> listadoAlumnadoTutorDto = listadoAlumnadoTutor.stream().map(x -> modelMapper.map(x, ListadoAlumnadoTutorDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ListadoAlumnadoTutorDto>>(listadoAlumnadoTutorDto, HttpStatus.OK);
	}
	
	
	
	/**
	 * Centros.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener centros", description = "Este metodo devuelve el listado de centros", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresAlumnado/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idPerfil}/{idCentroCombo}/{idProvincia}/{sTodos}")
		public ResponseEntity<List<ElementoSelectDto>> getTutoresAlumnado(@PathVariable("idTutorfctdual") Long idTutorfctdual, 
																		  @PathVariable("idCentro") Long idCentro, 
																		  @PathVariable("cAnno") Integer cAnno, 
																		  @PathVariable("tipoEmpresa") Integer tipoEmpresa, 
																		  @PathVariable("idEmpresa") Long idEmpresa,
																		  @PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("idCentroCombo") Long idCentroCombo,
																		  @PathVariable("idProvincia") Long idProvincia,
																		  @PathVariable("sTodos") Integer sTodos,
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<ElementoSelect> tutores = alumnadoService.getTutoresAlumnado(idTutorfctdual, 
																		  idCentro, 
																		  cAnno, 
																		  tipoEmpresa, 
																		  idEmpresa,
																		  idPerfil,
																		  idCentroCombo,
																		  idProvincia,
																		  sTodos,
																		  datosUsuario.getXUsuarioDelphos(),
																		  xEmpleadoComunica
																		  ) ;
		
		List<ElementoSelectDto> tutoresDto = tutores.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(tutoresDto, HttpStatus.OK);
	}
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 * @throws ParseException 
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Listado curso empleado centro", description = "Este metodo devuelve una listado curso empleado centro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosEmpleadoCentro/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idPerfil}/{idCentroCombo}/{idProvincia}/{sTodos}")
	public ResponseEntity<List<CursoModalidadDto>> getCursosEmpleadoCentro(@PathVariable("idTutorfctdual") Long idTutorfctdual, 
																		   @PathVariable("idCentro") Long idCentro, 
																		   @PathVariable("cAnno") Integer cAnno, 
																		   @PathVariable("tipoEmpresa") Integer tipoEmpresa, 
																		   @PathVariable("idEmpresa") Long idEmpresa,
																		   @PathVariable("idPerfil") Long idPerfil,
																		   @PathVariable("idCentroCombo") Long idCentroCombo,
																		   @PathVariable("idProvincia") Long idProvincia,
																		   @PathVariable("sTodos") Integer sTodos,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<CursoModalidad> cursos =  alumnadoService.getCursosEmpleadoCentro(idTutorfctdual, 
																			   idCentro, 
																			   cAnno, 
																			   tipoEmpresa, 
																			   idEmpresa,
																			   idPerfil,
																			   idCentroCombo,
																			   idProvincia,
																			   sTodos,
																			   datosUsuario.getXUsuarioDelphos(),
																			   xEmpleadoComunica);
		
		List<CursoModalidadDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, CursoModalidadDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<CursoModalidadDto>>(cursosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 * @throws ParseException 
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Listado curso empleado centro", description = "Este metodo devuelve una listado curso empleado centro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesEmpleadoCentro/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idOfertamatrig}/{idPerfil}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<UnidadCursoDto>> getUnidadesEmpleadoCentro(@PathVariable("idTutorfctdual") Long idTutorfctdual,  
																		  @PathVariable("idCentro") Long idCentro, 
																		  @PathVariable("cAnno") Integer cAnno, 
																		  @PathVariable("tipoEmpresa") Integer tipoEmpresa, 
																		  @PathVariable("idEmpresa") Long idEmpresa, 
																		  @PathVariable("idOfertamatrig") Long idOfertamatrig,
																		  @PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("idCentroCombo") Long idCentroCombo,
																		  @PathVariable("idProvincia") Long idProvincia,
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);	
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<UnidadCurso> unidades =  alumnadoService.getUnidadesEmpleadoCentro(idTutorfctdual, 
																			    idCentro, 
																			    cAnno, 
																			    tipoEmpresa, 
																			    idEmpresa, 
																			    idOfertamatrig,
																			    idPerfil,
																			    idCentroCombo,
																			    idProvincia,
																			    datosUsuario.getXUsuarioDelphos(),
																			    xEmpleadoComunica);
		
		List<UnidadCursoDto> unidadesDto = unidades.stream().map(x -> modelMapper.map(x, UnidadCursoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<UnidadCursoDto>>(unidadesDto, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	/*------------------------------------------------------------------------------------------**/
	
	
	
	
	
	
	
	
	/**
	 * Creación de los Datos de Parsem Alumno Proyecto.
	 *
	 * @param parsemAluProyDto 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU')")
	@Operation(summary = "Creación de Parsem Alumno Proyecto", description = "Este metodo crea un registro en la tabla Parsem Alumno Proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createParsemAluProy/{fechaIni}/{nuDias}")
	public ResponseEntity<ParsemAluProyDto> createParsemAluProy(
			@Parameter(description = "Alumno Programa", required = true) @RequestBody final ParsemAluProyDto parsemAluProyDto,
			@PathVariable("fechaIni") String fechaIni,
			@PathVariable("nuDias") Integer nuDias) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			ParsemAluProy parsemAluProyIn = modelMapper.map(parsemAluProyDto, ParsemAluProy.class);
			
			parsemAluProyIn = alumnadoService.createParsemAluProy(parsemAluProyIn,fechaIni, nuDias);
			
			ParsemAluProyDto parsemAluProyOut = modelMapper.map(parsemAluProyIn, ParsemAluProyDto.class);
			
			return new ResponseEntity<ParsemAluProyDto>(parsemAluProyOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos de Parsem Actividades Proyecto.
	 *
	 * @param pParsemActProgDto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU')")
	@Operation(summary = "Creación de Parsem Actividades Proyecto", description = "Este metodo crea Parsem Actividades Proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createParsemActProy/{idParsemAluProy}")
	public ResponseEntity<List<ParsemActProyDto>> createParsemActProy(
			@Parameter(description = "Actividades Programa", required = true) @RequestBody final List<ParsemActProyDto> parsemActProyDto,
			@PathVariable("idParsemAluProy") Long idParsemAluProy) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<ParsemActProy> parsemActProyIn = parsemActProyDto.stream().map(x -> modelMapper.map(x, ParsemActProy.class)).collect(Collectors.toList());
					
			parsemActProyIn = alumnadoService.createParsemActProy(parsemActProyIn, idParsemAluProy);
			
			List<ParsemActProyDto> parsemActProyOut = parsemActProyIn.stream().map(x -> modelMapper.map(x, ParsemActProyDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ParsemActProyDto>>(parsemActProyOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Read
	
	/**
	 * Get Parsem Alumno Proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Parsem Alumno Proyecto", description = "Este metodo devuelve el Parsem Alumno Proyecto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getParsemAluProyByIdConvProyAlu/{idConvProyAlu}/{fechaIni}")
	public ResponseEntity<ParsemAluProyDto> getParsemAluProyByIdConvProyAlu(@PathVariable("idConvProyAlu") Long idConvProyAlu,
																			@PathVariable("fechaIni") String fechaIni){
		
		ParsemAluProy parsemAluProy = alumnadoService.getParsemAluProyByIdConvProyAlu(idConvProyAlu,fechaIni); 
		
		ParsemAluProyDto parsemAluProyDTO = modelMapper.map(parsemAluProy, ParsemAluProyDto.class);
		
		return new ResponseEntity<ParsemAluProyDto>(parsemAluProyDTO, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Parsem Alumno Proyecto", description = "Este metodo devuelve el Parsem Alumno Proyecto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getParsemAluProyByIdParsemAluProy/{idParsemAluProy}")
	public ResponseEntity<ParsemAluProyDto> getParsemAluProyByIdParsemAluProy(@PathVariable("idParsemAluProy") Long idParsemAluProy){
		
		ParsemAluProy parsemAluProy = alumnadoService.getParsemAluProyByIdParsemAluProy(idParsemAluProy); 
		
		ParsemAluProyDto parsemAluProyDTO = modelMapper.map(parsemAluProy, ParsemAluProyDto.class);
		
		return new ResponseEntity<ParsemAluProyDto>(parsemAluProyDTO, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Lista de Parsem Actividades Proyecto", description = "Este metodo devuelve una lista de Parsem Actividades Proyecto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllParsemActProy/{idParsemAluProy}")
	public ResponseEntity<List<ParsemActProyDto>> getAllParsemActProy( @PathVariable("idParsemAluProy") Long idParsemAluProy){
		
		List<ParsemActProy> parsemActProy =  alumnadoService.getAllParsemActProy(idParsemAluProy);
		
		List<ParsemActProyDto> parsemActProgsDto = parsemActProy.stream().map(x -> modelMapper.map(x, ParsemActProyDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ParsemActProyDto>>(parsemActProgsDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Lista de Fecha semana", description = "Este metodo devuelve una lista de Fechas semana",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechasSemanaProy/{idConvProyAlu}")
	public ResponseEntity<List<FechaSemanaDto>> getFechasSemanaProy( @PathVariable("idConvProyAlu") Long idConvProyAlu){
		
		List<FechaSemana> fechaSemana =  alumnadoService.getFechasSemanaProy(idConvProyAlu);
		
		List<FechaSemanaDto> fechaSemanaDto = fechaSemana.stream().map(x -> modelMapper.map(x, FechaSemanaDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<FechaSemanaDto>>(fechaSemanaDto, HttpStatus.OK);
	}	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Lista de evaluacion alumno datos proyecto", description = "Este metodo devuelve una lista de evaluacion alumno datos proyecto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllEvaAluActProg/{idConvProgAlu}")
	public ResponseEntity<List<EvaAluActProgDto>> getAllEvaAluActProg( @PathVariable("idConvProgAlu") Long idConvProgAlu){
		
		List<EvaAluActProg> parsemActProy =  alumnadoService.getAllEvaAluActProg(idConvProgAlu);
		
		List<EvaAluActProgDto> parsemActProgsDto = parsemActProy.stream().map(x -> modelMapper.map(x, EvaAluActProgDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<EvaAluActProgDto>>(parsemActProgsDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Lista de evaluacion alumno datos programa", description = "Este metodo devuelve una lista de evaluacion alumno datos programa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllEvaAluActProy/{idConvProyAlu}")
	public ResponseEntity<List<EvaAluActProyDto>> getAllEvaAluActProy( @PathVariable("idConvProyAlu") Long idConvProyAlu){
		
		List<EvaAluActProy> parsemActProy =  alumnadoService.getAllEvaAluActProy(idConvProyAlu);
		
		List<EvaAluActProyDto> parsemActProgsDto = parsemActProy.stream().map(x -> modelMapper.map(x, EvaAluActProyDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<EvaAluActProyDto>>(parsemActProgsDto, HttpStatus.OK);
	}
	
	/**
	 * Creación de los Datos de Parsem Alumno Programa.
	 *
	 * @param parsemAluProgDto 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Creación de Evaluacion alumno datos programa", description = "Este metodo crea un registro en la tabla Evaluacion alumno datos programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createEvaAluActProg/{idConvProgAlu}")
	public ResponseEntity<List<EvaAluActProgDto>> createEvaAluActProg(
			@Parameter(description = "Evaluacion alumno datos programa", required = true) @RequestBody final List<EvaAluActProgDto> evaAluActProgDto,
			@PathVariable("idConvProgAlu") Long idConvProgAlu) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<EvaAluActProg> evaAluActProgIn = evaAluActProgDto.stream().map(x -> modelMapper.map(x, EvaAluActProg.class)).collect(Collectors.toList());
			
			evaAluActProgIn = alumnadoService.createEvaAluActProg(evaAluActProgIn, idConvProgAlu);
			
			List<EvaAluActProgDto> evaAluActProgOut = evaAluActProgIn.stream().map(x -> modelMapper.map(x, EvaAluActProgDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<EvaAluActProgDto>>(evaAluActProgOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos de Parsem Alumno Proyecto.
	 *
	 * @param parsemAluProgDto 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@Operation(summary = "Creación de Evaluacion alumno datos Proyecto", description = "Este metodo crea un registro en la tabla Evaluacion alumno datos Proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createEvaAluActProy/{idConvProyAlu}")
	public ResponseEntity<List<EvaAluActProyDto>> createEvaAluActProy(
			@Parameter(description = "Evaluacion alumno datos proyecto", required = true) @RequestBody final List<EvaAluActProyDto> evaAluActProyDto,
			@PathVariable("idConvProyAlu") Long idConvProyAlu) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<EvaAluActProy> evaAluActProyIn = evaAluActProyDto.stream().map(x -> modelMapper.map(x, EvaAluActProy.class)).collect(Collectors.toList());
			
			evaAluActProyIn = alumnadoService.createEvaAluActProy(evaAluActProyIn, idConvProyAlu);
			
			List<EvaAluActProyDto> evaAluActProyOut = evaAluActProyIn.stream().map(x -> modelMapper.map(x, EvaAluActProyDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<EvaAluActProyDto>>(evaAluActProyOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Lista de evaluacion alumno apto", description = "Este metodo devuelve una lista de evaluacion alumno apto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAptoEvaluacionAlumno")
	public ResponseEntity<List<AptoEvaluacionAlumnoDto>> getAptoEvaluacionAlumno(){
		
		List<AptoEvaluacionAlumno> aptoEvaluacionAlumno =  alumnadoService.getAptoEvaluacionAlumno();
		
		List<AptoEvaluacionAlumnoDto> aptoEvaluacionAlumnoDto = aptoEvaluacionAlumno.stream().map(x -> modelMapper.map(x, AptoEvaluacionAlumnoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<AptoEvaluacionAlumnoDto>>(aptoEvaluacionAlumnoDto, HttpStatus.OK);
	}
	
	
	//GetEvaluacionById Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@GetMapping("/getEvaluacionpdf/{id}/{tipo}")
	public void generateReport(HttpServletResponse response, @PathVariable Long id,
							   @PathVariable String tipo) throws JRException, IOException {
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=" + "Evaluacion" +"."+ "pdf");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setStatus(200);	    
	    
	    byte [] documento = alumnadoService.exportReport(id,tipo);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	//GetParteSemanalById Jasper PDF
		//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
		@GetMapping("/getParteSemanalpdf/{id}/{tipo}/{idParsem}")
		public void generateParteSemanalReport(HttpServletResponse response, @PathVariable Long id,@PathVariable Long idParsem,				
								   @PathVariable String tipo) throws JRException, IOException {
			response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; filename=" + "Evaluacion" +"."+ "pdf");
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setStatus(200);	    
		    
		    byte [] documento = alumnadoService.exportParteSemanalReport(id,tipo,idParsem);
		    
		    InputStream is = new ByteArrayInputStream(documento);
		    FileCopyUtils.copy(is, response.getOutputStream());
		    response.flushBuffer();
	       
	    }
		
		/**
		 * Datos de un Convenio.
		 *
		 * @param id Id del convenio
		 * @return the response entity
		 */
		//@PreAuthorize("hasAnyRole('P','PRO','C')")
		@Operation(summary = "Actualizar la fecha de firma", description = "Este metodo actualiza la fecha de firma de un convenio", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@PostMapping("/updateFechaFirmaEva/{idEntidad}/{entidad}")
		public ResponseEntity<Boolean> firmarEvaluacionAlumno(@Parameter(description = "Identificador del alumno/programa o alumno/proyecto", required = true) 
																		@PathVariable("idEntidad") Long idEntidad,
															  @Parameter(description = "Entidad Programa o Proyecto", required = true) 
																		@PathVariable("entidad") String entidad) 
		{
			
			
			return new ResponseEntity<Boolean>(alumnadoService.firmarEvaluacionAlumno(idEntidad,entidad), HttpStatus.OK);
			

		}
		
		/**
		 * Lista datos.
		 *
		 * @return the response entity
		 */
		@Operation(summary = "Lista de evaluacion alumno datos programa", description = "Este metodo devuelve una lista de evaluacion alumno datos programa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getAlumnadoNSS/{cAnno}")
		public ResponseEntity<List<AlumnadoNSSDto>> getAlumnadoNSS( @PathVariable("cAnno") Integer cAnno){
			
			List<AlumnadoNSS> alumnado =  alumnadoService.getAlumnadoNSS(cAnno);
			
			List<AlumnadoNSSDto> alumnadoDto = alumnado.stream().map(x -> modelMapper.map(x, AlumnadoNSSDto.class)).collect(Collectors.toList());
					
			return new ResponseEntity<List<AlumnadoNSSDto>>(alumnadoDto, HttpStatus.OK);
		}
		/**
		 * Listado  de altas y bajas del alumnado
		 * @param cAnno
		 * @return
		 */
		@Operation(summary = "Altas y bajas del alumnado", description = "Este metodo devuelve una lista de altas y bajas del Alumnado",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
			@GetMapping("/getAlumnadoAltasBajas/{cAnno}")
			public ResponseEntity<List<AlumnadoAltasBajasDto>> getAlumnadoAltasBajas( @PathVariable("cAnno") Integer cAnno){
																			  
				
				List<AlumnadoAltasBajas> alumnado =  alumnadoService.getAlumnadoAltasBajas(cAnno);
				
				List<AlumnadoAltasBajasDto> alumnadoAltasBajasDto = alumnado.stream().map(x -> modelMapper.map(x, AlumnadoAltasBajasDto.class)).collect(Collectors.toList());
						
				return new ResponseEntity<List<AlumnadoAltasBajasDto>>(alumnadoAltasBajasDto, HttpStatus.OK);
			}
		
		/**
		 * Listado de alumnos de Erasmus
		 * @param cAnno
		 * @return
		 */
		@Operation(summary = "Lista de alumnado de Erasmus", description = "Este metodo devuelve una lista de alumnado de Erasmus",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
			@GetMapping("/getAlumnadoAltas/{cAnno}/{idCentro}")
			public ResponseEntity<List<AlumnadoAltasDto>> getAlumnadoErasmus(@PathVariable("cAnno") Integer cAnno,
																			   @PathVariable("idCentro") Long idCentro) {
																			      

				List<AlumnadoAltas> alumnadoErasmus =  alumnadoService.getAlumnadoAltas(cAnno,idCentro);
				
				List<AlumnadoAltasDto> alumnadoErasmusDto = alumnadoErasmus.stream().map(x -> modelMapper.map(x, AlumnadoAltasDto.class)).collect(Collectors.toList());
						
				return new ResponseEntity<List<AlumnadoAltasDto>>(alumnadoErasmusDto, HttpStatus.OK);
			}
		
		/**
		 * Listado de comunicación de realización de prácticas.
		 * @param cAnno
		 * @return
		 */
		@Operation(summary = "Lista de comunicación de realización de prácticas", description = "Este metodo devuelve un listado de comunicación de realización de prácticas",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
			@GetMapping("/getComunicacionDiasPracticas/{cAnno}/{idCentro}/{nMes}")
			public ResponseEntity<List<ComunicacionDiasPracticasDto>> getComunicacionDiasPracticas(@PathVariable("cAnno") Integer cAnno,
																							       @PathVariable("idCentro") Long idCentro,
																							       @PathVariable("idCentro") Integer nMes) {
																			      

				List<ComunicacionDiasPracticas> comuDiasPrac =  alumnadoService.getComunicacionDiasPracticas(cAnno,idCentro,nMes);
				
				List<ComunicacionDiasPracticasDto> comuDiasPracDto = comuDiasPrac.stream().map(x -> modelMapper.map(x, ComunicacionDiasPracticasDto.class)).collect(Collectors.toList());
						
				return new ResponseEntity<List<ComunicacionDiasPracticasDto>>(comuDiasPracDto, HttpStatus.OK);
			}
		
		/**
		 * Get Parsem Alumno Programa.
		 *
		 * @return the response entity
		 */
		@Operation(summary = "Numero programas/proyectos alumnos", description = "Este metodo devuelve el numero de programas o proyectos de un alumno",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getNumeroProgProy/{cAnno}/{xCentro}")
		public ResponseEntity<Integer> getNumeroProgProy(@PathVariable("cAnno") Integer cAnno,
														 @PathVariable("xCentro") Long xCentro,
														 @RequestHeader(Constants.AUTHORIZATION) String jwt){
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
			
			Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
			
			Integer nAlumnos = alumnadoService.getNumeroProgProy(cAnno,xCentro,xEmpleadoComunica);
			
			return new ResponseEntity<Integer>(nAlumnos, HttpStatus.OK);
		}


	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Delete partes semanales", description = "Este metodo elimina los partes semanales de un alumno de FCT y DUAL",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/deleteAlumParteSemanal")
	public ResponseEntity<Object> deleteAlumParteSemanal(@RequestParam("id") Long idConvProAlu,
														 @RequestParam("tipo") String tipo) {
		try {
			alumnadoService.deleteAlumnoParteSemanal(idConvProAlu, tipo);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 *
	 * @param idAluCon
	 * @param tipo
	 * @param nuHorasEva
	 * @return
	 */
	//	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Update horas evaluación", description = "Actualiza el campo de horas de evaluación del alumno.",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PutMapping("/updateNuHorasEva/{idAluCon}/{tipo}/{nuHorasEva}")
	public ResponseEntity<Void> updateNuHorasEval(@PathVariable ("idAluCon") Long idAluCon,
												  	   @PathVariable ("tipo") String tipo,
													   @PathVariable ("nuHorasEva") Integer nuHorasEva) {

		try {

			alumnadoService.updateNuHorasEval(idAluCon,tipo,nuHorasEva);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
