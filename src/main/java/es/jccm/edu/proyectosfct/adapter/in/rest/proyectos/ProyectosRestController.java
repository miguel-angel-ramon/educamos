package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ParteSemanalAnexosProyectoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoVinculacionEmpresasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoParteSemanalDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.ParsemAluplanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.InfoAdicionalPlanDto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.*;
import es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan.IParsemAluplanService;
import net.sf.jasperreports.engine.*;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ListadoProyectosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModuloModalidadDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModuloProyectoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModulosActividadDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModulosCursoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ProyectosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.TipoProyectosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IProyectosService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.modalidades.model.ModalidadDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.FamiliaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectosHorarioAlumnoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConvProyAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConvProyHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectoAlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectoAnexosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.CursoModalidadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Proyectos FCT", description = "Servicio con las operaciones sobre Proyectos FCT")
public class ProyectosRestController {
	
	private static final String NO_CACHE = "no-cache";
	private static final String APPLICATION = "application/octet-stream";
	private static final String CONTENT = "Content-Disposition";
	private static final String ATTACHMENT = "attachment; filename=";
	private static final String PRAGMA = "Pragma";
	private static final String CACHE = "Cache-Control";
	private static final String ID_CONVALU = "idConvProyAlu";
	private static final String FECHAS = "fechas";
	
	@Autowired
	private IProyectosService proyectosService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private IParsemAluplanService parsemAluplanService;
	
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','CFT')")
	@Operation(summary = "Lista de Proyectos", description = "Este metodo devuelve una lista con todos los proyectos de un centro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllProyectos/{idCentro}/{idAnno}/{idTutorActual}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}/{idCurso}")
	public ResponseEntity<List<ListadoProyectosDto>> getAllProyectos(
			             @Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
			             @Parameter(description = "Identificador del anno", required = true) @PathVariable("idAnno") Integer idAnno,
						 @Parameter(description = "Identificador del tutor de consulta", required = true) @PathVariable("idTutorActual") Long idTutorActual,
			             @PathVariable("idTipoProyecto") Long idTipoProyecto, @PathVariable("idTutor") Long idTutor, 
			             @PathVariable("idFamilia") Long idFamilia, @PathVariable("idModalidad") Long idModalidad, @PathVariable("idCurso") Long idCurso){
		
		List<ListadoProyectos> proyectos = proyectosService.getAllProyectos(idCentro, idAnno, idTutorActual, idTipoProyecto, idTutor, idFamilia, idModalidad,idCurso);
		
		List<ListadoProyectosDto> proyectosDto = proyectos.stream().map(x -> modelMapper.map(x, ListadoProyectosDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoProyectosDto>>(proyectosDto, HttpStatus.OK);
	
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de tipo proyectos", description = "Este metodo devuelve un listado de tipo proyectos",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTiposListadoProyectos/{idCentro}/{idAnno}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<TipoProyectosDto>> getTiposListadoProyectos(
			             @Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
			             @Parameter(description = "Identificador del anno", required = true) @PathVariable("idAnno") Integer idAnno,
			             @PathVariable("idTipoProyecto") Long idTipoProyecto, @PathVariable("idTutor") Long idTutor, 
			             @PathVariable("idFamilia") Long idFamilia, @PathVariable("idModalidad") Long idModalidad){		
		
		List<TipoProyecto> TipoProyectos = proyectosService.getTiposListadoProyectos(idCentro, idAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		List<TipoProyectosDto> TipoProyectosDto = TipoProyectos.stream().map(x -> modelMapper.map(x, TipoProyectosDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TipoProyectosDto>>(TipoProyectosDto, HttpStatus.OK);
	
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Lista de tutores", description = "Este metodo devuelve un listado de tutores",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresListadoProyectos/{idCentro}/{idAnno}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<TutorFctDualDto>> getTutoresListadoProyectos(
			             @Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
			             @Parameter(description = "Identificador del anno", required = true) @PathVariable("idAnno") Integer idAnno,
			             @PathVariable("idTipoProyecto") Long idTipoProyecto, @PathVariable("idTutor") Long idTutor, 
			             @PathVariable("idFamilia") Long idFamilia, @PathVariable("idModalidad") Long idModalidad){		
		
		List<TutorFctDual> tutores = proyectosService.getTutoresListadoProyectos(idCentro, idAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		List<TutorFctDualDto> tutoresDto = tutores.stream().map(x -> modelMapper.map(x, TutorFctDualDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TutorFctDualDto>>(tutoresDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de familias", description = "Este metodo devuelve un listado de familias",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFamiliaListadoProyectos/{idCentro}/{idAnno}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<FamiliaDto>> getFamiliaListadoProyectos(
			             @Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
			             @Parameter(description = "Identificador del anno", required = true) @PathVariable("idAnno") Integer idAnno,
			             @PathVariable("idTipoProyecto") Long idTipoProyecto, @PathVariable("idTutor") Long idTutor, 
			             @PathVariable("idFamilia") Long idFamilia, @PathVariable("idModalidad") Long idModalidad){		
		
		List<Familia> familias = proyectosService.getFamiliaListadoProyectos(idCentro, idAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		List<FamiliaDto> familiasDto = familias.stream().map(x -> modelMapper.map(x, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de modalidades", description = "Este metodo devuelve un listado de modalidades",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModalidadListadoProyectos/{idCentro}/{idAnno}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<ModalidadDto>> getModalidadListadoProyectos(
			             @Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
			             @Parameter(description = "Identificador del anno", required = true) @PathVariable("idAnno") Integer idAnno,
			             @PathVariable("idTipoProyecto") Long idTipoProyecto, @PathVariable("idTutor") Long idTutor, 
			             @PathVariable("idFamilia") Long idFamilia, @PathVariable("idModalidad") Long idModalidad){		
		
		List<Modalidad> modalidades = proyectosService.getModalidadListadoProyectos(idCentro, idAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		List<ModalidadDto> modalidadesDto = modalidades.stream().map(x -> modelMapper.map(x, ModalidadDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ModalidadDto>>(modalidadesDto, HttpStatus.OK);
	}
	
	/**
	 * Creación de los Datos de proyectos.
	 *
	 * @param 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de proyectos", description = "Este metodo crea proyectos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createProyecto")
	public ResponseEntity<ProyectosDto> createProyecto(
			@Parameter(description = "Datos del Proyecto", required = true) @RequestBody final ProyectosDto proyectoDto) {
		try {
			Proyectos proyectoIn = modelMapper.map(proyectoDto, Proyectos.class);
			
			proyectoIn = proyectosService.createProyecto(proyectoIn);

			ProyectosDto proyectoOut = modelMapper.map(proyectoIn, ProyectosDto.class);
			
			return new ResponseEntity<ProyectosDto>(proyectoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Copiar los datos de un plan
	 *
	 * @param idProyectoOriginal ID del plan original a copiar
	 * @param proyectoDto Datos del nuevo proyecto a crear
	 * @return ResponseEntity con los datos del plan copiado
	 */
	@Operation(summary = "Copiar un plan", description = "Este método copia un plan.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, copiado correctamente"),
			@ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/copiarPlan/{idProyectoOriginal}")
	public ResponseEntity<Object> copiarPlan(
			@Parameter(description = "ID del proyecto original", required = true)
			@PathVariable Long idProyectoOriginal,

			@Parameter(description = "Datos del nuevo proyecto", required = true)
			@RequestBody final ProyectosDto proyectoDto) {

		try {
			ProyectosDto nuevoProyecto = proyectosService.copiarPlan(idProyectoOriginal, proyectoDto);
			return ResponseEntity.ok(nuevoProyecto);		    
		} catch (Exception e) {
			
			 if (e.getCause() instanceof ResponseStatusException) {
			        ResponseStatusException rse = (ResponseStatusException) e.getCause();
			        return new ResponseEntity<>(rse.getReason(), HttpStatus.BAD_REQUEST);
			    }
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * actualiza los Datos de proyectos.
	 *
	 * @param 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualiza de proyectos", description = "Este metodo actualiza proyectos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateProyecto")
	public ResponseEntity<ProyectosDto> updateProyecto(
			@Parameter(description = "Datos del Proyecto", required = true) @RequestBody final ProyectosDto proyectoDto) {
		try {
			Proyectos proyectoIn = modelMapper.map(proyectoDto, Proyectos.class);
			
			proyectoIn = proyectosService.updateProyecto(proyectoIn);

			ProyectosDto proyectoOut = modelMapper.map(proyectoIn, ProyectosDto.class);
			
			return new ResponseEntity<ProyectosDto>(proyectoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Proyecto del centro", description = "Este metodo devuelve el proyecto del centro que recibe por id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getProyectoId/{idProyecto}" })
	public ResponseEntity<ProyectosDto> getProyectoId(@PathVariable("idProyecto") Long idProyecto) {
		
		Proyectos proyecto = proyectosService.getProyectoId(idProyecto);
		
		ProyectosDto proyectoDto = modelMapper.map(proyecto, ProyectosDto.class);
		
		return new ResponseEntity<ProyectosDto>(proyectoDto, HttpStatus.OK);
	}
	
	
	/**
	 * Asocia Convenios a proyectos.
	 *
	 * @param IdProyeto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Asocia Convenios a un proyecto", description = "Este metodo asocia un proyecto a un conjunto de Convenios",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	//@PostMapping("/conveniosAProyecto/{idProyecto}")
	//public ResponseEntity<Object> conveniosAproyecto(
			//@Parameter(description = "Asocia Proyecto a convenios", required = true) @RequestBody final List<ConveniosProyectoDto> proconvDto,
	//		@Parameter(description = "Asocia Proyecto a convenios", required = true) @RequestBody final List<ConveniosProyectosHorarioAlumnoFctDto> proconvDto,
	//		@PathVariable("idProyecto") Long idProyecto) {
	@PostMapping("/createConvenioProyectos")
	public ResponseEntity<Object> conveniosAproyecto(
			@Parameter(description = "Convenio a proyecto", required = true) @RequestBody final List<ConveniosProyectosHorarioAlumnoFctDto> listConveniosProyectosHorAluFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
			
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			//List<ConveniosProyecto> proconv = proconvDto.stream().map(x -> modelMapper.map(x, ConveniosProyecto.class)).collect(Collectors.toList());
					
           //proconv = proyectosService.conveniosAproyecto(proconv, idProyecto);
			
			//List<ConveniosProyectoDto> proconvDtoUpdate = proconv.stream().map(x -> modelMapper.map(x, ConveniosProyectoDto.class)).collect(Collectors.toList());
			
			//return new ResponseEntity<List<ConveniosProyectoDto>>(proconvDtoUpdate, HttpStatus.OK);
			
			List<ConveniosProyecto> proconv = proyectosService.createConvenioProyectoHorarioAlumno(listConveniosProyectosHorAluFctDto);
			
			List<ConveniosProyectoDto> conveniosProgramasFctListOut = proconv.stream().map(entity -> modelMapper.map(entity, ConveniosProyectoDto.class)).collect(Collectors.toList());
		
			response = new ResponseEntity<>(conveniosProgramasFctListOut, HttpStatus.OK);			
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	 * Devuelve la lista de convenios asociados a un proyecto.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de empresas", description = "Este metodo devuelve la lista de empresas de un proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpresasProyecto/{idProyecto}")
	public ResponseEntity<List<ConveniosProyectoDto>> getEmpresasProyecto(
			@Parameter(description = "Identificador del Proyecto", required = true) @PathVariable("idProyecto") Long idProyecto) {
		try {
										
			List<ConveniosProyecto> convproy = proyectosService.getEmpresasProyecto(idProyecto);	
			
			List<ConveniosProyectoDto> convproyDto = convproy.stream().map(x -> modelMapper.map(x, ConveniosProyectoDto.class)).collect(Collectors.toList());
			
			for (int i = 0; i < convproyDto.size(); i++) {
				
				Integer anno = proyectosService.getAnnoConvenioProyecto(convproyDto.get(i).getId());
				
				
				List<Alumno> alumnos =  proyectosService.getAlumnosSeleccionados(convproyDto.get(i).getConvenio().getCentro().getId(),
																				 anno, 
																				 convproyDto.get(i).getId());
				
				List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
				convproyDto.get(i).setAlumnos(alumnosDto);
				
			}
			
			return new ResponseEntity<List<ConveniosProyectoDto>>(convproyDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Devuelve la lista de convenios asociados a un proyecto.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de empresas", description = "Este metodo devuelve la lista de empresas de un proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpresasProyectoByAnno/{idProyecto}/{cAnno}")
	public ResponseEntity<List<ConveniosProyectoDto>> getEmpresasProyectoByAnno(
			@Parameter(description = "Identificador del Proyecto", required = true) @PathVariable("idProyecto") Long idProyecto,
			@Parameter(description = "Identificador del anno", required = true) @PathVariable("cAnno") Integer cAnno) {
		try {
										
			List<ConveniosProyecto> convproy = proyectosService.getEmpresasProyectoByAnno(idProyecto,cAnno);	
			
			List<ConveniosProyectoDto> convproyDto = convproy.stream().map(x -> modelMapper.map(x, ConveniosProyectoDto.class)).collect(Collectors.toList());
			
			for (int i = 0; i < convproyDto.size(); i++) {
				
				Integer anno = proyectosService.getAnnoConvenioProyecto(convproyDto.get(i).getId());
				
				
				List<Alumno> alumnos =  proyectosService.getAlumnosSeleccionados(convproyDto.get(i).getConvenio().getCentro().getId(),
																				 anno, 
																				 convproyDto.get(i).getId());
				
				List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
				convproyDto.get(i).setAlumnos(alumnosDto);
				
			}
			
			
			return new ResponseEntity<List<ConveniosProyectoDto>>(convproyDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Devuelve la lista de cursos asociados a una modalidad.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve la lista de cursos modalidad",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosModalidad/{idModalidad}/{cAnno}/{idCentro}/{idTipoMod}")
	public ResponseEntity<List<CursoModalidadDto>> getCursosModalidad(
			@Parameter(description = "Identificador de la modalidad", required = true) @PathVariable("idModalidad") Long idModalidad,  
																					   @PathVariable("idCentro") Integer idCentro,
																					   @PathVariable("cAnno") Integer cAnno,
																					   @PathVariable("idTipoMod") String idTipoMod) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
										
			List<CursoModalidad> curso = proyectosService.getCursosModalidad(idModalidad, cAnno, idCentro, idTipoMod);	
			
			List<CursoModalidadDto> cursoDto = curso.stream().map(x -> modelMapper.map(x, CursoModalidadDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<CursoModalidadDto>>(cursoDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Devuelve la lista de Modulos asociados a una modalidad.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Modulos", description = "Este metodo devuelve la lista de Modulos modalidad",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModulosModalidad/{idOfertamatrig}/{idModalidad}/{cAnno}/{idCentro}")
	public ResponseEntity<List<ModuloModalidadDto>> getModulosModalidad(@PathVariable("idOfertamatrig") Long idOfertamatrig, @PathVariable("idModalidad") Long idModalidad,  
			@PathVariable("cAnno") Integer cAnno, @PathVariable("idCentro") Integer idCentro) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<ModuloModalidad> modulo = proyectosService.getModulosModalidad(idOfertamatrig, idModalidad, cAnno, idCentro);	
			
			List<ModuloModalidadDto> moduloDto = modulo.stream().map(x -> modelMapper.map(x, ModuloModalidadDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ModuloModalidadDto>>(moduloDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Devuelve la lista de Modulos asociados al proyecto.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Modulos", description = "Este metodo devuelve la lista de Modulos asociado al proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModulosProyecto/{idProyecto}")
	public ResponseEntity<List<ModuloProyectoDto>> getModulosProyecto(@PathVariable("idProyecto") Long idProyecto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
										
			List<ModuloProyecto> modulo = proyectosService.getModulosProyecto(idProyecto);	
			
			List<ModuloProyectoDto> moduloDto = modulo.stream().map(x -> modelMapper.map(x, ModuloProyectoDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ModuloProyectoDto>>(moduloDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Asocia Convenios a proyectos.
	 *
	 * @param IdProyeto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Asocia Modulos a un proyecto", description = "Asocia Modulos a un proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/modulosAProyecto/{idProyecto}")
	public ResponseEntity<List<ModulosCursoDto>> modulosAproyecto(@RequestBody final List<ModulosCursoDto> modulosDto, @PathVariable("idProyecto") Long idProyecto) {
		try {
			
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<ModulosCurso> modulos = modulosDto.stream().map(x -> modelMapper.map(x, ModulosCurso.class)).collect(Collectors.toList());
					
			modulos = proyectosService.modulosAproyecto(modulos, idProyecto);
			
			List<ModulosCursoDto> modulosDtoUpdate = modulos.stream().map(x -> modelMapper.map(x, ModulosCursoDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ModulosCursoDto>>(modulosDtoUpdate, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Familias del centro", description = "Este metodo devuelve una lista con todas familias para un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasCentroProyectos/{idCentro}/{cAnno}/{idTipo}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasCentro(@PathVariable("idCentro") Long idCentro,
																 @PathVariable("cAnno") int cAnno,
																 @PathVariable("idTipo") Long idTipo) {
			
		List<Familia> familias = proyectosService.getAllFamiliasCentro(idCentro, cAnno, idTipo);		
		
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
	@Operation(summary = "Lista de Familias del centro tutor", description = "Este metodo devuelve una lista con todas familias para un centro precarga tutor", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasCentroProyectosTutor/{idCentro}/{cAnno}/{idTipo}/{idTutor}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasCentro(@PathVariable("idCentro") Long idCentro,
																 @PathVariable("cAnno") int cAnno,
																 @PathVariable("idTipo") Long idTipo,
																 @PathVariable("idTutor") Long idTutor) {
			
		List<Familia> familias = proyectosService.getAllFamiliasCentroTutor(idCentro, cAnno, idTipo, idTutor);		
		
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
	@Operation(summary = "Convenio proyecto", description = "Este metodo devuelve el convenio de un proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getConvenioProyecto/{idConvProy}" })
	public ResponseEntity<ConveniosProyectoDto> getConvenioProyecto(@PathVariable("idConvProy") Long idConvProy) {
		
		ConveniosProyecto convProyecto = proyectosService.getConvenioProyecto(idConvProy);
		
		ConveniosProyectoDto convProyectoDto = modelMapper.map(convProyecto, ConveniosProyectoDto.class);
		
		return new ResponseEntity<ConveniosProyectoDto>(convProyectoDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
	@Operation(summary = "Convenio proyecto alumno", description = "Este metodo devuelve el convenio de un proyecto alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getConvenioProyectoAlumno/{idConvProyAlu}" })
	public ResponseEntity<ConveniosProyectoAlumnoDto> getConvenioProyectoAlumno(@PathVariable("idConvProyAlu") Long idConvProyAlu) {
		
		ConveniosProyectoAlumno convProyectoAlu = proyectosService.getConvenioProyectoAlumno(idConvProyAlu);
		
		ConveniosProyectoAlumnoDto convProyectoaluDto = modelMapper.map(convProyectoAlu, ConveniosProyectoAlumnoDto.class);
		
		return new ResponseEntity<ConveniosProyectoAlumnoDto>(convProyectoaluDto, HttpStatus.OK);
	}	
	
	/**
	 * Creación de los Datos de Alumno Convenio Proyecto.
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
	@PostMapping("/createAlumnoConvenioProyecto")
	public ResponseEntity<List<ConveniosProyectoAlumnoDto>> createAlumnoConvenioProyecto(@RequestBody final List<ConveniosProyectoAlumnoDto> alumnosProgramaDto,
																						 @RequestParam("idConvProy") Long idConvProy)
			
     {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			List<ConveniosProyectoAlumno> alumnosProyectoIn = alumnosProgramaDto.stream().map(x -> modelMapper.map(x, ConveniosProyectoAlumno.class)).collect(Collectors.toList());
					
			alumnosProyectoIn = proyectosService.createAlumnoProyecto(idConvProy,alumnosProyectoIn);
			
			List<ConveniosProyectoAlumnoDto> alumnosProyectoOut = alumnosProyectoIn.stream().map(x -> modelMapper.map(x, ConveniosProyectoAlumnoDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ConveniosProyectoAlumnoDto>>(alumnosProyectoOut, HttpStatus.OK);
		
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
    @PutMapping("/updateSegSocialAlumProy")
    public ResponseEntity<List<AlumnoDto>> updateSegSocialAlumProy(@RequestBody final List<AlumnoDto> alumSeleccionados) {

		try {

			proyectosService.updateAlumSeleccionados(alumSeleccionados);

			return new ResponseEntity<List<AlumnoDto>>(alumSeleccionados, HttpStatus.OK);


		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

    }
	
	/**
	 * Update de los Datos de Alumno Convenio Proyecto.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU')")
	@Operation(summary = "Update de Alumno Convenio Programa", description = "Este metodo actualiza Alumno Convenio Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateAlumnoConvenioProyecto")
	public ResponseEntity<ConveniosProyectoAlumnoDto> updateAlumnoConvenioProyecto(
			@Parameter(description = "Datos del Programa", required = true) @RequestBody final ConveniosProyectoAlumnoDto alumnosProgramaDto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			ConveniosProyectoAlumno alumnosProyectoIn = modelMapper.map(alumnosProgramaDto, ConveniosProyectoAlumno.class);
					
			alumnosProyectoIn = proyectosService.updateAlumnoConvenioProyecto(alumnosProyectoIn);
			
			ConveniosProyectoAlumnoDto alumnosProyectoOut = modelMapper.map(alumnosProyectoIn, ConveniosProyectoAlumnoDto.class);
			
			return new ResponseEntity<ConveniosProyectoAlumnoDto>(alumnosProyectoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Alumnos seleccionadops asociados al convenio-proyecto", description = "Este metodo devuelve una lista de Alumnos seleccionadops asociados al convenio-proyecto",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosProyectoSeleccionados/{idCentro}/{anno}/{idConvProy}")
	public ResponseEntity<List<AlumnoDto>> getAlumnosProyectoSeleccionados(@PathVariable("idCentro") Long idCentro, @PathVariable("anno") int cAnno,
																		   @PathVariable("idConvProy") Long idConvProy){

		List<Alumno> alumnos =  proyectosService.getAlumnosSeleccionados(idCentro, cAnno, idConvProy);

		List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<AlumnoDto>>(alumnosDto, HttpStatus.OK);
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
	@GetMapping("/getAlumnosProyectos/{idCentro}/{idOfertamatrig}/{anno}/{idConvProy}")
	public ResponseEntity<List<AlumnoDto>> getAlumnosProyecto(@PathVariable("idCentro") Long idCentro, 
											        		  @PathVariable("idOfertamatrig") Long idOfertamatrig, 
													          @PathVariable("anno") int cAnno,
													          @PathVariable("idConvProy") Long idConvProy ){
		
		List<Alumno> alumnos =  proyectosService.getAlumnosProyectos(idCentro, idOfertamatrig, cAnno, idConvProy);
		
		List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<AlumnoDto>>(alumnosDto, HttpStatus.OK);
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
	@GetMapping("/getAlumnosProyectosModalidad/{idCentro}/{idModalidad}/{anno}/{idConvProy}")
	public ResponseEntity<List<AlumnoVinculacionEmpresasDto>> getAlumnosProyectoModalidad(@PathVariable("idCentro") Long idCentro,
																						  @PathVariable("idModalidad") Long idModalidad,
																						  @PathVariable("anno") int cAnno,
																						  @PathVariable("idConvProy") Long idConvProy ){

		List<AlumnoVinculacionEmpresasDto> alumnosDto =  proyectosService.getAlumnosProyectosModalidad(idCentro, idModalidad, cAnno, idConvProy);



		return new ResponseEntity<List<AlumnoVinculacionEmpresasDto>>(alumnosDto, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Modulo curso", description = "Este metodo devuelve el modulo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getModuloCurso/{idModCurso}" })
	public ResponseEntity<ModulosCursoDto> getModuloCurso(@PathVariable("idModCurso") Long idModCurso) {
		
		ModulosCurso moduloCurso = proyectosService.getModuloCurso(idModCurso);
		
		ModulosCursoDto moduloCursoDto = modelMapper.map(moduloCurso, ModulosCursoDto.class);
		
		return new ResponseEntity<ModulosCursoDto>(moduloCursoDto, HttpStatus.OK);
	}	
	
	/**
	 * Creación de los Datos de modulos a actividades.
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
	@PostMapping("/createModuloActividad/{idModuloCurso}")
	public ResponseEntity<List<ModulosActividadDto>> createModuloActividad(
			@Parameter(description = "Datos del Convenio", required = true) @RequestBody final List<ModulosActividadDto> moduloActividadDto, @PathVariable("idModuloCurso") Long idModuloCurso) {
		
 		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		List<ModulosActividad> moduloActividad = moduloActividadDto.stream().map(x -> modelMapper.map(x, ModulosActividad.class)).collect(Collectors.toList());
				
		moduloActividad = proyectosService.createModuloActividad(moduloActividad, idModuloCurso);
		
		List<ModulosActividadDto> moduloActividadOut = moduloActividad.stream().map(x -> modelMapper.map(x, ModulosActividadDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ModulosActividadDto>>(moduloActividadOut, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Modulo actividad", description = "Este metodo devuelve el modulo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getModuloActividad/{idModCurso}/{idDatProy}" })
	public ResponseEntity<ModulosActividadDto> getModuloActividad(@PathVariable("idModCurso") Long idModCurso, @PathVariable("idDatProy") Long idDatProy) {
		
		ModulosActividad moduloActividad = proyectosService.getModuloActividad(idModCurso, idDatProy);
		
		ModulosActividadDto moduloActividadDto = modelMapper.map(moduloActividad, ModulosActividadDto.class);
		
		return new ResponseEntity<ModulosActividadDto>(moduloActividadDto, HttpStatus.OK);
	}	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Modulo actividad", description = "Este metodo devuelve el modulo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getIsCheckedModuloActividad/{idModCurso}/{idDatProy}" })
	public ResponseEntity<Boolean> getIsCheckedModuloActividad(@PathVariable("idModCurso") Long idModCurso, @PathVariable("idDatProy") Long idDatProy) {
		
		Boolean isChecked = proyectosService.getIsCheckedModuloActividad(idModCurso, idDatProy);
		
		return new ResponseEntity<Boolean>(isChecked, HttpStatus.OK);
	}	
	
	/**
	 * Borrado de un proyecto.
	 *
	 * @param idProyecto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado de un Proyecto", description = "Este metodo borra los datos de un Proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteProyecto/{idProyecto}")
	public ResponseEntity<HttpStatus> deleteProyecto(
			@Parameter(description = "Identificador del Proyecto", required = true) @PathVariable("idProyecto") Long idProyecto) {
		try {
			proyectosService.deleteProyecto(idProyecto);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * count modulos proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "count modulos proyecto", description = "Este metodo devuelve count modulos proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/countModulosProyecto/{idProyecto}" })
	public ResponseEntity<Integer> countModulosProyecto(@PathVariable("idProyecto") Long idProyecto) {
		
		Integer numModulos = proyectosService.countModulosProyecto(idProyecto);		
		
		return new ResponseEntity<Integer>(numModulos, HttpStatus.OK);
	}
	
	/**
	 * count empresas proyecto.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "count empresas proyecto", description = "Este metodo devuelve count empresas proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/countEmpresasProyecto/{idProyecto}" })
	public ResponseEntity<Integer> countEmpresasProyecto(@PathVariable("idProyecto") Long idProyecto) {
		
		Integer numEmpresas = proyectosService.countEmpresasProyecto(idProyecto);		
		
		return new ResponseEntity<Integer>(numEmpresas, HttpStatus.OK);
	}
	
	//Get anexoII Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@GetMapping("/downloadAnexo2Proyecto/{idConvProy}/{idMatricula}")
	public void generateReport(HttpServletResponse response, 
			                   @PathVariable("idConvProy") Long idConvProy,
			                   @PathVariable("idMatricula") Long idMatricula) throws JRException, IOException {
		response.setContentType(APPLICATION);
	    response.setHeader(CONTENT, ATTACHMENT + "AnexoII_ProgramaFormativo" +"."+ "pdf");
	    response.setHeader(PRAGMA, NO_CACHE);
	    response.setHeader(CACHE, NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = proyectosService.exportReport(idConvProy,idMatricula);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	//Get anexoII Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@GetMapping("/downloadAnexo1AlumnadoProyecto/{idConvProy}")
	public void generateReportAnexoI(HttpServletResponse response, @PathVariable("idConvProy") Long idConvProy) throws JRException, IOException {
		response.setContentType(APPLICATION);
	    response.setHeader(CONTENT, ATTACHMENT + "AnexoI_Alumnado_Dual" +"."+ "pdf");
	    response.setHeader(PRAGMA, NO_CACHE);
	    response.setHeader(CACHE, NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = proyectosService.exportReportAnexoI(idConvProy);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
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
	@GetMapping("/getCentrosProyectosDelegacion/{idPerfil}/{idCentro}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getCentrosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("idProvincia") Long idProvincia,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> centros = proyectosService.getCentrosDelegacion(datosUsuario.getXUsuarioDelphos(), idPerfil, idCentro,idProvincia);
		
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
	@GetMapping("/getTutoresProyectosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idAnno}/{idTipo}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getTutoresDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("idCentroProvincia") Long idCentroProvincia, 
																			@PathVariable("idAnno") Integer idAnno,
																			@PathVariable("idTipo") Long idTipo,
																			@PathVariable("idProvincia") Long idProvincia,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		List<ElementoSelect> tutores = proyectosService.getTutoresDelegacion(datosUsuario.getXUsuarioDelphos(), 
																			 idPerfil, 
																		  	 idCentro,
																		     idCentroProvincia,
																		     idAnno,
																		     idTipo,
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
	@GetMapping("/getFamiliasProyectosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idAnno}/{idTipo}/{idTutor}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getFamiliasProyectosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			          @PathVariable("idCentro") Long idCentro,
																			          @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																			          @PathVariable("idAnno") Integer idAnno,
																			          @PathVariable("idTipo") Long idTipo,
																			          @PathVariable("idTutor") Long idTutor,
																			          @PathVariable("idProvincia") Long idProvincia,
																			          @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		List<ElementoSelect> familias = proyectosService.getFamiliasProyectosDelegacion(datosUsuario.getXUsuarioDelphos(), 
																				        idPerfil, 
																				        idCentro,
																				        idCentroProvincia,
																				        idAnno,
																				        idTipo,
																				        idTutor,
																				        idProvincia);
		
		List<ElementoSelectDto> familiasDto = familias.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(familiasDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Modalidad.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener Modalidades", description = "Este metodo devuelve el listado de modalidad de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModalidadesProyectosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idAnno}/{idTipo}/{idTutor}/{idFamilia}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getModalidadesDelegacion(@PathVariable("idPerfil") Long idPerfil,
																		        @PathVariable("idCentro") Long idCentro,
																		        @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																		        @PathVariable("idAnno") Integer idAnno,
																		        @PathVariable("idTipo") Long idTipo,
																		        @PathVariable("idTutor") Long idTutor,
																	   	        @PathVariable("idFamilia") Long idFamilia,
																	   	        @PathVariable("idProvincia") Long idProvincia,
																		        @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> cursos = proyectosService.getModalidadesDelegacion(datosUsuario.getXUsuarioDelphos(), 
																		        idPerfil, 
																		        idCentro,
																		        idCentroProvincia,
																		        idAnno,
																		        idTipo,
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
	@Operation(summary = "Lista de Proyectos", description = "Este metodo devuelve una lista con todos los proyectos de un centro",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllProyectosDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{idAnno}/{idTipo}/{idTutor}/{idFamilia}/{idModalidad}/{idProvincia}/{idCurso}")
	public ResponseEntity<List<ListadoProyectosDto>> getAllProyectosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																	           @PathVariable("idCentro") Long idCentro,
																	           @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																	           @PathVariable("idAnno") Integer idAnno, 
																	           @PathVariable("idTipo") Long idTipo,
																	           @PathVariable("idTutor") Long idTutor,
																   	           @PathVariable("idFamilia") Long idFamilia,
																   	           @PathVariable("idModalidad") Long idModalidad,
																   	           @PathVariable("idProvincia") Long idProvincia,
																			   @PathVariable("idCurso") Long idCurso,
																	           @RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ListadoProyectos> proyectos = proyectosService.getAllProyectosDelegacion(datosUsuario.getXUsuarioDelphos(), 
																					  idPerfil,
																					  idCentro, 
																					  idCentroProvincia,
																					  idAnno, 
																					  idTipo, 
																					  idTutor, 
																					  idFamilia, 
																					  idModalidad,
																					  idProvincia,
																					  idCurso);

		List<ListadoProyectosDto> proyectosDto = proyectos.stream().map(x -> modelMapper.map(x, ListadoProyectosDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ListadoProyectosDto>>(proyectosDto, HttpStatus.OK);
	
	}
	
	/**
	 * Delete convenio programas.
	 *
	 * @param ConveniosProgramasFctDto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Delete convenio proyectos", description = "Este metodo delete convenio programas", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteConvenioProyectos")
	public ResponseEntity<Object> deleteConvenioProgramas(
			@Parameter(description = "Convenio a proyecto", required = true) @RequestBody final List<ConveniosProyectosHorarioAlumnoFctDto> listConveniosProyectosHorAluFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
//			List<ConveniosProgramasFct> convenioProgramasFctListIn = ListConveniosProgramasFctDto.stream().map(entity -> modelMapper.map(entity, ConveniosProgramasFct.class)).collect(Collectors.toList());
		
			proyectosService.deleteConvenioProyectosHorarioAlumno(listConveniosProyectosHorAluFctDto);
		
			response = new ResponseEntity<>(null, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	/**
	 * Devuelve los horarios generales de un convenio proyecto
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Horarios generales de un convenio proyecto", description = "Este metodo devuelve los horarios generales de un convenio proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioProyectoPeriodosHorarios/{idConvProy}")
	public ResponseEntity<List<ConvProyHorPeriodoFctDto>> getConvenioProyectoPeriodosHorarios(@PathVariable("idConvProy") Long idConvProy){
		
		List<ConvProyHorPeriodoFctDto> periodosDto = proyectosService.getConvenioProyectoPeriodosHorarios(idConvProy);
		
		return new ResponseEntity<List<ConvProyHorPeriodoFctDto>>(periodosDto, HttpStatus.OK);		
	}
	
	/**
	 * Devuelve el horario personal de un convenio proyecto alumno
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Horario personal de un convenio proyecto alumno", description = "Este metodo devuelve el horario personal de un convenio proyecto alumno",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioProgramaPeriodosHorariosAlumnoProyecto/{idConvProy}/{idMatricula}")
	public ResponseEntity<List<ConvProyAluHorPeriodoFctDto>> getConvenioProyectoPeriodosHorariosAlumno(@PathVariable("idConvProy") Long idConvProy,
																								    @PathVariable("idMatricula") Long idMatricula){
		
		List<ConvProyAluHorPeriodoFctDto> periodosDto = proyectosService.getConvenioProyectoPeriodosHorariosAlumno(idConvProy, idMatricula);
		
		return new ResponseEntity<List<ConvProyAluHorPeriodoFctDto>>(periodosDto, HttpStatus.OK);		
	}
	
	/**
	 * Create convenios programa horario alumnado.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de convenios proyecto horario alumnado", description = "Este metodo crea convenio proyecto horario alumnado", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenioProyectoPeriodosHorarios")
	public ResponseEntity<Object> createConvenioProyectoPeriodosHorarios(
			@Parameter(description = "Convenio proyecto horario alumnado", required = true) @RequestBody final List<ConvProyHorPeriodoFctDto> listConvProyHorPeriodoFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);						
		
			List<ConvProyHorPeriodoFctDto> listConvProyHorPeriodoFctOut = proyectosService.createConvenioProyectoPeriodosHorarios(listConvProyHorPeriodoFctDto);
		
			response = new ResponseEntity<>(listConvProyHorPeriodoFctOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	/**
	 * Create convenios proyecto horario alumnado.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de convenios proyecto horario personal alumnado", description = "Este metodo crea convenio proyecto horario personal alumnado", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenioProyectoPeriodosHorariosAlumno")
	public ResponseEntity<Object> createConvenioProyectoPeriodosHorariosAlumno(
			@Parameter(description = "Convenio proyecto horario alumnado", required = true) @RequestBody final List<ConvProyAluHorPeriodoFctDto> listConvProyAluHorPeriodoFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);						
		
			List<ConvProyAluHorPeriodoFctDto> listConvProyHorPeriodoFctOut = proyectosService.createConvenioProyectoPeriodosHorariosAlumno(listConvProyAluHorPeriodoFctDto);
		
			response = new ResponseEntity<>(listConvProyHorPeriodoFctOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	/**
	 * Devuelve el número de alumnos por id convenio-proyecto
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Contador de alumnos por id convenio-proyecto", description = "Este metodo devuelve el numero de alumnos por id convenio-proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountAlumnosByConvenioProyectoId/{idConvProy}")
	public ResponseEntity<Integer> getCountByConvenioProyectoId(@PathVariable("idConvProy") Long idConvProy){
		return new ResponseEntity<Integer>(proyectosService.countByConvenioProyectoId(idConvProy), HttpStatus.OK);
		
	}	
	
	
	/**
	 * Datos de un Convenio-Proyecto.
	 *
	 * @param id Id del Convenio-Proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener el anno de un Convenio-Proyecto", description = "Este metodo devuelve el anno de un Convenio-Proyecto", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnnoConvenioProyecto/{idConvProy}")
	public ResponseEntity<Integer> getAnnoConvenioPrograma(
			@Parameter(description = "Identificador del convenio-proyecto", required = true) @PathVariable("idConvProy") Long idConvProy) {
		
		Integer anno = proyectosService.getAnnoConvenioProyecto(idConvProy);
		
		return new ResponseEntity<Integer>(anno, HttpStatus.OK);
	}
	
	
	/**
	 * Upload fichero para la autorizacion del desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Upload ficheros de Anexos", description = "Upload ficheros de Anexos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadFicherosAnexoProy/{idConvProy}/{tipo}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> uploadFicherosAnexo(@PathVariable("idConvProy") Long idConvProy,
																		  @PathVariable("tipo") String tipo,
																		  @RequestPart List<MultipartFile> ficheros) {
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			proyectosService.uploadFicherosAnexo(idConvProy, tipo, ficheros);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Anexos II", description = "Este metodo devuelve los identificadores de los anexos convenios proyectos", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnexoIIProyectos/{idConvProy}")
	public ResponseEntity<Object> getAnexoIIProyectos(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("idConvProy") Long idConvProy) {
						
		
		try {
		
			List<ConveniosProyectoAnexos> anexosIn = proyectosService.getAnexoII(idConvProy);
			
			List<ConveniosProyectoAnexosDto> anexosInDto = anexosIn.stream().map(entity -> modelMapper.map(entity, ConveniosProyectoAnexosDto.class)).collect(Collectors.toList());
		
			return new ResponseEntity<>(anexosInDto, HttpStatus.OK);	
			
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	/**
	 * Upload ficheros de los partes semanales del seguimiento del alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Upload ficheros de partes semanales", description = "Upload ficheros de Partes semanales del seguimiento del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadFicherosParsemAnexoProy/{idConvProyAlu}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ParteSemanalAnexosProyectoDto>> uploadFicherosParsemAnexoProy(@PathVariable("idConvProyAlu") Long idConvProyAlu,
																					   @RequestPart List<MultipartFile> files) {

		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ParteSemanalAnexosProyecto> parsemAluProy = proyectosService.uploadFilesParteSemanal(idConvProyAlu, files);

			List<ParteSemanalAnexosProyectoDto> listParsemAneProy = parsemAluProy.stream().map(x -> modelMapper.map(x, ParteSemanalAnexosProyectoDto.class)).collect(Collectors.toList());

			return new ResponseEntity<>(listParsemAneProy, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Get de ficheros de los partes semanales del seguimiento del alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT', 'ALU')")
	@Operation(summary = "Get ficheros de partes semanales", description = "Obtener ficheros de Partes semanales del seguimiento del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping(value="/getFicherosParsemAnexoProy/{idConvProyAlu}")
	public ResponseEntity<List<ParteSemanalAnexosProyectoDto>> getFicherosParsemAnexoProy(@PathVariable("idConvProyAlu") Long idConvProyAlu) {

		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ParteSemanalAnexosProyecto> parsemAluProy = proyectosService.getFilesParteSemanal(idConvProyAlu);

			List<ParteSemanalAnexosProyectoDto> listParsemAneProy = parsemAluProy.stream().map(x -> modelMapper.map(x, ParteSemanalAnexosProyectoDto.class)).collect(Collectors.toList());

			return new ResponseEntity<>(listParsemAneProy, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "", description = "",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/checkOverlappingDatesProy")
	public ResponseEntity<Integer> checkOverlappingDatesProy(@RequestParam("idConvenio") Long idConvenio,
														@RequestParam("idProyecto") Long idProyecto,
														@RequestParam("fechaInicio") String fechaInicio,
														@RequestParam("fechaFin") String fechaFin,
														@RequestParam("idResponsable") Long idResponsable) {

		try {

			return new ResponseEntity<Integer>(proyectosService.checkOverlappingDates(idConvenio, idProyecto, fechaInicio, fechaFin, idResponsable), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "", description = "",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PutMapping("/updateProyectDates")
	public ResponseEntity<Object> updateProgramDates(@RequestParam("idConvProy") Long idConvProy,
													 @RequestParam("fechaInicio") String fechaInicio,
													 @RequestParam("fechaFin") String fechaFin,
													 @RequestParam("newHora") Integer newHora) {

		try {

			proyectosService.updateProyectDates(idConvProy, fechaInicio, fechaFin,newHora);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Proyecto está siendo usado", description = "Programa está siendo usado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getProyectoUsado/{id}"})
	public ResponseEntity<Integer> getProyectoUsado(@PathVariable("id") Long id) {
		
        Integer usado = proyectosService.getProyectoUsado(id);
        
		return new ResponseEntity<Integer>(usado, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener cursos", description = "Este método devuelve el listado de cursos según centro, año, tipo de proyecto, tutor, familia y modalidad",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")
	})
	@GetMapping("/getCursosCentroAnnoTutorFamiliaModalidad/{idCentro}/{cAnno}/{idTipoProyecto}/{idTutor}/{idFamilia}/{idModalidad}")
	public ResponseEntity<List<ElementoSelectDto>> getCursosCentroAnnoTutorFamiliaModalidad(
			@PathVariable("idCentro") Long idCentro,
			@PathVariable("cAnno") Integer cAnno,
			@PathVariable("idTipoProyecto") Long idTipoProyecto,
			@PathVariable("idTutor") Long idTutor,
			@PathVariable("idFamilia") Long idFamilia,
			@PathVariable("idModalidad") Long idModalidad,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException {

		List<ElementoSelect> cursos = proyectosService.getCursosCentroAnnoTutorFamiliaModalidad(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);

		List<ElementoSelectDto> cursosDto = cursos.stream()
				.map(curso -> modelMapper.map(curso, ElementoSelectDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(cursosDto, HttpStatus.OK);
	}

	/**
	 * Asocia Convenios a proyectos.
	 *
	 * @param IdProyeto Id del proyecto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Asocia Modulos a un proyecto", description = "Asocia Modulos a un proyecto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createModuloPlan/{idProyecto}")
	public ResponseEntity<List<ModulosCursoDto>> createModuloPlan(@RequestBody final List<ModulosCursoDto> modulosDto, @PathVariable("idProyecto") Long idProyecto) {
		try {

			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ModulosCurso> modulos = modulosDto.stream().map(x -> modelMapper.map(x, ModulosCurso.class)).collect(Collectors.toList());

			modulos = proyectosService.createModuloPlan(modulos, idProyecto);

			List<ModulosCursoDto> modulosDtoUpdate = modulos.stream().map(x -> modelMapper.map(x, ModulosCursoDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ModulosCursoDto>>(modulosDtoUpdate, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/actualizarInfoAdicionalPlan")
	@Operation(
			summary = "Actualizar información adicional del plan",
			description = "Crea o actualiza la información adicional del plan para un proyecto específico",
			responses = {
					@ApiResponse(responseCode = "200", description = "Información adicional actualizada correctamente"),
					@ApiResponse(responseCode = "400", description = "Error en los datos proporcionados"),
					@ApiResponse(responseCode = "404", description = "Proyecto o información adicional no encontrado"),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor")
			}
	)
	public ResponseEntity<InfoAdicionalPlanDto> actualizarInfoAdicionalPlan(
			@Parameter(description = "Datos de la información adicional del plan", required = true)
			@RequestBody InfoAdicionalPlanDto infoAdicionalPlanDto
	) {
		try {
			InfoAdicionalPlan infoAdicionalPlan = modelMapper.map(infoAdicionalPlanDto, InfoAdicionalPlan.class);

			InfoAdicionalPlan savedEntity;
			if (infoAdicionalPlan.getId() != null) {
				// Actualización
				savedEntity = proyectosService.updateInfoAdicionalPlan(infoAdicionalPlan);
				if (savedEntity == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(null); // Información adicional no encontrada
				}
			} else {
				// Creación
				savedEntity = proyectosService.createInfoAdicionalPlan(infoAdicionalPlan);
				if (savedEntity == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(null); // Proyecto no encontrado
				}
			}

			InfoAdicionalPlanDto savedDto = modelMapper.map(savedEntity, InfoAdicionalPlanDto.class);
			return ResponseEntity.ok(savedDto);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/getInfoAdicionalPlan/{idProyecto}")
	@Operation(
			summary = "Obtener información adicional del plan",
			description = "Obtiene los datos de la información adicional del plan para un proyecto específico",
			responses = {
					@ApiResponse(responseCode = "200", description = "Datos obtenidos correctamente"),
					@ApiResponse(responseCode = "404", description = "Información adicional del plan no encontrada"),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor")
			}
	)
	public ResponseEntity<InfoAdicionalPlanDto> getInfoAdicionalPlan(
			@Parameter(description = "ID del proyecto", required = true) @PathVariable Long idProyecto
	) {
		try {
			InfoAdicionalPlan infoAdicionalPlan = proyectosService.getInfoAdicionalPlanByIdProyecto(idProyecto);
			InfoAdicionalPlanDto infoAdicionalPlanDto;
			if (infoAdicionalPlan == null) {
				infoAdicionalPlanDto = new InfoAdicionalPlanDto();
			} else {
				infoAdicionalPlanDto = modelMapper.map(infoAdicionalPlan, InfoAdicionalPlanDto.class);
			}
			return ResponseEntity.ok(infoAdicionalPlanDto);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@GetMapping("/downloadAnexo2Plan/{idConvProy}/{idMatricula}")
	public void generateReportPlan(HttpServletResponse response,
							   @PathVariable("idConvProy") Long idConvProy,
							   @PathVariable("idMatricula") Long idMatricula) throws JRException, IOException {
		response.setContentType(APPLICATION);
		response.setHeader(CONTENT, ATTACHMENT + "AnexoII_LOFP" +"."+ "pdf");
		response.setHeader(PRAGMA, NO_CACHE);
		response.setHeader(CACHE, NO_CACHE);
		response.setStatus(200);

		byte [] documento = proyectosService.exportReportPlan(idConvProy,idMatricula);

		InputStream is = new ByteArrayInputStream(documento);
		FileCopyUtils.copy(is, response.getOutputStream());
		response.flushBuffer();

	}


	//Get anexoI plan Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@GetMapping("/downloadAnexo1AlumnadoPlan/{idConvProg}")
	public void generateReportAnexoIPlan(HttpServletResponse response, @PathVariable("idConvProg") Long idConvProg) throws JRException, IOException {
		response.setContentType(APPLICATION);
		response.setHeader(CONTENT, ATTACHMENT + "AnexoI_Alumnado_Plan" +"."+ "pdf");
		response.setHeader(PRAGMA, NO_CACHE);
		response.setHeader(CACHE, NO_CACHE);
		response.setStatus(200);

		byte [] documento = proyectosService.exportReportAnexoIPlan(idConvProg);

		InputStream is = new ByteArrayInputStream(documento);
		FileCopyUtils.copy(is, response.getOutputStream());
		response.flushBuffer();

	}




	/**
	 *  Metodo para generar un parte semanal para el alumno.
	 *
	 * @param response
	 * @param idParsemAluPlan
	 * @throws JRException
	 * @throws IOException
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT')")
	@GetMapping("/getParteSemanalPdfPlan/{idParsemAluPlan}")
	public void generateParteSemanalReportPlan
	(HttpServletResponse response,@PathVariable ("idParsemAluPlan") Long idParsemAluPlan) throws JRException, IOException {
		response.setContentType(APPLICATION);
		response.setHeader(CONTENT, ATTACHMENT + "ParteSemanalAlumnado" +"."+ "pdf");
		response.setHeader(PRAGMA, NO_CACHE);
		response.setHeader(CACHE, NO_CACHE);
		response.setStatus(200);

		byte [] documento = proyectosService.exportParteSemanalReportPlan(idParsemAluPlan);

		InputStream is = new ByteArrayInputStream(documento);
		FileCopyUtils.copy(is, response.getOutputStream());
		response.flushBuffer();

	}

	/**
	 * Método para generar un parte semanal para el alumno basado en idConvProyAlu y fechaInicioSem.
	 *
	 * @param response
	 * @param idConvProyAlu
	 * @param fechaInicioSem
	 * @return ResponseEntity con el estado de la operación
	 * @throws IOException
	 */
	@GetMapping("/getParteSemanalPlanPdf/{idConvProyAlu}/{fechaInicioSem}")
	public ResponseEntity<Object> generateParteSemanalReportPlan2(
			HttpServletResponse response,
			@PathVariable("idConvProyAlu") Long idConvProyAlu,
			@PathVariable("fechaInicioSem") String fechaInicioSem) throws JRException {

		// Convertir la fecha desde String a Date
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaInicio;
		try {
			fechaInicio = dateFormat.parse(fechaInicioSem);
		} catch (java.text.ParseException e) {
			// Devolver un ResponseEntity con el estado 500 y un mensaje
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Formato de fecha inválido. Debe ser dd-MM-yyyy.");
		}

		// Obtener el ID del parte semanal
		Optional<Long> idParsemOpt = parsemAluplanService.obtenerIdParsemPorConvProyYFecha(idConvProyAlu, fechaInicio);

		// Verificar si no se encontró el ID
		if (idParsemOpt.isEmpty()) {
			// Devolver un ResponseEntity con estado 200 y cuerpo null
			return ResponseEntity.ok().body(null);
		}

		Long idParsemAluPlan = idParsemOpt.get();

		try {
			// Generar el documento PDF
			byte[] documento = proyectosService.exportParteSemanalReportPlan(idParsemAluPlan);

			// Configurar la respuesta HTTP para la descarga del archivo PDF
			response.setContentType(APPLICATION);
			response.setHeader(CONTENT, ATTACHMENT + "ParteSemanalAlumnado" +"."+ "pdf");
			response.setHeader(PRAGMA, NO_CACHE);
			response.setHeader(CACHE, NO_CACHE);
			response.setStatus(200);
			// Enviar el documento PDF en la respuesta
			InputStream is = new ByteArrayInputStream(documento);
			FileCopyUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al generar el documento PDF.");
		}
	}

	@PostMapping("/getPartesSemanalesDtos")
	@Operation(
			summary = "Obtiene un listado de DTOs de partes semanales",
			description = "Obtiene los DTOs de partes semanales basados en el ID de convenio y una lista de fechas.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
					@ApiResponse(responseCode = "400", description = "Datos de entrada no válidos"),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor")
			}
	)
	public ResponseEntity<List<ParsemAluplanDto>> getPartesSemanalesDtos(
			@RequestBody Map<String, Object> requestBody) {

		try {
			// Extraer el idConvProyAlu y fechas del cuerpo de la petición
			Long idConvProyAlu = requestBody.containsKey(ID_CONVALU) ? Long.valueOf(requestBody.get(ID_CONVALU).toString()) : null;
			List<String> fechas = requestBody.containsKey(FECHAS) ? (List<String>) requestBody.get(FECHAS) : null;

			// Validar entradas
			if (idConvProyAlu == null || fechas == null || fechas.isEmpty()) {
				return ResponseEntity.badRequest().body(Collections.emptyList());
			}


			// Llamar al servicio para obtener los DTOs
			List<ParsemAluplanDto> partesDtos = parsemAluplanService.obtenerPartesSemanales(
					idConvProyAlu,
					fechas
			);

			// Devolver los DTOs como respuesta
			return ResponseEntity.ok(partesDtos);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}



	/**
	 * Obtiene la fecha de finalización de alta en la SS
	 *
	 * @param idConvProyAlu
	 * @param idMatricula
	 * @return
	 */
	@Operation(summary = "Obtener fecha finalización de alta en la seguridad social", description = "Este metodo devuelve la fecha de finalización del alta en la seguridad Social",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechaFinAltaSSDual/{idConvProyAlu}/{idMatricula}")
	public ResponseEntity<Date> getFechaFinAltaSSDual(@PathVariable("idConvProyAlu") Long idConvProyAlu,
												      @PathVariable("idMatricula") Long idMatricula){

		Date fechaFin = proyectosService.obtenerFechaFinAltaSSDual(idConvProyAlu, idMatricula);
		return ResponseEntity.ok(fechaFin);

	}
	
	@PostMapping("/getInfoParteSemanal")
	@Operation(
			summary = "Obtener informacion del parte semanal",
			description = "Obtiene los datos de la información informacion del parte semanal",
			responses = {
					@ApiResponse(responseCode = "200", description = "Datos obtenidos correctamente"),
					@ApiResponse(responseCode = "404", description = "Información adicional del plan no encontrada"),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor")
			}
	)
	public ResponseEntity<List<InfoParteSemanalDto>> getInfoParteSemanal(
			@RequestBody Map<String, Object> requestBody) 
	{
		try {
			// Extraer el idConvProyAlu y fechas del cuerpo de la petición
			Long idConvProyAluPar = requestBody.containsKey(ID_CONVALU) ? Long.valueOf(requestBody.get(ID_CONVALU).toString()) : null;
			List<String> fechasPar = requestBody.containsKey(FECHAS) ? (List<String>) requestBody.get(FECHAS) : null;

			// Validar entradas
			if (idConvProyAluPar == null || fechasPar == null || fechasPar.isEmpty()) {
				return ResponseEntity.badRequest().body(Collections.emptyList());
			}


			// Llamar al servicio para obtener los DTOs
			List<InfoParteSemanalDto> partesDtos = parsemAluplanService.getInfoParteSemanal(
					idConvProyAluPar,
					fechasPar
			);

			// Devolver los DTOs como respuesta
			return ResponseEntity.ok(partesDtos);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

}
