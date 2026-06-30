package es.jccm.edu.proyectosfct.adapter.in.rest.programas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.FamiliaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ListadoProgramaFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ProgramaFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.EmpleadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.OfertaMatriculaGenericoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaGenerico;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.programas.IProgramasFctService;
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
public class ProgramasFctRestController {
	
	private static final String NO_CACHE = "no-cache";

	@Autowired
	IProgramasFctService programasFctService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Tutor que entra a la aplicacion", description = "Este metodo devuelve los datos del tutor que entra a la aplicacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getDatosTutor/{idCentro}/{idEmpleado}"})
	public ResponseEntity<EmpleadoDto> getDatosTutor(@PathVariable("idCentro") Long idCentro,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Empleado tutor = programasFctService.getDatosTutor(idCentro, datosUsuario.getIdEmpleadoDelphos());		
		
		EmpleadoDto tutorDto = modelMapper.map(tutor, EmpleadoDto.class);
		
		return new ResponseEntity<EmpleadoDto>(tutorDto, HttpStatus.OK);

	}	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','CFT','FCT')")
	@Operation(summary = "Lista de Familias del centro", description = "Este metodo devuelve una lista con todas familias para un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasCentro/{idCentro}/{cAnno}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasCentro(@PathVariable("idCentro") Long idCentro,
																 @PathVariable("cAnno") int cAnno) {
		List<Familia> familias = programasFctService.getAllFamiliasCentro(idCentro, cAnno);		
		
		List<FamiliaDto> familiasDto = familias.stream()
				.map(entity -> modelMapper.map(entity, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);

	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Familias del centro precarga tutor", description = "Este metodo devuelve una lista con todas familias para un centro con precarga del tutor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasCentroTutor/{cAnno}/{idTutor}/{idCentro}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasCentroTutor(@PathVariable("cAnno") int cAnno,
																 	  @PathVariable("idTutor") Long idTutor,
																 	 @PathVariable("idCentro") Long idCentro) {
		List<Familia> familias = programasFctService.getAllFamiliasCentroTutor(cAnno, idTutor, idCentro);		
		
		List<FamiliaDto> familiasDto = familias.stream()
				.map(entity -> modelMapper.map(entity, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);

	}	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT')")
	@Operation(summary = "Lista de Familias delegacion", description = "Este metodo devuelve una lista con todas familias delegacion FCT", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasDelegacion/{idPerfil}/{idCentro}/{cAnno}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasDelegacion(@PathVariable("idPerfil") Long idPerfil,
			                                                         @PathVariable("idCentro") Long idCentro,
																     @PathVariable("cAnno") int cAnno,
																     @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		List<Familia> familias = programasFctService.getAllFamiliasDelegacion(datosUsuario.getXUsuarioDelphos(),
																			  idPerfil, 
																			  idCentro, 
																			  cAnno);		
		
		List<FamiliaDto> familiasDto = familias.stream()
				.map(entity -> modelMapper.map(entity, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);
	}
	
	

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Curso del centro", description = "Este metodo devuelve una lista con todos los Curso para una familia para un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({"/getAllCursosFamiliaCentro/{idCentro}/{cAnno}/{idFamilia}"})
	public ResponseEntity<List<OfertaMatriculaGenericoDto>> getAllCursosFamiliaCentro(@PathVariable("idCentro") Long idCentro, 
																					  @PathVariable("cAnno") int cAnno,
																					  @PathVariable("idFamilia") Long idFamilia) {
		List<OfertaMatriculaGenerico> ofertas = programasFctService.getAllCursosFamiliaCentro(idCentro, cAnno, idFamilia);
		
		List<OfertaMatriculaGenericoDto> familiasDto = ofertas.stream()
				.map(entity -> modelMapper.map(entity, OfertaMatriculaGenericoDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<OfertaMatriculaGenericoDto>>(familiasDto, HttpStatus.OK);	
		
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Curso del centro", description = "Este metodo devuelve una lista con todos los Curso para una familia para un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({"/getAllCursosFamiliaTutor/{idTutor}/{cAnno}/{idFamilia}/{idCentro}"})
	public ResponseEntity<List<OfertaMatriculaGenericoDto>> getAllCursosFamiliaTutor(@PathVariable("idTutor") Long idTutor, 
																					  @PathVariable("cAnno") int cAnno,
																					  @PathVariable("idFamilia") Long idFamilia,
																					  @PathVariable("idCentro") Long idCentro) {
		List<OfertaMatriculaGenerico> ofertas = programasFctService.getAllCursosFamiliaTutor(idTutor, cAnno, idFamilia,idCentro);
		
		List<OfertaMatriculaGenericoDto> familiasDto = ofertas.stream()
				.map(entity -> modelMapper.map(entity, OfertaMatriculaGenericoDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<OfertaMatriculaGenericoDto>>(familiasDto, HttpStatus.OK);	
		
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Tutores FCT de un centro", description = "Este metodo devuelve una lista de tutores FCT de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresCentro/{idCentro}")
	public ResponseEntity<List<EmpleadoDto>> getTutoresCentro(@PathVariable("idCentro") Long idCentro) {

		List<Empleado> tutores = programasFctService.getTutoresCentro(idCentro);
		
		List<EmpleadoDto> tutoresDto = tutores.stream()
				.map(entity -> modelMapper.map(entity, EmpleadoDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<EmpleadoDto>>(tutoresDto, HttpStatus.OK);
	}

	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','CFT')")
	@Operation(summary = "Lista de Programas del centro", description = "Este metodo devuelve una lista con todas los Programas del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping(value = {"/getAllProgramasCentroTutor/{idCentro}/{idTutor}/{idFamilia}/{idOferta}/{idConvenio}/{idAnno}" })
	public ResponseEntity<List<ListadoProgramaFctDto>> getAllProgramasCentroTutor(@PathVariable("idCentro") Long idCentro,
																		          @PathVariable("idTutor") Long idTutor,
																		          @PathVariable("idFamilia") Long idFamilia,
																		          @PathVariable("idOferta") Long idOferta,
																		          @PathVariable("idConvenio") Long idConvenio,
																		          @PathVariable("idAnno") Long idAnno) {
		
		List<ListadoProgramaFct> programas = programasFctService.getAllProgramasCentroTutor(idCentro, idTutor, idFamilia, idOferta, idConvenio,idAnno);
		
		List<ListadoProgramaFctDto> programasDto = programas.stream()
				.map(entity -> modelMapper.map(entity, ListadoProgramaFctDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoProgramaFctDto>>(programasDto, HttpStatus.OK);		

	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Programas del centro DTO", description = "Este metodo devuelve una lista con todas los Programas del centro con dto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping(value = {"/getAllProgramasDtoCentroTutor/{idCentro}/{cAnno}/{idTutor}" })
	public ResponseEntity<List<ProgramaFctDto>> getAllProgramasDtoCentroTutor(@PathVariable("idCentro") Long idCentro,
																			  @PathVariable("cAnno") Integer cAnno,
																		      @PathVariable Long idTutor) {
				
		List<ProgramaFct> programas = programasFctService.getAllProgramasDtoCentroTutor(idCentro, cAnno, idTutor);
		
		List<ProgramaFctDto> programasDto = programas.stream()
							.map(entity -> modelMapper.map(entity, ProgramaFctDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProgramaFctDto>>(programasDto, HttpStatus.OK);			

	}

	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Programas del centro", description = "Este metodo devuelve una lista con todas los Programas del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping(value = { "/getAllProgramasCentroTutorName/{idCentro}/{name}/{page}",
			"/getAllProgramasCentroTutorName/{idCentro}/{name}/{page}/{xTutor}" })
	public ResponseEntity<Page<ProgramaFctDto>> getAllProgramasCentroTutorName(@PathVariable("idCentro") Long idCentro,
																			   @PathVariable("name") String sName, @PathVariable("page") int page,
																			   @PathVariable Optional<Integer> xTutor) {
		Long idTutor = null;
		if (xTutor.isPresent())
			idTutor = Long.valueOf(xTutor.get().longValue());

		List<ProgramaFct> programasList = programasFctService.getAllProgramasCentroTutorName(idCentro, sName, page, idTutor).getContent();
		
		List<ProgramaFctDto> programasDtoList = programasList.stream().map(x -> modelMapper.map(x, ProgramaFctDto.class)).collect(Collectors.toList());
		
		Pageable paging = PageRequest.of(page, 10, Sort.by("ds_programa"));		
		
		Page<ProgramaFctDto> programasDtoPage = new PageImpl<ProgramaFctDto>(programasDtoList, paging, 10L);
		
		return new ResponseEntity<Page<ProgramaFctDto>>(programasDtoPage, HttpStatus.OK);	

	}	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Programa del centro", description = "Este metodo devuelve el programa del centro que recibe por id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getProgramaId/{idPrograma}" })
	public ResponseEntity<ProgramaFctDto> getProgramaId(@PathVariable("idPrograma") Long idPrograma) {

		ProgramaFct programaFct = programasFctService.getProgramaId(idPrograma);
		
		ProgramaFctDto programaFctDto = modelMapper.map(programaFct, ProgramaFctDto.class);
		
		return new ResponseEntity<ProgramaFctDto>(programaFctDto, HttpStatus.OK);
	}

	/**
	 * Creación de los Datos de un Programa.
	 *
	 * @param programaFctDto Datos del programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de un Progama", description = "Este metodo crea los datos de un Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createPrograma")
	public ResponseEntity<Object> createPrograma(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final ProgramaFctDto programaFctDto) {
		ProgramaFctDto empresaOut = null;
		ResponseEntity<Object> response = null;
		
		try {
			ProgramaFct programaIn = modelMapper.map(programaFctDto, ProgramaFct.class);
			
			programaIn = programasFctService.createPrograma(programaIn);

			empresaOut = modelMapper.map(programaIn, ProgramaFctDto.class);
			
			response = new ResponseEntity<>(empresaOut, HttpStatus.OK);
		}catch (DataIntegrityViolationException e) {
			if(e.getMessage().contains("FCT_PRO_UK_IDTUT_DSDES")){
				response = new ResponseEntity<>("Un mismo tutor no puede crear dos programas con la misma descripción.", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	/**
	 * Actualizacion de los Datos de un Programa.
	 *
	 * @param programaFctDto Datos del programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualización de los datos de un Programa", description = "Este metodo actualiza los datos de un Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updatePrograma")
	public ResponseEntity<ProgramaFctDto> updatePrograma(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final ProgramaFctDto programaFctDto) {
		
		ProgramaFct programaIn = modelMapper.map(programaFctDto, ProgramaFct.class);

		programaIn = programasFctService.updatePrograma(programaIn);

		ProgramaFctDto empresaOut = modelMapper.map(programaIn, ProgramaFctDto.class);

		return new ResponseEntity<ProgramaFctDto>(empresaOut, HttpStatus.OK);
		
	}

	/**
	 * Borrado de un programa.
	 *
	 * @param idPrograma Id del programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado de un Programa", description = "Este metodo borra los datos de un Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deletePrograma/{idPrograma}")
	public ResponseEntity<HttpStatus> deleteConvenio(
			@Parameter(description = "Identificador del Programa", required = true) @PathVariable("idPrograma") Long idPrograma) {
		try {
			programasFctService.deletePrograma(idPrograma);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Get anexoII Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@GetMapping("/downloadAnexo2Programa/{idConvProg}/{idMatricula}")
	public void generateReport(HttpServletResponse response, 
							   @PathVariable("idConvProg") Long idConvProg,
							   @PathVariable("idMatricula") Long idMatricula) throws JRException, IOException {
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoII_ProgramaFormativo" +"."+ "pdf");
	    response.setHeader("Pragma", NO_CACHE);
	    response.setHeader("Cache-Control", NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = programasFctService.exportReport(idConvProg,idMatricula);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	//Get anexoI Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@GetMapping("/downloadAnexo1AlumnadoPrograma/{idConvProg}")
	public void generateReportAnexoI(HttpServletResponse response, @PathVariable("idConvProg") Long idConvProg) throws JRException, IOException {
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoI_Alumnado" +"."+ "pdf");
	    response.setHeader("Pragma", NO_CACHE);
	    response.setHeader("Cache-Control", NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = programasFctService.exportReportAnexoI(idConvProg);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "tutores del centro", description = "Este metodo devuelve un listado de tutores para el combo de programas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllTutoresListadoProgramas/{idCentro}" })
	public ResponseEntity<Object> getAllTutoresListadoProgramas(@PathVariable("idCentro") Long idCentro) {

		List<TutorFctDual> tutores = programasFctService.getAllTutoresListadoProgramas(idCentro);
		
		List<TutorFctDualDto> tutoresDto = tutores.stream().map( tutor -> modelMapper.map(tutor, TutorFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<Object>(tutoresDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "tutores del centro", description = "Este metodo devuelve un listado de tutores para el combo de programas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasListadoProgramas/{idCentro}/{idTutor}" })
	public ResponseEntity<Object> getAllFamiliasListadoProgramas(@PathVariable("idCentro") Long idCentro, @PathVariable("idTutor") Long idTutor) {

		List<Familia> familias = programasFctService.getAllFamiliasListadoProgramas(idCentro, idTutor);
		
		List<FamiliaDto> familiasDto = familias.stream().map( tutor -> modelMapper.map(tutor, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<Object>(familiasDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "tutores del centro", description = "Este metodo devuelve un listado de tutores para el combo de programas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllOfertaGenericaListadoProgramas/{idCentro}/{idTutor}/{idFamilia}" })
	public ResponseEntity<Object> getAllOfertaGenericaListadoProgramas(@PathVariable("idCentro") Long idCentro, @PathVariable("idTutor") Long idTutor
			, @PathVariable("idFamilia") Long idFamilia) {

		List<OfertaMatriculaGenerico> ofematrgen = programasFctService.getAllOfertaGenericaListadoProgramas(idCentro, idTutor, idFamilia);
		
		List<OfertaMatriculaGenericoDto> ofematrgenDto = ofematrgen.stream().map( tutor -> modelMapper.map(tutor, OfertaMatriculaGenericoDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<Object>(ofematrgenDto, HttpStatus.OK);
	}
	
	/**
	 * Centros.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener centros", description = "Este metodo devuelve el listado de centros de un municipio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCentrosProgramasDelegacion/{idPerfil}/{idCentro}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getCentrosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			  @PathVariable("idCentro") Long idCentro,
																			  @PathVariable("idProvincia") Long idProvincia,
																			  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> centros = programasFctService.getCentrosDelegacion(datosUsuario.getXUsuarioDelphos(), idPerfil, idCentro,idProvincia);
		
		List<ElementoSelectDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Tutores.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener tutores", description = "Este metodo devuelve el listado de tutores de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresProgramasDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getTutoresDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("idCentroProvincia") Long idCentroProvincia,  
																			@PathVariable("idProvincia") Long idProvincia,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> tutores = programasFctService.getTutoresDelegacion(datosUsuario.getXUsuarioDelphos(), 
																				idPerfil, 
																				idCentro,
																				idCentroProvincia,
																				idProvincia);
		
		List<ElementoSelectDto> tutoresDto = tutores.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(tutoresDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Familias.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener tutores", description = "Este metodo devuelve el listado de familias de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFamiliasProgramasDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idTutor}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getFamiliasDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			 @PathVariable("idCentro") Long idCentro,
																			 @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																			 @PathVariable("idTutor") Long idTutor,
																			 @PathVariable("idProvincia") Long idProvincia,
																			 @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> familias = programasFctService.getFamiliasDelegacion(datosUsuario.getXUsuarioDelphos(), 
																				  idPerfil, 
																				  idCentro,
																				  idCentroProvincia,
																				  idTutor,
																				  idProvincia);
		
		List<ElementoSelectDto> familiasDto = familias.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(familiasDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Cursos.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener Cursos", description = "Este metodo devuelve el listado de cursos de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idTutor}/{idFamilia}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getCursosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																		   @PathVariable("idCentro") Long idCentro,
																		   @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																		   @PathVariable("idTutor") Long idTutor,
																	   	   @PathVariable("idFamilia") Long idFamilia,
																	       @PathVariable("idProvincia") Long idProvincia,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> cursos = programasFctService.getCursosDelegacion(datosUsuario.getXUsuarioDelphos(), 
																			  idPerfil, 
																			  idCentro,
																			  idCentroProvincia,
																			  idTutor,
																			  idFamilia,
																			  idProvincia);
		
		List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(cursosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Programas de la delegacion", description = "Este metodo devuelve una lista con todas los Programas de la delegacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllProgramasDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idTutor}/{idFamilia}/{idCurso}/{idProvincia}/{idConvenio}/{cAnno}")
	public ResponseEntity<List<ListadoProgramaFctDto>> getAllProgramasDelegacion(@PathVariable("idPerfil") Long idPerfil,																				 
																				 @PathVariable("idCentro") Long idCentro,
																				 @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																		         @PathVariable("idTutor") Long idTutor,
																		         @PathVariable("idFamilia") Long idFamilia,
																		         @PathVariable("idCurso") Long idCurso,
																		         @PathVariable("idProvincia") Long idProvincia,
																		         @PathVariable("idConvenio") Long idConvenio,
																		         @PathVariable("cAnno") Integer cAnno,
																		         @RequestHeader(Constants.AUTHORIZATION) String jwt) {		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		
		List<ListadoProgramaFct> programas = programasFctService.getAllProgramasDelegacion(datosUsuario.getXUsuarioDelphos(),
				 																		   idPerfil,				 																		   
																						   idCentro, 
																						   idCentroProvincia,
																						   idTutor, 
																						   idFamilia, 
																						   idCurso,
																						   idProvincia,
																						   idConvenio,
																						   cAnno);
		
		List<ListadoProgramaFctDto> programasDto = programas.stream()
				.map(entity -> modelMapper.map(entity, ListadoProgramaFctDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoProgramaFctDto>>(programasDto, HttpStatus.OK);		

	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','CFT')")
	@Operation(summary = "Lista de Programas del centro", description = "Este metodo devuelve una lista con todas los Programas del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping(value = {"/getAllProgramasConvenios/{idCentro}/{idTutor}/{idFamilia}/{idOferta}" })
	public ResponseEntity<List<ElementoSelectDto>> getAllProgramasConvenios(@PathVariable("idCentro") Long idCentro,
																		      @PathVariable("idTutor") Long idTutor,
																		      @PathVariable("idFamilia") Long idFamilia,
																		      @PathVariable("idOferta") Long idOferta) {		
		List<ElementoSelect> convenios = programasFctService.getAllProgramasConvenios(idCentro, idTutor, idFamilia, idOferta);
		
		List<ElementoSelectDto> conveniosDto = convenios.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(conveniosDto, HttpStatus.OK);		

	}
	
	/**
	 * Cursos.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener Convenios", description = "Este metodo devuelve el listado de cursos de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConveniosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idTutor}/{idFamilia}/{idProvincia}/{idCurso}")
		public ResponseEntity<List<ElementoSelectDto>> getConveniosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																		   @PathVariable("idCentro") Long idCentro,
																		   @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																		   @PathVariable("idTutor") Long idTutor,
																	   	   @PathVariable("idFamilia") Long idFamilia,
																	       @PathVariable("idProvincia") Long idProvincia,
																	       @PathVariable("idCurso") Long idCurso,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> convenios = programasFctService.getConveniosDelegacion(datosUsuario.getXUsuarioDelphos(), 
																			  idPerfil, 
																			  idCentro,
																			  idCentroProvincia,
																			  idTutor,
																			  idFamilia,
																			  idProvincia,
																			  idCurso);
		
		List<ElementoSelectDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(conveniosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Programa está siendo usado", description = "Programa está siendo usado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getProgramaUsado/{id}"})
	public ResponseEntity<Integer> getProgramaUsado(@PathVariable("id") Long id) {
		
        Integer usado = programasFctService.getProgramaUsado(id);
        
		return new ResponseEntity<Integer>(usado, HttpStatus.OK);
	}

	
	

}
