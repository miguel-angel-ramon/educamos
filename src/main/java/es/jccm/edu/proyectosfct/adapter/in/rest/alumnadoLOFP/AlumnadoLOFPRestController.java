package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.*;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.UnidadCursoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.ParsemAluplanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModuloModalidadDto;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.PeriodoLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModuloModalidad;
import es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan.IParsemAluplanService;
import es.jccm.edu.proyectosfct.application.ports.in.resultadosAsociadosPlan.IResultadosAsociadosPlanService;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.CursoModalidadDto;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;

import es.jccm.edu.proyectosfct.application.ports.in.alumnadoLOFP.IAlumnadoLOFPService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;

import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio con las operaciones sobre Programas FCT")
public class AlumnadoLOFPRestController {

	@Autowired
	IAlumnadoLOFPService alumnadoLOFPService;

	@Autowired
	IResultadosAsociadosPlanService resultadosAsociadosPlanService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IRodalClient rodalClient;

	@Autowired
	private IParsemAluplanService parsemAluplanService;
	
	@Autowired
	IParamsFCTService iParamsFCTService;


	/**
	 * Lista cursos.
	 *
	 * @return the response entity
	 * @throws ParseException
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Listado cursos LOFP", description = "Este metodo devuelve un listado de cursos para LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosLOFP/{idTutorfctdual}/{idCentro}/{cAnno}/{idPerfil}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<CursoModalidadDto>> getCursosLOFP(@PathVariable("idTutorfctdual") Long idTutorfctdual,
																 @PathVariable("idCentro") Long idCentro,
																 @PathVariable("cAnno") Integer cAnno,
																 @PathVariable("idPerfil") Long idPerfil,
																 @PathVariable("idCentroCombo") Long idCentroCombo,
																 @PathVariable("idProvincia") Long idProvincia,
																 @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());

		List<CursoModalidad> cursos =  alumnadoLOFPService.getCursosEmpleadoCentro(idTutorfctdual,
				idCentro,
				cAnno,
				idPerfil,
				idCentroCombo,
				idProvincia,
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
	@Operation(summary = "Listado unidades LOFP", description = "Este metodo devuelve una listado unidades para LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesLOFP/{idTutorfctdual}/{idCentro}/{cAnno}/{idOfertamatrig}/{idPerfil}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<UnidadCursoDto>> getUnidadesLOFP(@PathVariable("idTutorfctdual") Long idTutorfctdual,
																		  @PathVariable("idCentro") Long idCentro,
																		  @PathVariable("cAnno") Integer cAnno,
																		  @PathVariable("idOfertamatrig") Long idOfertamatrig,
																		  @PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("idCentroCombo") Long idCentroCombo,
																		  @PathVariable("idProvincia") Long idProvincia,
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());

		List<UnidadCurso> unidades =  alumnadoLOFPService.getUnidadesLOFP(idTutorfctdual,
				idCentro,
				cAnno,
				idOfertamatrig,
				idPerfil,
				idCentroCombo,
				idProvincia,
				datosUsuario.getXUsuarioDelphos(),
				xEmpleadoComunica);

		List<UnidadCursoDto> unidadesDto = unidades.stream().map(x -> modelMapper.map(x, UnidadCursoDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<UnidadCursoDto>>(unidadesDto, HttpStatus.OK);
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
	@GetMapping("/getTutoresAlumnadoLOFP/{idTutorfctdual}/{idCentro}/{cAnno}/{idPerfil}/{idCentroCombo}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getTutoresAlumnado(@PathVariable("idTutorfctdual") Long idTutorfctdual, 
																		  @PathVariable("idCentro") Long idCentro, 
																		  @PathVariable("cAnno") Integer cAnno,																		  
																		  @PathVariable("idPerfil") Long idPerfil,
																		  @PathVariable("idCentroCombo") Long idCentroCombo,
																		  @PathVariable("idProvincia") Long idProvincia,
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<ElementoSelect> tutores = alumnadoLOFPService.getTutoresAlumnadoLOFP(idTutorfctdual, 
																		  idCentro, 
																		  cAnno,																		  
																		  idPerfil,
																		  idCentroCombo,
																		  idProvincia,
																		  datosUsuario.getXUsuarioDelphos(),
																		  xEmpleadoComunica
																		  ) ;
		
		List<ElementoSelectDto> tutoresDto = tutores.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(tutoresDto, HttpStatus.OK);
	}
	
	/**
	 * Centros.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener centros", description = "Este metodo devuelve el listado de centros", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCentrosDelegacionLOFP/{idPerfil}/{idCentro}/{idProvincia}/{cAnno}")
		public ResponseEntity<List<ElementoSelectDto>> getCentrosConveniosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("idProvincia") Long idProvincia,
																			@PathVariable("cAnno") Integer cAnno,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> centros = alumnadoLOFPService.getCentrosDelegacionLOFP(datosUsuario.getXUsuarioDelphos(), idPerfil, idCentro, idProvincia, cAnno );
		
		List<ElementoSelectDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(centrosDto, HttpStatus.OK);
	}
	
	
	@Operation(summary = "Listado alumnos tutor LOFP", description = "Este metodo devuelve una listado de alumnos de un tutor LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getListadoAlumnosLOPD/{idTutorfctdual}/{idCentro}/{cAnno}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}/{esSinPlan}/{idEstado}")
		public ResponseEntity<List<AlumnadoTutorLofpDto>> getListadoAlumnosTutor(@PathVariable("idTutorfctdual") Long idTutorfctdual,
																					@PathVariable("idCentro") Long idCentro, 
																					@PathVariable("cAnno") Integer cAnno,																					 
																					@PathVariable("idOfertamatrig") Long idOfertamatrig, 
																					@PathVariable("idUnidad") Long idUnidad,
																					@PathVariable("idPerfil") Long idPerfil,
																					@PathVariable("idCentroCombo") Long idCentroCombo,
																					@PathVariable("idProvincia") Long idProvincia,
																					@PathVariable("esSinPlan") Integer esSinPlan,
																					@PathVariable("idEstado") Integer idEstado,
																					@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
			
			List<AlumnadoTutorLofpDto> listadoAlumnadoTutor =  alumnadoLOFPService.getListadoAlumnosLOPD(idTutorfctdual,
																									  idCentro, 
																									  cAnno,																									   
																									  idOfertamatrig, 
																									  idUnidad,
																									  idPerfil,
																									  idCentroCombo,
																									  idProvincia,
																									  datosUsuario.getXUsuarioDelphos(),
																									  xEmpleadoComunica,
					                                                                                  esSinPlan,
																									  idEstado);

					
			return new ResponseEntity<List<AlumnadoTutorLofpDto>>(listadoAlumnadoTutor, HttpStatus.OK);
		}


	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener familias", description = "Este método devuelve el listado de familias para un centro y tutor en particular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")
	})
	@GetMapping("/getFamiliasCentroTutorLOFP/{idCentro}/{cAnno}/{idTutor}")
	public ResponseEntity<List<ElementoSelectDto>> getFamiliasCentroTutorLOFP(
			@PathVariable("idCentro") Long idCentro,
			@PathVariable("cAnno") Integer cAnno,
			@PathVariable("idTutor") Long idTutor,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {


		List<ElementoSelect> familias = alumnadoLOFPService.getFamiliasCentroTutorLOFP(idCentro, cAnno, idTutor);

		List<ElementoSelectDto> familiasDto = familias.stream()
				.map(familia -> modelMapper.map(familia, ElementoSelectDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(familiasDto, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener modalidades", description = "Este método devuelve el listado de modalidades para un centro, tutor y familia específicos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")
	})
	@GetMapping("/getModCentroTutorFamiliaLOFP/{idCentro}/{cAnno}/{idTutor}/{idFamilia}")
	public ResponseEntity<List<ElementoSelectDto>> getModCentroTutorFamiliaLOFP(
			@PathVariable("idCentro") Long idCentro,
			@PathVariable("cAnno") Integer cAnno,
			@PathVariable("idTutor") Long idTutor,
			@PathVariable("idFamilia") Long idFamilia,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {

		List<ElementoSelect> modalidades = alumnadoLOFPService.getModCentroTutorFamiliaLOFP(idCentro, cAnno, idTutor, idFamilia);

		List<ElementoSelectDto> modalidadesDto = modalidades.stream()
				.map(modalidad -> modelMapper.map(modalidad, ElementoSelectDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(modalidadesDto, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener cursos", description = "Este método devuelve el listado de cursos según centro, año, tutor, familia y modalidad",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")
	})
	@GetMapping("/getCursosCentroAnnoTutorFamiliaModalidadLOFP/{idCentro}/{cAnno}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<ElementoSelectDto>> getCursosCentroAnnoTutorFamiliaModalidadLOFP(
			@PathVariable("idCentro") Long idCentro,
			@PathVariable("cAnno") Integer cAnno,
			@PathVariable("idTutor") Long idTutor,
			@PathVariable("idFamilia") Long idFamilia,
			@PathVariable("idModalidad") Long idModalidad,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ElementoSelect> cursos = alumnadoLOFPService.getCursosCentroAnnoTutorFamiliaModalidadLOFP(idCentro, cAnno, idTutor, idFamilia, idModalidad);

		List<ElementoSelectDto> cursosDto = cursos.stream()
				.map(curso -> modelMapper.map(curso, ElementoSelectDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(cursosDto, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de cursos modalidad LOFP", description = "Este método devuelve la lista de cursos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")
	})
	@GetMapping("/getCursosModalidadLOFP/{idCurso}/{idCentro}/{cAnno}")
	public ResponseEntity<List<CursoModalidadDto>> getCursosModalidadLOFP(
			@Parameter(description = "Identificador de la modalidad", required = true) @PathVariable("idCurso") Long idCurso,
			@PathVariable("idCentro") Integer idCentro,
			@PathVariable("cAnno") Integer cAnno) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<CursoModalidad> cursoLOFP = alumnadoLOFPService.getCursosModalidadLOFP(idCurso, idCentro, cAnno);

			List<CursoModalidadDto> cursoLOFPDto = cursoLOFP.stream()
					.map(x -> modelMapper.map(x, CursoModalidadDto.class))
					.collect(Collectors.toList());

			return new ResponseEntity<List<CursoModalidadDto>>(cursoLOFPDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Modulos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModulosModalidadLOFP/{idCurso}/{idCentro}/{cAnno}")
	public ResponseEntity<List<ModuloModalidadDto>> getModulosModalidadLOFP(@PathVariable("idCurso") Long idCurso,@PathVariable("idCentro") Integer idCentro,
																		@PathVariable("cAnno") Integer cAnno) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ModuloModalidad> moduloLOFP = alumnadoLOFPService.getModulosModalidadLOFP(idCurso, idCentro, cAnno);

			List<ModuloModalidadDto> moduloLOFPDto = moduloLOFP.stream().map(x -> modelMapper.map(x, ModuloModalidadDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ModuloModalidadDto>>(moduloLOFPDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de información de seguimiento alumnado", description = "Este metodo devuelve la lista de meses del seguimiento de alumnado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosSeguimientoLOFP/{xMatricula}/{idEmpresa}/{idProyecto}")
	public ResponseEntity<List<PeriodoLOFPDto>> getDatosSeguimientoLOFP(@PathVariable("xMatricula") Long xMatricula, @PathVariable("idEmpresa") Long idEmpresa, @PathVariable("idProyecto") Long idProyecto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<PeriodoLOFP> periodoLOFP = alumnadoLOFPService.getDatosSeguimientoLOFP(xMatricula, idEmpresa, idProyecto);

			List<PeriodoLOFPDto> periodoLOFPDto = periodoLOFP.stream().map(x -> modelMapper.map(x, PeriodoLOFPDto.class)).collect(Collectors.toList());

			return new ResponseEntity<>(periodoLOFPDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Guarda estado de los planes", description = "Este método guarda el estado de validación de los planes",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/postValidarPlanMasivo/{cPerfil}")
	public void postValidarPlanMasivo(@PathVariable("cPerfil") String cPerfil,
									  @Parameter(description = "Listado de matriculas", required = true) @RequestBody final List<Long> matriculas,
									  @RequestHeader(Constants.AUTHORIZATION) String jwt) {

			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());

			alumnadoLOFPService.postValidarPlanMasivo(matriculas, cPerfil, datosUsuario.getXUsuarioDelphos(), xUsuarioComunica);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Tutor y responsable del plan", description = "Este metodo devuelve los nombres del tutor y del responsable del plan",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutorYResponsable/{idConvProyAlu}")
	public ResponseEntity<DatosTutorYResponsableDto> getTutorYResponsable(@PathVariable("idConvProyAlu") Long idConvProyAlu) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			DatosTutorYResponsableDto datosEncargadosDto = alumnadoLOFPService.getTutorYResponsable(idConvProyAlu);

			return new ResponseEntity<>(datosEncargadosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Validaciones por matrícula", description = "Devuelve el listado de estados de validación asociados a una matrícula",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoValidacionDto.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, listado devuelto correctamente"),
			@ApiResponse(responseCode = "404", description = "Matrícula no encontrada"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@GetMapping("/getHistoricoValidacionesPlanPorMatricula/{matricula}")
	public ResponseEntity<List<EstadoValidacionDto>> getHistoricoValidacionesPlanPorMatricula(@PathVariable("matricula") Long matricula) {
		try {
			List<EstadoValidacionDto> validaciones = alumnadoLOFPService.getValidacionesPorMatricula(matricula);

			return new ResponseEntity<>(validaciones, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Subir múltiples documentos asociados a partes semanales",
			description = "Este método permite subir múltiples documentos asociados a partes semanales. Los datos incluyen archivos, fechas y los identificadores requeridos.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Documentos subidos correctamente"),
					@ApiResponse(responseCode = "400", description = "Error en los datos de entrada"),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor")
			}
	)
	@PostMapping("/postMultipleParSemAluPlan/{idConvProyAlu}/{anno}/{xCentro}/{cPerfil}")
	public ResponseEntity<Object> postMultipleParSemAluPlan(
			@PathVariable("idConvProyAlu") Long idConvProyAlu,
			@PathVariable("anno") String anno,
			@PathVariable("xCentro") String xCentro,
			@PathVariable("cPerfil") String cPerfil,
			@RequestParam("files") List<MultipartFile> files,
			@RequestParam("fechas") List<String> fechas,
			@RequestHeader(Constants.AUTHORIZATION) String jwt
	) {
		try {
			// Obtener datos del usuario
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<Object> resultados = new ArrayList<>();

			for (int i = 0; i < fechas.size(); i++) {
				// Parsear la fecha
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date fecha = dateFormat.parse(fechas.get(i));
				MultipartFile file = files.get(i);

				Optional<ParsemAluplanDto> parteExistente = parsemAluplanService.obtenerParsemAluplanDtoPorConvProyYFecha(
						idConvProyAlu, fecha);

				ParsemAluplanDto parte;
				if (parteExistente.isPresent()) {
					parte = parteExistente.get();
				} else {
					// Crear un nuevo parte semanal si no existe
					ParsemAluplan nuevoParte = new ParsemAluplan();
					nuevoParte.setIdConvproyAlu(idConvProyAlu);
					nuevoParte.setFInisem(fecha);
					nuevoParte.setLgActualizado(1);
					parte = parsemAluplanService.crearParsemAluplan(nuevoParte);
				}

				Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
				
				if (parte.getIdParsemRodal() != null && parte.getTxParsemFichero() != null) {
					// Realizar update
					Object resultadoUpdate = rodalClient.actualizaDoc(
							file, "MFCT", "PAR_SEMPL_", parte.getIdParsemAluplan(), cPerfil,
							datosUsuario.getXUsuarioDelphos(), xUsuarioComunica);
					resultados.add(resultadoUpdate);
				} else {
					// Realizar insert
					Object resultadoInsert = rodalClient.insertaDoc(
							file, "MFCT", "PAR_SEMPL_", parte.getIdParsemAluplan(), Long.valueOf(anno),
							Long.valueOf(xCentro), -1L, cPerfil, datosUsuario.getXUsuarioDelphos(),
							xUsuarioComunica);
					resultados.add(resultadoInsert);
				}
			}

			return ResponseEntity.ok(resultados);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error procesando la solicitud.");
		}
	}


}