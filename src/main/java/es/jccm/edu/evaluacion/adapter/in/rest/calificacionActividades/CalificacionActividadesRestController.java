package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaActividadService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.AlumnoValoracionActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.MateriaCursoGenericaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.MateriasValoracionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.UnidadesValoracionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.OfertaMatriculaGenericoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.PonderacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.application.domain.evaluacion.MateriasValoracion;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaCalificacionActividadesService;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaMateriaCursoOfertaMatriculaGenericaService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaOfertaMatriculaGenericoService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(BasePath.EvaluacionBasePath + "/calificacionActividades")
@Tag(name = "Servicio Calificación de Actividades", description = "Servicio para recuperar las Calificaciones de Actividades")
@CrossOrigin
public class CalificacionActividadesRestController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IEvaCalificacionActividadesService calificacionActividadesService;
	
	@Autowired
	private IEvaOfertaMatriculaGenericoService ofertaMatriculaGenericoService;
	
	@Autowired
	private IEvaMateriaCursoOfertaMatriculaGenericaService materiaCursoOfertaMatriculaGenericaService;

	@Autowired
	private IEvaActividadService evaActividadService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	/**
	 *Método convocatorias de una programacion de aula
	 *@param idProgramacionAula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista alumnos por grupo del año académico indicado", description = "Método convocatorias de una programacion de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvocatorias")
	public ResponseEntity<List<ConvocatoriasDto>> getConvocatorias(@RequestParam("idProgramacionAula") Long idProgramacionAula) {
		List<ConvocatoriasDto> list = calificacionActividadesService.getConvocatorias(idProgramacionAula);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	/**
	 * Método obtener unidades de programación
	 * @param idConvCentroOmc
	 * @param idProgramacionAula
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Método coger unidades de programación ", description = "Método coger unidades de programación ", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesProgramacionProgAula")
	public ResponseEntity<List<UnidadProgramacionDTO>> getUnidadesProgramacionProgAula(@RequestParam("idConvCentroOmc") Long idConvCentroOmc,
																					   @RequestParam("idProgramacionAula") Long idProgramacionAula,
																					   @RequestParam(value = "idUnidadProgramacion", required = false) Long idUnidadProgramacion) {
		List<UnidadProgramacionDTO> list = calificacionActividadesService.getUnidadesProgramacionProgAula(idConvCentroOmc, idProgramacionAula, idUnidadProgramacion);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	/**
	 *Método coger programaciones de aula
	 *@param idOfermatrig
	 *@param idMateriaOmg
	 *@param idCentro
	 *@param anno
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Método coger programaciones de aula", description = "Método coger programaciones de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getProgramacionesAula")
	public ResponseEntity<List<ProgramacionAulaDTO>> getProgramacionesAula(@RequestParam("idOfermatrig") Long idOfermatrig,
																		   @RequestParam("idMateriaOmg") Long idMateriaOmg,
																		   @RequestParam("idCentro") Long idCentro,
																		   @RequestParam("anno") Integer anno,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt,
																		   @RequestParam("idsEmpleadosCompartidas") List<Long> idsEmpleadosCompartidas,
																		   @RequestParam("director") Boolean director) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		idsEmpleadosCompartidas.add(datosUsuario.getIdEmpleadoDelphos());

		List<ProgramacionAulaDTO> list = calificacionActividadesService.getProgramacionesAulaByDidactica(idOfermatrig, idMateriaOmg, idCentro, anno, idsEmpleadosCompartidas, director);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 *Método que obtiene un curso a partir de su id
	 *
	 *@param idOfertaMatrig Id.
	 *
	 *@return el curso cuyo id es pasado por parámetro
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Método que obtiene un curso a partir de su id", description = "Método que obtiene un curso a partir de su id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursoByOfertaMatrig")
	public ResponseEntity<OfertaMatriculaGenericoDTO> getCursoByOfertaMatrig(@RequestParam("idOfertaMatrig") Long idOfertaMatrig) {
		
		try {
			OfertaMatriculaGenericoDTO curso = ofertaMatriculaGenericoService.getCursoByOfertaMatrig(idOfertaMatrig);
            return new ResponseEntity<>(curso, HttpStatus.OK);
            
    	} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener el curso", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 *Método que obtiene una materia a partir de su id
	 *
	 *@param idMateriaOmg Id. materia
	 *
	 *@return la materia cuyo id es pasado por parámetro
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Método que obtiene una materia a partir de su id", description = "Método que obtiene una materia a partir de su id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMateria")
	public ResponseEntity<MateriaCursoGenericaDTO> getMateria(@RequestParam("idMateriaOmg") Long idMateriaOmg) {
		
		try {
			MateriaCursoGenericaDTO materia = materiaCursoOfertaMatriculaGenericaService.getMateria(idMateriaOmg);
            return new ResponseEntity<>(materia, HttpStatus.OK);
            
    	} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener la materia", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 *Método que obtiene la ponderación asociada a la programación didáctica
	 *
	 *@param idProgramacionDidactica Id. materia
	 *
	 *@return la ponderación asociada a la programación didáctica cuyo id es pasado por parámetro
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Método que obtiene la ponderación asociada a la programación didáctica", description = "Método que obtiene la ponderación asociada a la programación didáctica", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPonderacionByProgramacionDidactica")
	public ResponseEntity<PonderacionDTO> getPonderacionByProgramacionDidactica(@RequestParam("idProgramacionDidactica") Long idProgramacionDidactica) {
		
		try {
			PonderacionDTO ponderacion = calificacionActividadesService.getPonderacionByProgramacionDidactica(idProgramacionDidactica);
            return new ResponseEntity<>(ponderacion, HttpStatus.OK);
            
    	} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener la ponderación asociada a la programación didáctica", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Devuelve la lista de alumnos de una programación de aula con sus calificaciones de los criterios de las actividades correspondientes a una unidad de una convocatoria
     *
     * @param idProgramacionAula
     * @param convCentroOmc
     * @param idUnidadProgramacion
     * @return the response entity
     */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de alumnos de una programación de aula con sus calificaciones de los criterios de las actividades", description = "Este metodo devuelve lista de alumnos de una programación de aula con sus calificaciones de los criterios de las actividades correspondientes a una unidad de programación de una convocatoria", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosCalificacionesUnidad")
	public ResponseEntity<List<AlumnoValoracionActividadDTO>> getAlumnosCalificacionesUnidad(@RequestParam("idProgramacionAula") Long idProgramacionAula,
																							 @RequestParam("idConvCentroOmc") Long convCentroOmc,
																							 @RequestParam("idUnidadProgramacion") Long idUnidadProgramacion,
																							 @RequestParam("idMateriaOmg") Long idMateriaOmg,
																							 @RequestParam("idUnidadCentro") Long idUnidadCentro) {
		try {
			List<AlumnoValoracionActividadDTO> alumnos = calificacionActividadesService.getAlumnosCalificacionesUnidad(idProgramacionAula, convCentroOmc, idUnidadProgramacion, idMateriaOmg, idUnidadCentro);
			return new ResponseEntity<>(alumnos, HttpStatus.OK);
		} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener el listado de alumnos con sus calificaciones asociado a la programación de aula, a la convocatoria y a la unidad de programación", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
     * Devuelve la lista de alumnos de una actividad con sus respectivas calificaciones de los criterios de evaluación
     *
     * @param idActividad
     * @return the response entity
     */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de alumnos de una actividad con sus respectivas calificaciones de los criterios de evaluación", description = "Este método devuelve lista de alumnos de una actividad con sus respectivas calificaciones de los criterios de evaluación", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosCalificacionesActividad")
    public ResponseEntity<List<AlumnoValoracionActividadDTO>> getAlumnosCalificacionesActividad(@RequestParam("idActividad") Long idActividad,
																								@RequestParam("idMateriaOmg") Long idMateriaOmg,
																								@RequestParam("idUnidadCentro") Long idUnidadCentro) {
		
		try {
			List<AlumnoValoracionActividadDTO> alumnos = calificacionActividadesService.getAlumnosCalificacionesActividad(idActividad, idMateriaOmg, idUnidadCentro);
			return new ResponseEntity<>(alumnos, HttpStatus.OK);
		} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener el listado de alumnos con sus calificaciones asociado a la programación de aula, a la convocatoria y a la unidad de programación", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * Devuelve la lista de alumnos de una actividad con sus respectivas calificaciones de los criterios de evaluación
	 *
	 * @param idActividad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de alumnos de una actividad para descargar el report", description = "Este método devuelve una lista de alumnos de una actividad para descargar el report", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getAlumnosActividadReport")
	public ResponseEntity<List<AlumnoValoracionActividadDTO>> getAlumnosActividadReport(@RequestParam("idActividad") Long idActividad,
																						@RequestParam("idMateriaOmg") Long idMateriaOmg,
																						@RequestParam("idUnidadCentro") Long idUnidadCentro) {

		try {
			List<AlumnoValoracionActividadDTO> alumnos = calificacionActividadesService.getAlumnosActividadReport(idActividad, idMateriaOmg, idUnidadCentro);
			return new ResponseEntity<>(alumnos, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de alumnos", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Guarda una calificación de un criterio de evaluación de una actividad del alumno
	 * 
	 * @param idCriterio
	 * @RequestBody AlumnoValoracionActividadDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Guarda una calificación de un criterio de evaluación de una actividad del alumno", description = "Este método guarda una calificación de un criterio de evaluación de una actividad del alumno",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/guardarCalificacionAlumno")
    public HttpStatus guardarCalificacionCriteriosActividadAlumno(@RequestBody AlumnoValoracionActividadDTO alumno, @RequestParam("idCriterio") Long idCriterio, 
			    		@RequestParam("idActividad") Long idActividad, 
			    		@RequestParam("idPonderacion") Long idPonderacion,
			    		@RequestParam("anno") Long anno,
			    		@RequestParam("idProgramacionAula") Long idProgramacionAula) throws Exception {
		try {
			calificacionActividadesService.guardarCalificacionCriteriosActividadAlumno(alumno, idCriterio, idActividad, idPonderacion, idProgramacionAula, anno);
			return HttpStatus.OK;
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener al guardar la calificacion del alumno", ex);
			throw new Exception("No se ha podido guardar la calificación del alumno");
        }
	}
	
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Bloquea una ponderación", description = "Bloquea una ponderación para que no se pueda editar",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/bloquearPonderacion")
    public HttpStatus bloquearPonderacion(@RequestParam("idPonderacion") Long idPonderacion) {

        try {
        	calificacionActividadesService.bloquearPonderacion(idPonderacion);
            return HttpStatus.OK;
        } catch (Exception ex) {
        	log.error("Se ha producido un error al bloquear la ponderación", ex);
            return HttpStatus.BAD_REQUEST;
        }
    }
	
	/**
     * Devuelve la lista de criterios de evaluación de una actividad
     *
     * @param idActividad
     * @return the response entity
     */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de criterios de evaluación de una actividad", description = "Este método devuelve lista de criterios de evaluación de una actividad", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getCriteriosByActividad")
    public ResponseEntity<List<CriterioEvaluacionDTO>> getCriteriosByActividad(@RequestParam("idActividad") Long idActividad) {
		try {
			List<CriterioEvaluacionDTO> criterios = calificacionActividadesService.getCriteriosByActividad(idActividad);
			return new ResponseEntity<>(criterios, HttpStatus.OK);
		} catch (Exception ex) {
    		log.error("Se ha producido un error al obtener el listado de criterios de evaluación de la actividad", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Devuelve la lista de materias de una programación de aula
	 *
	 * @param anno
	 * @param codigoCentro
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Lista de materias de una programación de aula", description = "Este método devuelve la lista de materias de una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getMateriasProgAula")
	public ResponseEntity<List<MateriasValoracionDto>> getMateriasProgAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																		   @RequestParam("anno") Long anno,
																		   @RequestParam("codigoCentro") Long codigoCentro,
																		   @RequestParam("idOfertaMatrig") Long idOfertaMatrig) { //Usamos este parámetro en caso de que sea director
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<MateriasValoracion> materias = calificacionActividadesService.getMateriasProgAula(datosUsuario.getIdEmpleadoDelphos(), anno, codigoCentro, idOfertaMatrig);
			List<MateriasValoracionDto> materiasOut = materias.stream().map(materia -> modelMapper.map(materia, MateriasValoracionDto.class)).collect(Collectors.toList());

			return new ResponseEntity<>(materiasOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de criterios de evaluación de la actividad", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Devuelve la lista de actividades por convocatoria
	 *
	 * @param idUnidadProgramacion
	 * @param idConvCentroOmc
	 * @param idProgramacionAula
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de actividades por convocatoria", description = "Este método devuelve la lista de actividades por convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getAllActividadesByConvocatoria")
	public ResponseEntity<List<ActividadDTO>> getAllActividadesByConvocatoria(@RequestParam("idUnidadProgramacion") Long idUnidadProgramacion,
																			  @RequestParam("idConvCentroOmc") Long idConvCentroOmc,
																			  @RequestParam("idProgramacionAula") Long idProgramacionAula) {
		try {
			List<ActividadDTO> actividades = evaActividadService.findActividadesByUnidadProgramacionAndConvocatoriaAndProgramacionAula(idUnidadProgramacion, idConvCentroOmc, idProgramacionAula);
			return new ResponseEntity<>(actividades, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado actividades", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Devuelve la lista de unidades de una programación de aula
	 *
	 * @param idProgramacionAula
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de unidades de una programación de aula", description = "Este método devuelve la lista de unidades de una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getUnidadesCentro")
	public ResponseEntity<List<UnidadesValoracionDto>> getUnidadesCentro(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																		   @RequestParam("idProgramacionAula") Long idProgramacionAula,
																		   @RequestParam("idActividad") Long idActividad,
																		   @RequestParam("idUnidadProg") Long idUnidadProg) {
		try {
			List<UnidadesValoracionDto> unidades = calificacionActividadesService.getUnidadesCentro(idProgramacionAula, idActividad, idUnidadProg);
			return new ResponseEntity<>(unidades, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de uynidades de la programación de aula", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
