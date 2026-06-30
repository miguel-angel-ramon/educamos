package es.jccm.edu.proyectosfct.adapter.in.rest.gastos;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoAlumnadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoAlumnadoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoAnexoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoAnexoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoEstadoFlujoSiguienteDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoTutorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.GastoTutorHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.HistoricoFlujoGastosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.ImpuestoTipoServicioDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.ListadoGastoAlumnadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.ListadoGastoAnexoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.ListadoGastoTutoresDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.PeriodoGastoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketAlumnadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketTutorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnadoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoEstadoFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutor;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutorHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.HistoricoFlujoGastos;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ImpuestoTipoServicio;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoTutores;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketTutor;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.gastos.IGastosService;
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
@Tag(name = "Servicio Gastos FCT", description = "Servicio con las operaciones sobre Gastos FCT")
public class GastosRestController {
	
	private static final String NO_CACHE = "no-cache";
	
	private static final Long ID_PERFIL_ALUMNADO = 5000L;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	IGastosService gastosService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	IParamsFCTService iParamsFCTService;
	
	/**
	 * Creación del ticket de un gasto del tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación del ticket de un gasto del tutor", description = "Este metodo crea el ticket de un gasto del tutor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createTicketTutor/{idGasto}/{cAnno}/{idCentro}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> createTicketTutor(@PathVariable("idGasto") Long idGasto,
													@PathVariable("cAnno") Long cAnno,
													@PathVariable("idCentro") Long idCentro,
													@RequestPart List<MultipartFile> files,
													@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<TicketTutor> ListTicketTutorIn = gastosService.createTicketTutor(idGasto,
																				  cAnno,
																				  idCentro,
																				  files, 
																				  datosUsuario.getXUsuarioDelphos());
			
			List<TicketTutorDto> ListTicketTutorOut = ListTicketTutorIn.stream().map(x->modelMapper.map(x, TicketTutorDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(ListTicketTutorOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete ticket de un gasto del tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Delete del ticket de un gasto del tutor", description = "Este metodo borra el ticket de un gasto del tutor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteTicketTutor")
	public ResponseEntity<Object> deleteTicketTutor(
			@Parameter(description = "Documento para subir a Rodal", required = true) @RequestBody final List<TicketTutorDto> listTicketsTutorDto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			gastosService.deleteTicketTutor(listTicketsTutorDto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación del ticket de un gasto del alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación del ticket de un gasto del alumno", description = "Este metodo crea el ticket de un gasto del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createTicketAlumnado/{idGasto}/{cAnno}/{idCentro}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> createTicketAlumnado(@PathVariable("idGasto") Long idGasto,
													   @PathVariable("cAnno") Long cAnno,
													   @PathVariable("idCentro") Long idCentro,
													   @RequestPart List<MultipartFile> files,
													   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
			
			List<TicketAlumnado> ListTicketAlumnoIn = gastosService.createTicketAlumno(idGasto,
																					   cAnno,
																					   idCentro,
																					   files, 
																					   xUsuarioComunica);
			
			List<TicketAlumnadoDto> ListTicketAlumnoOut = ListTicketAlumnoIn.stream().map(x->modelMapper.map(x, TicketAlumnadoDto.class)).collect(Collectors.toList());;
			
			return new ResponseEntity<>(ListTicketAlumnoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete del ticket de un gasto del alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Delete del ticket de un gasto del alumno", description = "Este metodo borra el ticket de un gasto del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteTicketAlumnado")
	public ResponseEntity<Object> deleteTicketAlumnado(
			@Parameter(description = "Documento para subir a Rodal", required = true) @RequestBody final List<TicketAlumnadoDto> listTicketAlumnadoDto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			gastosService.deleteTicketAlumno(listTicketAlumnadoDto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Generar Anexo VIII.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Generar Anexo VIII", description = "Este metodo generar Anexo VIII", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/generarAnexoVIII/{idPerfil}/{idCentro}/{idPeriodoGasto}/{abrevTipoGasto}/{annoAnexo}")
	public ResponseEntity<Object> generarAnexoVIII(
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idPerfil") Long idPerfil,
			@PathVariable("idCentro") Long idCentro,
			@PathVariable("idPeriodoGasto") Long idPeriodoGasto,
			@PathVariable("abrevTipoGasto") String abrevTipoGasto,
			@PathVariable("annoAnexo") Integer annoAnexo) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			gastosService.generarAnexoVIII(idPerfil, idCentro, datosUsuario.getXUsuarioDelphos(), idPeriodoGasto, abrevTipoGasto, annoAnexo);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Generar Anexo VIII.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Generar Anexo VIII", description = "Este metodo generar Anexo VIII", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/generarAnexosVI/{idPerfil}/{idCentro}/{idPeriodoGasto}/{abrevTipoGasto}/{annoAnexo}/{idTutor}/{idCurso}/{idUnidad}")
	public ResponseEntity<Object> generarAnexos(
												@RequestHeader(Constants.AUTHORIZATION) String jwt,
												@PathVariable("idPerfil") Long idPerfil,
												@PathVariable("idCentro") Long idCentro,
												@PathVariable("idPeriodoGasto") Long idPeriodoGasto,
												@PathVariable("abrevTipoGasto") String abrevTipoGasto,
												@PathVariable("annoAnexo") Integer annoAnexo,
												@PathVariable("idTutor") Long idTutor,
												@PathVariable("idCurso") Long idCurso,
												@PathVariable("idUnidad") Long idUnidad) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			gastosService.generarAnexosVI(idPerfil, 
										  idCentro, 
										  datosUsuario.getXUsuarioDelphos(), 
										  idPeriodoGasto, 
										  abrevTipoGasto, 
										  annoAnexo,
										  idTutor,
										  idCurso,
										  idUnidad);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	

	/**
	 * Creación del siguiente estado del flujo tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación del siguiente estado del flujo", description = "Este metodo crea el siguiente estado del flujo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createSiguienteEstadoFlujoTutor/{idPerfil}/{abrevTipoGasto}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoTutor(
			@Parameter(description = "Gastos de un tutor", required = true) @RequestBody final GastoTutorHistorialDto gastoTutorHistorialDto,
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idPerfil") Long idPerfil,
			@PathVariable("abrevTipoGasto") String abrevTipoGasto) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);			
			
			GastoTutorHistorial gastoTutorHistorialIn = modelMapper.map(gastoTutorHistorialDto, GastoTutorHistorial.class);
			
			gastoTutorHistorialIn = gastosService.createSiguienteEstadoFlujoTutor(datosUsuario.getXUsuarioDelphos(), gastoTutorHistorialIn, idPerfil, abrevTipoGasto);
			
			GastoTutorHistorialDto gastoTutorHistorialOut = modelMapper.map(gastoTutorHistorialIn, GastoTutorHistorialDto.class);
			
			return new ResponseEntity<>(gastoTutorHistorialOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación del siguiente estado del flujo alumnado.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación del siguiente estado del flujo", description = "Este metodo crea el siguiente estado del flujo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createSiguienteEstadoFlujoAlumnado/{idPerfil}/{abrevTipoGasto}/{idTutor}/{idCurso}/{idUnidad}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoAlumnado(
			@Parameter(description = "Gastos de un alumno/a", required = true) @RequestBody final GastoAlumnadoHistorialDto gastoAlumnadoHistorialDto,
													@RequestHeader(Constants.AUTHORIZATION) String jwt,
													@PathVariable("idPerfil") Long idPerfil,
													@PathVariable("abrevTipoGasto") String abrevTipoGasto,
													@PathVariable("idTutor") Long idTutor,
													@PathVariable("idCurso") Long idCurso,
													@PathVariable("idUnidad") Long idUnidad) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);			
			
			GastoAlumnadoHistorial gastoAlumnadoHistorialIn = modelMapper.map(gastoAlumnadoHistorialDto, GastoAlumnadoHistorial.class);
			
			Long idUsuario = datosUsuario.getXUsuarioDelphos();
			
			if (idPerfil.equals(ID_PERFIL_ALUMNADO)) {				
				idUsuario = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
			} 		
			
			gastoAlumnadoHistorialIn = gastosService.createSiguienteEstadoFlujoAlumnado(idUsuario, 
																						gastoAlumnadoHistorialIn, 
																						idPerfil, 
																						abrevTipoGasto,
																						idTutor,
																						idCurso,
																						idUnidad);
			
			GastoAlumnadoHistorialDto gastoAlumnadoHistorialOut = modelMapper.map(gastoAlumnadoHistorialIn, GastoAlumnadoHistorialDto.class);
			
			return new ResponseEntity<>(gastoAlumnadoHistorialOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos para el gasto de un tutor.
	 *
	 * @param GastoTutorDto Datos de gasto tutor
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación de gastos del tutor", description = "Este metodo crea el gasto de un tutor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createGastoTutor/{idPerfil}")
	public ResponseEntity<Object> createGastoTutor(
			@Parameter(description = "Gastos de un tutor", required = true) @RequestBody final GastoTutorDto gastoTutorDto,
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idPerfil") Long idPerfil) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			GastoTutor gastoTutorIn = modelMapper.map(gastoTutorDto, GastoTutor.class);
			
			gastoTutorIn = gastosService.createGastoTutor(datosUsuario.getXUsuarioDelphos(), idPerfil, gastoTutorIn);
			
			GastoTutorDto gastoTutorOut = modelMapper.map(gastoTutorIn, GastoTutorDto.class);
			
			return new ResponseEntity<>(gastoTutorOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos para el gasto de un alumno.
	 *
	 * @param GastoTutorDto Datos de gasto alumno
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación de gastos del alumno", description = "Este metodo crea el gasto de un alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createGastoAlumnado/{idPerfil}")
	public ResponseEntity<Object> createGastoAlumnado(
			@Parameter(description = "Gastos de un alumno", required = true) @RequestBody final GastoAlumnadoDto gastoAlumnoDto,
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idPerfil") Long idPerfil) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			GastoAlumnado gastoAlumnoIn = modelMapper.map(gastoAlumnoDto, GastoAlumnado.class);

			Long xUsuario = idPerfil==2?datosUsuario.getXUsuarioDelphos():iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());

			gastoAlumnoIn = gastosService.createGastoAlumnado(xUsuario, idPerfil, gastoAlumnoIn);
			
			GastoAlumnadoDto gastoAlumnoOut = modelMapper.map(gastoAlumnoIn, GastoAlumnadoDto.class);
			
			return new ResponseEntity<>(gastoAlumnoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Creación de los Datos para el gasto de un anexo.
	 *
	 * @param GastoTutorDto Datos de gasto anexo
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Creación de gastos del anexo", description = "Este metodo crea el gasto de un anexo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createGastoAnexo")
	public ResponseEntity<Object> createGastoAnexo(
			@Parameter(description = "Gastos de un anexo", required = true) @RequestBody final GastoAnexoDto gastoAnexoDto,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			GastoAnexo gastoAnexoIn = modelMapper.map(gastoAnexoDto, GastoAnexo.class);
			
			gastoAnexoIn = gastosService.createGastoAnexo(datosUsuario.getXUsuarioDelphos(), gastoAnexoIn);
			
			GastoAnexoDto gastoAnexoOut = modelMapper.map(gastoAnexoIn, GastoAnexoDto.class);
			
			return new ResponseEntity<>(gastoAnexoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Lista siguiente estado flujo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista siguiente estado", description = "Este metodo devuelve una lista siguiente estado",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSiguienteGastoFlujo/{idPerfil}/{idGasto}/{abrevTipoGasto}")
	public ResponseEntity<List<GastoEstadoFlujoSiguienteDto>> getSiguienteGastoFlujo(@PathVariable("idPerfil") Long idPerfil, @PathVariable("idGasto") Long idGasto, 
																					 @PathVariable("abrevTipoGasto") String abrevTipoGasto){
		
		List<GastoEstadoFlujoSiguiente> listadoEstadoSiguienteIn =  gastosService.getSiguienteGastoFlujo(idPerfil, idGasto, abrevTipoGasto);
		
		List<GastoEstadoFlujoSiguienteDto> listadoEstadoSiguienteOut = listadoEstadoSiguienteIn.stream().map(x -> modelMapper.map(x, GastoEstadoFlujoSiguienteDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoEstadoSiguienteOut, HttpStatus.OK);
	}
	
	/**
	 * Lista gasto tutores.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista gasto tutores", description = "Este metodo devuelve una lista gasto tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoGastoTutores/{annoPeriodo}/{idCentro}/{idTutor}/{idPeriodoGasto}/{idPerfil}/{idEstado}")
	public ResponseEntity<List<ListadoGastoTutoresDto>> getListadoGastoTutores(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																			   @PathVariable("idCentro") Long idCentro, 
																			   @PathVariable("idTutor") Long idTutor, 
																			   @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																			   @PathVariable("idPerfil") Long idPerfil,
																			   @PathVariable("idEstado") Long idEstado){
		
		List<ListadoGastoTutores> listadoGastoTutoresIn =  gastosService.getListadoGastoTutores(annoPeriodo, 
																						        idCentro, 
																						        idTutor, 
																						        idPeriodoGasto,
																						        idPerfil,
																						        idEstado);
		
		List<ListadoGastoTutoresDto> listadoGastoTutoresOut = listadoGastoTutoresIn.stream().map(x -> modelMapper.map(x, ListadoGastoTutoresDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoGastoTutoresOut, HttpStatus.OK);
	}
	
	/**
	 * Lista gasto tutores.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista gasto tutores", description = "Este metodo devuelve una lista gasto tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getComboTutores/{annoPeriodo}/{idCentro}")
	public ResponseEntity<List<ListadoGastoTutoresDto>> getComboTutores(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																			   @PathVariable("idCentro") Long idCentro,
																			   @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ListadoGastoTutores> listadoGastoTutoresIn =  gastosService.getComboTutores(annoPeriodo, 
																					     idCentro,
																					     datosUsuario.getXUsuarioDelphos());
		
		List<ListadoGastoTutoresDto> listadoGastoTutoresOut = listadoGastoTutoresIn.stream().map(x -> modelMapper.map(x, ListadoGastoTutoresDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoGastoTutoresOut, HttpStatus.OK);
	}
	
	/**
	 * Lista gasto alumnado.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista gasto tutores", description = "Este metodo devuelve una lista gasto tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoGastoAlumnado/{annoPeriodo}/{idCentro}/{idTutor}/{idPeriodoGasto}/{idPerfil}/{idCurso}/{idUnidad}")
	public ResponseEntity<List<ListadoGastoAlumnadoDto>> getListadoGastoAlumnado(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																				 @PathVariable("idCentro") Long idCentro, 
																				 @PathVariable("idTutor") Long idTutor, 
																				 @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																				 @PathVariable("idPerfil") Long idPerfil,
																				 @PathVariable("idCurso") Long idCurso,
																				 @PathVariable("idUnidad") Long idUnidad,
																				 @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		
		List<ListadoGastoAlumnado> listadoGastoAlumnadoIn =  gastosService.getListadoGastoAlumnado(annoPeriodo, 
																								   idCentro, 
																								   idTutor, 
																								   idPeriodoGasto,
																								   idPerfil,
																								   idCurso,
																								   idUnidad,
																								   xUsuarioComunica);
		
		List<ListadoGastoAlumnadoDto> listadoGastoAlumnadoOut = listadoGastoAlumnadoIn.stream().map(x -> modelMapper.map(x, ListadoGastoAlumnadoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoGastoAlumnadoOut, HttpStatus.OK);
	}
	
	/**
	 * Lista gasto anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista gasto anexo", description = "Este metodo devuelve una lista gasto anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoGastoAnexo/{annoPeriodo}/{idCentro}/{abrevTipoGasto}/{idPeriodoGasto}/{idTutor}/{idCurso}/{idUnidad}/{idPerfil}/{idProvinciaDelegacion}")
	public ResponseEntity<List<ListadoGastoAnexoDto>> getListadoGastoAnexo(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																		   @PathVariable("idCentro") Long idCentro, 
																		   @PathVariable("abrevTipoGasto") String abrevTipoGasto, 
																		   @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																		   @PathVariable("idTutor") Long idTutor,
																		   @PathVariable("idCurso") Long idCurso,
																		   @PathVariable("idUnidad") Long idUnidad,
																		   @PathVariable("idPerfil") Long idPerfil,
																		   @PathVariable("idProvinciaDelegacion") Long idProvinciaDelegacion,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt){

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ListadoGastoAnexo> listadoGastoAnexoIn =  gastosService.getListadoGastoAnexo(annoPeriodo, 
																						  idCentro, 
																						  abrevTipoGasto, 
																						  idPeriodoGasto,
																						  idTutor,	
																						  idCurso,
																						  idUnidad,
																						  idPerfil,
																						  idProvinciaDelegacion,
																						  datosUsuario.getXUsuarioDelphos());
		
		List<ListadoGastoAnexoDto> listadoGastoAnexoOut = listadoGastoAnexoIn.stream().map(x -> modelMapper.map(x, ListadoGastoAnexoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoGastoAnexoOut, HttpStatus.OK);
	}
	
	/**
	 * Lista Periodo gasto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista Periodo gasto", description = "Este metodo devuelve una Lista de periodos gasto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPeriodosGasto/{annoPeriodo}/{idCentro}/{idPerfil}/{idTutor}")
	public ResponseEntity<List<PeriodoGastoDto>> getPeriodosGasto(@PathVariable("annoPeriodo") Integer annoPeriodo,
																  @PathVariable("idCentro") Long idCentro,
																  @PathVariable("idPerfil") Long idPerfil,
																  @PathVariable("idTutor") Long idTutor,
																  @RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt); 
		
		Long idUsuarioComunica =1L;
		
		if (idPerfil.equals(ID_PERFIL_ALUMNADO)) {				
			idUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		}
		
		List<PeriodoGasto> listaPeriodosIn =  gastosService.getPeriodosGasto(annoPeriodo,idCentro,idPerfil,idTutor,idUsuarioComunica);
		
		List<PeriodoGastoDto> listaPeriodosOut = listaPeriodosIn.stream().map(x -> modelMapper.map(x, PeriodoGastoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listaPeriodosOut, HttpStatus.OK);
	}
	
	/**
	 * Gasto tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Gasto tutor", description = "Este metodo devuelve un Gasto tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGastoTutorById/{idGastoTutor}")
	public ResponseEntity<GastoTutorDto> getGastoTutorById(@PathVariable("idGastoTutor") Long idGastoTutor){
		
		GastoTutor gastoTutor =  gastosService.getGastoTutor(idGastoTutor);
		GastoTutorDto gastoTutorOut = modelMapper.map(gastoTutor, GastoTutorDto.class);
				
		return new ResponseEntity<>(gastoTutorOut, HttpStatus.OK);
	}	
	
	/**
	 * Gasto Alumnado.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Gasto Alumnado", description = "Este metodo devuelve un Gasto alumno",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGastoAlumnadoById/{idGastoAlumnado}")
	public ResponseEntity<GastoAlumnadoDto> getGastoAlumnadoById(@PathVariable("idGastoAlumnado") Long idGastoAlumnado){		
		
		GastoAlumnado gastoAlumnado =  gastosService.getGastoAlumnado(idGastoAlumnado);
		GastoAlumnadoDto gastoAlumnadoOut = modelMapper.map(gastoAlumnado, GastoAlumnadoDto.class);
				
		return new ResponseEntity<>(gastoAlumnadoOut, HttpStatus.OK);
	}	
	
	/**
	 * Periodo gasto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Periodo gasto", description = "Este metodo devuelve un Periodo gasto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPeriodoGastoById/{idGastoPeriodo}")
	public ResponseEntity<PeriodoGastoDto> getPeriodoGastoById(@PathVariable("idGastoPeriodo") Long idGastoPeriodo){
		
		PeriodoGasto gastoPeriodo =  gastosService.getPeriodoGastoById(idGastoPeriodo);
		PeriodoGastoDto gastoPeriodoOut = modelMapper.map(gastoPeriodo, PeriodoGastoDto.class);
				
		return new ResponseEntity<>(gastoPeriodoOut, HttpStatus.OK);
	}	
	
	/**
	 * Historico gastos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista histórico gasto", description = "Este metodo devuelve una Lista de historico de un gasto tutor/alumnado y anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getHistoricoFlujoGastos/{id}/{abrevTipoGasto}")
	public ResponseEntity<List<HistoricoFlujoGastosDto>> getHistoricoFlujoGastos(@PathVariable("id") Long id,
																		         @PathVariable("abrevTipoGasto") String abrevTipoGasto){
		
		List<HistoricoFlujoGastos> historicoIn =  gastosService.getHistoricoFlujoGastos(id, abrevTipoGasto);
		
		List<HistoricoFlujoGastosDto> historicoOut = historicoIn.stream().map(x -> modelMapper.map(x, HistoricoFlujoGastosDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(historicoOut, HttpStatus.OK);
	}
	
	
	//Get anexoI Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@GetMapping("/downloadAnexoGastoTipo/{cAnno}/{idCentro}/{idPeriodo}/{idTipo}/{idTutor}/{idCurso}/{idUnidad}")
	public void generateReportAnexoGastos(HttpServletResponse response, 
										  @PathVariable("cAnno") Integer cAnno,
										  @PathVariable("idCentro") Long idCentro,
									      @PathVariable("idPeriodo") Long idPeriodo, 
									      @PathVariable("idTipo") Long idTipo,
									      @PathVariable("idTutor") Long idTutor,
									      @PathVariable("idCurso") Long idCurso,
									      @PathVariable("idUnidad") Long idUnidad) throws JRException, IOException {
		response.setContentType("application/octet-stream");
		
		if (idTutor != -1L) 
		{
			response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoVI" +"."+ "pdf");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoVI-BIS" +"."+ "pdf");
		}
	    
	    response.setHeader("Pragma", NO_CACHE);
	    response.setHeader("Cache-Control", NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = gastosService.exportReportAnexoGastoTipo(cAnno, 
	    															 idCentro, 
	    															 idPeriodo, 
	    															 idTipo, 
	    															 idTutor,
	    															 idCurso,
	    															 idCurso,
	    															 -1L,-1L,-1L);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getComboCursosGastos/{annoPeriodo}/{idCentro}/{abrevTipoGasto}/{idPeriodoGasto}/{idTutor}/{idPerfil}")
	public ResponseEntity<List<ElementoSelectDto>> getComboCursosGastos(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																		@PathVariable("idCentro") Long idCentro, 
																		@PathVariable("abrevTipoGasto") String abrevTipoGasto, 
																		@PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																		@PathVariable("idTutor") Long idTutor,
																		@PathVariable("idPerfil") Long idPerfil,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long idUsuario = datosUsuario.getXUsuarioDelphos();
		
		if (idPerfil.equals(ID_PERFIL_ALUMNADO)) {				
			idUsuario = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		} 
		
		List<ElementoSelect> cursos =  gastosService.getComboCursosGastos(annoPeriodo, 
																		  idCentro, 
																		  abrevTipoGasto, 
																		  idPeriodoGasto,
																		  idTutor,
																		  idUsuario,
																		  idPerfil);
		
		List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(cursosDto, HttpStatus.OK);
	}
	
	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista modalidad anexo", description = "Este metodo devuelve una lista de modalidades anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getComboUnidadGastos/{annoPeriodo}/{idCentro}/{abrevTipoGasto}/{idPeriodoGasto}/{idTutor}/{idCurso}/{idPerfil}")
	public ResponseEntity<List<ElementoSelectDto>> getComboUnidadGastos(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																		   @PathVariable("idCentro") Long idCentro, 
																		   @PathVariable("abrevTipoGasto") String abrevTipoGasto, 
																		   @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																		   @PathVariable("idTutor") Long idTutor,
																		   @PathVariable("idCurso") Long idCurso,
																		   @PathVariable("idPerfil") Long idPerfil,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long idUsuario = datosUsuario.getXUsuarioDelphos();
		
		if (idPerfil.equals(ID_PERFIL_ALUMNADO)) {				
			idUsuario = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		} 
		
		List<ElementoSelect> modalidades =  gastosService.getComboUnidadGastos(annoPeriodo, 
																		          idCentro, 
																		          abrevTipoGasto, 
																		          idPeriodoGasto,
																		          idTutor,
																		          idCurso,
																		          idUsuario,
																		          idPerfil);
		
		List<ElementoSelectDto> modalidadesDto = modalidades.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(modalidadesDto, HttpStatus.OK);
	}
	
	/**
	 * Combos familias anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista familias anexo", description = "Este metodo devuelve una lista de familias anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getComboTutoresGastosAlumno/{annoPeriodo}/{idCentro}/{abrevTipoGasto}/{idPeriodoGasto}/{idPerfil}")
	public ResponseEntity<List<ElementoSelectDto>> getComboTutoresGastosAlumno(@PathVariable("annoPeriodo") Integer annoPeriodo, 
																		       @PathVariable("idCentro") Long idCentro, 
																		       @PathVariable("abrevTipoGasto") String abrevTipoGasto, 
																		       @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
																		       @PathVariable("idPerfil") Long idPerfil,
																		       @RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long idUsuario = datosUsuario.getXUsuarioDelphos();
		
		if (idPerfil.equals(ID_PERFIL_ALUMNADO)) {				
			idUsuario = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		} 
		
		List<ElementoSelect> tutoresDto =  gastosService.getComboTutoresGastosAlumno(annoPeriodo, 
																				     idCentro, 
																				     abrevTipoGasto, 
																				     idPeriodoGasto,
																				     idUsuario,
																				     idPerfil);
		
		List<ElementoSelectDto> tutores = tutoresDto.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(tutores, HttpStatus.OK);
	}
	
	
	/**
	 * Actualización firma
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT')")
	@Operation(summary = "Actualización fecha firma", description = "Nos permite actualizar la fecha de firma y el xUsuario de los anexos, posiciones permitidas 1 y 2",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateFirmaAnexo/{idHistorial}/{posFirma}/{idPerfil}")
	public ResponseEntity<Boolean> updateFirmaAnexo(@PathVariable("idHistorial") Long idHistorial, 
													@PathVariable("posFirma") Integer posFirma,
													@PathVariable("idPerfil") Long idPerfil,
													@RequestHeader(Constants.AUTHORIZATION) String jwt){
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			gastosService.updateFirmaAnexo(idHistorial, 
										   datosUsuario.getXUsuarioDelphos(), 
										   posFirma,
										   idPerfil);
			
			return new ResponseEntity<>(true, HttpStatus.OK);

		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/**
	 * Lista tickets tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista tickets tutor", description = "Este metodo devuelve una lista de tickets de un tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })

	@GetMapping("/getTicketsTutor/{idGastoTutor}")
	public ResponseEntity<List<TicketTutorDto>> getTicketsTutor(@PathVariable("idGastoTutor") Long idGastoTutor){
		
		List<TicketTutor> listadoTicketsTutorIn =  gastosService.getTicketsTutor(idGastoTutor);
		
		List<TicketTutorDto> listadoTicketsTutorOut = listadoTicketsTutorIn.stream().map(x -> modelMapper.map(x, TicketTutorDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoTicketsTutorOut, HttpStatus.OK);
	}
	
	/**
	 * Lista tickets alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista tickets alumno", description = "Este metodo devuelve una lista de tickets de un alumno",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTicketsAlumno/{idGastoAlumnado}")
	public ResponseEntity<List<TicketAlumnadoDto>> getTicketsAlumno(@PathVariable("idGastoAlumnado") Long idGastoAlumnado){
		
		List<TicketAlumnado> listadoTicketsAlumnoIn =  gastosService.getTicketsAlumno(idGastoAlumnado);
		
		List<TicketAlumnadoDto> listadoTicketsAlumnoOut = listadoTicketsAlumnoIn.stream().map(x -> modelMapper.map(x, TicketAlumnadoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(listadoTicketsAlumnoOut, HttpStatus.OK);
	}
	
	/**
	 * Nombre tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Nombre tutor", description = "Este metodo devuelve el Nombre tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNombreTutorById/{idTutorFct}")
	public ResponseEntity<TutorFctDualDto> getNombreTutorById(@PathVariable("idTutorFct") Long idTutorFct){
		
		TutorFctDual tutor =  gastosService.getNombreTutorById(idTutorFct);
				
		return new ResponseEntity<>(modelMapper.map(tutor, TutorFctDualDto.class), HttpStatus.OK);
	}	
	/**
	 * Nombre tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Listado Impuesto tipo", description = "Este metodo devuelve el listado Impuesto tipo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getImpuestoTipoServicio/{cAnno}/{tipoServicio}")
	public ResponseEntity<ImpuestoTipoServicioDto> getListadoImpuestoTipoServicio(@PathVariable("cAnno") Integer annoPeriodo, @PathVariable("tipoServicio") String tipServicio){
							
		ImpuestoTipoServicio impuestoTipoServicioIn = gastosService.getImpuestoTipoServicio(annoPeriodo, tipServicio);
		ImpuestoTipoServicioDto impuestoTipoServicioOut = modelMapper.map(impuestoTipoServicioIn, ImpuestoTipoServicioDto.class);
		return new ResponseEntity<>(impuestoTipoServicioOut, HttpStatus.OK);
	}
	
	/**
	 * Datos alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Datos alumno", description = "Este metodo devuelve el Nombre tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosAlumnoGastoNuevo/{annoPeriodo}/{idCentro}")
	public ResponseEntity<AlumnoDto> getDatosAlumnoGastoNuevo(@PathVariable("annoPeriodo") Integer annoPeriodo, @PathVariable("idCentro") Long idCentro, 
			@RequestHeader(Constants.AUTHORIZATION) String jwt){
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		
		Alumno alumno =  gastosService.getDatosAlumnoGastoNuevo(annoPeriodo, idCentro, xUsuarioComunica);
				
		return new ResponseEntity<>(modelMapper.map(alumno, AlumnoDto.class), HttpStatus.OK);
	}	
	
	
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista Estados gastos director", description = "Este metodo devuelve el listado estados de gastos del director",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosGastosDirector")
	public ResponseEntity<List<ElementoSelectDto>> getEstadosGastosDirector(){		
		
		List<ElementoSelect> estados =  gastosService.getEstadosGastosDirector();
		
		List<ElementoSelectDto> estadosDto = estados.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(estadosDto, HttpStatus.OK);
	}
	
	/**
	 * Gasto tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Gasto tutor", description = "Este metodo devuelve un Gasto tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGastoAnexoById/{idAnexo}")
	public ResponseEntity<GastoAnexoDto> getGastoAnexoById(@PathVariable("idAnexo") Long idAnexo)
	{		
		GastoAnexo gasto = gastosService.getGastoAnexoById(idAnexo);

		GastoAnexoDto gastoDto = modelMapper.map(gasto, GastoAnexoDto.class);
				
		return new ResponseEntity<>(gastoDto, HttpStatus.OK);
	}
	
	
	/**
	 * Creación del siguiente estado del flujo alumnado.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT')")
	@Operation(summary = "Creación del siguiente estado del flujo", description = "Este metodo crea el siguiente estado del flujo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createSiguienteEstadoFlujoAnexo")
	public ResponseEntity<Object> createSiguienteEstadoFlujoAnexo(
			@Parameter(description = "Gastos de un alumno/a", required = true) @RequestBody final GastoAnexoHistorialDto gastoDto,
													@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);			
			
			GastoAnexoHistorial gastoHistorialIn = modelMapper.map(gastoDto, GastoAnexoHistorial.class);			
			
			gastoHistorialIn = gastosService.createSiguienteEstadoFlujoAnexo(gastoHistorialIn, 
																			 datosUsuario.getXUsuarioDelphos());
			
			GastoAnexoHistorialDto gastoHistorialOut = modelMapper.map(gastoHistorialIn, GastoAnexoHistorialDto.class);
			
			return new ResponseEntity<>(gastoHistorialOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Borrado de un Gasto de un Tutor", description = "Este metodo borra los datos de un Gasto de un Tutor",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteGastoTutor/{idGastoTutor}")
	public ResponseEntity<HttpStatus> deleteGastoTutor(
			@Parameter(description = "Identificador del Gasto del Tutor", required = true) @PathVariable("idGastoTutor") Long idGastoTutor) {
		try {
			gastosService.deleteGastoTutor(idGastoTutor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//@PreAuthorize("hasAnyRole('ALU','P','PRO', 'C')")
	@Operation(summary = "Borrado de un Gasto de un Alumno", description = "Este metodo borra los datos de un Gasto de un Alumno",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteGastoAlumno/{idGastoAlumno}")
	public ResponseEntity<HttpStatus> deleteGastoAlumno(
			@Parameter(description = "Identificador del Gasto del Alumno", required = true) @PathVariable("idGastoAlumno") Long idGastoAlumno) {
		try {
			gastosService.deleteGastoAlumno(idGastoAlumno);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "getNoModificableAnexoVI", description = "getNoModificableAnexoVI",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNoModificableAnexoVI/{idCentro}/{idPeriodo}/{idTutor}")
	public ResponseEntity<Integer> getNoModificableAnexoVI(@PathVariable("idCentro") Long idCentro,
													 @PathVariable("idPeriodo") Long idPeriodo,
													 @PathVariable("idTutor") Long idTutor){				
		
		Integer noModificable = gastosService.getNoModificableAnexoVI(idCentro,idPeriodo,idTutor);
		
		return new ResponseEntity<Integer>(noModificable, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "getNoModificableAnexoVIbis", description = "getNoModificableAnexoVIbis",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNoModificableAnexoVIbis/{idCentro}/{idPeriodo}")
	public ResponseEntity<Integer> getNoModificableAnexoVIbis(@PathVariable("idCentro") Long idCentro,
													 @PathVariable("idPeriodo") Long idPeriodo
													 )
	{		
		return new ResponseEntity<Integer>(gastosService.getNoModificableAnexoVIbis(idCentro,idPeriodo), HttpStatus.OK);
	}

    //@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")

	/**
	 * Este método devuelve los valores del combo para los gastos de los alumnos
	 *
	 * @param anno
	 * @param idCentro
	 * @param idTutor
	 * @param idCurso
	 * @param idPerfil
	 * @param idPeriodo
	 * @param jwt
	 * @return ResponseEntity<List<ElementoSelectDto>>
	 */
    @Operation(summary = "Get valores del combo de los gastos de alumnos",
            description = "Este método devuelve los valores del combo para los gastos de los alumnos",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getComboGastoAlumnos/{anno}/{idCentro}/{idTutor}/{idCurso}/{idUnidad}/{idPeriodo}")
    public ResponseEntity<List<ElementoSelectDto>> getComboGastoAlumnos(@PathVariable("anno") Integer anno,
                                                  @PathVariable("idCentro") Long idCentro,
                                                  @PathVariable("idTutor") Long idTutor,
                                                  @PathVariable("idCurso") Long idCurso,
                                                  @PathVariable("idUnidad") Long idUnidad,
												  @PathVariable("idPeriodo") Long idPeriodo,
												  @RequestHeader(Constants.AUTHORIZATION) String jwt){

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
        
        List<ElementoSelect> elementIn = gastosService.getComboGastosAlumnos(anno, idCentro, idTutor, idCurso, idUnidad,  xUsuarioComunica,idPeriodo);

        List<ElementoSelectDto> elementOut = elementIn.stream().map(x->modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(elementOut, HttpStatus.OK);
    }

	@Operation(summary = "Obtener estado de los anexos de gastos",
			description = "Este método obtiene el estado del anexo de gastos VI/VI_bis",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadoAnexoGasto/{idCentro}/{idPeriodoGasto}/{idTipoGasto}/{idTutorFct}/{idUnidad}/{idCurso}")
	public ResponseEntity<String> getEstadoAnexoGasto(@PathVariable("idCentro") Long idCentro,
													   @PathVariable("idPeriodoGasto") Long idPeriodoGasto,
					    							   @PathVariable("idTipoGasto") Long idTipoGasto,
					    							   @PathVariable("idTutorFct") Long idTutorFct,
					    							   @PathVariable("idUnidad") Long idUnidad,
					    							   @PathVariable("idCurso") Long idCurso) {

        String estadoActual = gastosService.getEstadoAnexoGasto(idCentro, idPeriodoGasto, idTipoGasto, idTutorFct, idUnidad, idCurso);

		return new ResponseEntity<String>(estadoActual, HttpStatus.OK);
	}
}
