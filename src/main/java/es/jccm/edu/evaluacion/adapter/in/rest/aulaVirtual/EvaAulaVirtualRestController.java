package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualListDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.CalificacionActividadesFilter;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.CriteriosActividadesFilter;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ProgramacionAulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.application.ports.in.aulaVirtual.IEvaAulaVirtualService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(BasePath.EvaluacionBasePath + "/aulaVirtual")
@Tag(name = "Servicio Aula Virtual", description = "Servicio Aula Virtual")
@CrossOrigin

public class EvaAulaVirtualRestController {
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	

	@Autowired
	private IEvaAulaVirtualService aulaVirtualService;

	/**
     * Devuelve las url de las aulas virtuales de las clases de un profesor
     *
     * @param jwt Datos del usuario logado
     * @param anno anno
     * @return List<AulaVirtualListDto> lista de aulas virtuales asociados al profesor
	 *         logado junto con el número de alumnos de cada una de ellas
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de aulas virtuales asociados al profesor logado junto con el número de alumnos de cada una de ellas", 
    	description = "Este método devuelve la lista de aulas virtuales asociados al profesor logado junto con el número de alumnos de cada una de ellas", responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("getAulasVirtualesProfesorByAnno")
    public ResponseEntity<List<AulaVirtualListDTO>> getAulasVirtualesProfesorByAnno(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
    												@RequestParam("idEmpleados") List<Long> idEmpleados,
    												@RequestParam("anno") Integer anno){
	    try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			idEmpleados.add(datosUsuario.getIdEmpleadoComunica());
			
			List<AulaVirtualListDTO> aulasVirtualesList = aulaVirtualService.getAulasVirtuales(idEmpleados, anno);
		    return new ResponseEntity<>(aulasVirtualesList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de aulas virtuales asociados al profesor", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Lista de Alumnos de un aula virtual
	 *
	 * @param idProgramacionAula
	 * @param idAula
	 * @return List<AlumnosPorMateriaDTO> Lista de alumnos de un aula virtual comparando con programación de aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de alumnos de un aula virtual comparando con programación de aula", description = "", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("getAlumnosAula")
	public ResponseEntity<List<AlumnosPorMateriaDTO>> getAlumnosAulas(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("idAula") Long idAula) {
		try {
			
			List<AlumnosPorMateriaDTO> alumnos = aulaVirtualService.getAlumnosAula(idProgramacionAula, idAula);

		return new ResponseEntity<>(alumnos, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de aulas virtuales asociados al profesor", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Vincula el Aula Virtual con la Programación de Aula
	 *
	 * @param idProgramacionAula Id. de la programación aula
	 * @param idAula Id. del aula virtual
	 * @return true si se ha vinculado correctamente, false en otro caso
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Vincula el Aula Virtual de la Programación de Aula", description = "Vincula el Aula Virtual de la Programación de Aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("vincularAulaVirtual")
	public ResponseEntity<Boolean> vincularAulaVirtual(@RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("idAula") Long idAula) {
		try {
			Boolean vinculado = aulaVirtualService.vincularAulaVirtual(idProgramacionAula, idAula);
		return new ResponseEntity<>(vinculado, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al vincular el aula virtual con la prog. de aula", ex);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Comprueba el alumnado del Aula virtual con el alumnado de la programación aula y devuelve la lista de alumnos de la prog. aula especificada, 
	 * indicando si se encuentran cada uno de ellos o no en el aula virtual indicada
	 *
	 * @param idProgramacionAula Id. de la programación aula
	 * @param idAula Id. del aula virtual
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "La lista de alumnos de la prog. aula especificada, indicando si se encuentran cada uno de ellos o no en el aula virtual indicada", description = "", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("comprobarAlumnado")
	public ResponseEntity<List<AlumnoDTO>> comprobarAlumnado(@RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("idAula") Long idAula) {
		try {
			List<AlumnoDTO> alumnos = aulaVirtualService.comprobarAlumnado(idProgramacionAula, idAula);
		return new ResponseEntity<>(alumnos, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener comprobar el listado de alumnos del aula virtual y la prog. de aula", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Actualiza la fecha de la ultima vez en la que se comprobaron si los alumnos 
	 * de la prog. de aula coinciden con los de moodle
	 *
	 * @param idProgramacionAula Id. de la programación aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "La fecha en la que se comprobó por última vez que los alumnos de la programación de aula y los de moodle coinciden", description = "", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("actualizaFechaComprobarAlumnado")
	public ResponseEntity<ProgramacionAulaDTO> actualizaFechaComprobarAlumnado(@RequestParam("idProgramacionAula") Long idProgramacionAula) {
		try {
			ProgramacionAulaDTO fechaActualizada = aulaVirtualService.actualizaFechaComprobarAlumnado(idProgramacionAula);
		return new ResponseEntity<>(fechaActualizada, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener la fecha actualizada", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Desvincula el Aula Virtual de la Programación de Aula
	 *
	 * @param idProgramacionAula Id. de la programación aula
	 * @param mantenerDatos Indica si hay que mantener los datos o eliminarlos
	 * @return true si se ha desvinculado correctamente, false en otro caso
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Desvincula el Aula Virtual de la Programación de Aula", description = "Desvincula el Aula Virtual de la Programación de Aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("desvincularAulaVirtual")
	public ResponseEntity<Boolean> desvincularAulaVirtual(@RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("mantenerDatos") Boolean mantenerDatos) {
		try {
			Boolean vinculado = aulaVirtualService.desvincularAulaVirtual(idProgramacionAula, mantenerDatos);
		return new ResponseEntity<>(vinculado, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al desvincular el aula virtual con la prog. de aula", ex);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
     * Devuelve la lista de actividades de un aula virtual
     *
     * @param programacionAula Prog. Aula
     * @param idAula Id. Aula virtual
     * @return List<ActividadDTO> lista de actividades del aula virtual
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de actividades del aula virtual", 
    	description = "Este método devuelve la lista de actividades del aula virtual", responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("getActividadesAula")
    public ResponseEntity<List<ActividadDTO>> getActividadesAula(@RequestBody ProgramacionAulaDTO programacionAula, @RequestParam("idAula") Long idAula){
	    try {
			
			List<ActividadDTO> actividadesList = aulaVirtualService.getActividadesAula(programacionAula, idAula);
		    return new ResponseEntity<>(actividadesList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de actividades del aula virtual con id: " + idAula, ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	/**
     * Devuelve la lista de actividades y los criterios asignados a dichas actividades
     *
     * @param filter Filtro (aula virtual y lista de ids de las actividades)
     * @return List<ActividadDTO> lista de actividades y los criterios asignados a dichas actividades
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Lista de actividades y los criterios asignados a dichas actividades", 
    	description = "Este método devuelve la lista de actividades y los criterios asignados a dichas actividades", responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("getCriteriosActividades")
    public ResponseEntity<List<ActividadDTO>> getCriteriosActividades(@RequestBody CriteriosActividadesFilter filter) {
	    try {
			
			List<ActividadDTO> actividadesList = aulaVirtualService.getCriteriosActividades(filter.getAulaVirtual(), filter.getActividades(),filter.getIdUnidadProgramacion());
		    return new ResponseEntity<>(actividadesList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de actividades y criterios asociados", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Aulas virtuales según profesor", description = "Este metodo devuelve las aulas virtuales correspondientes a las clases de un prfeosr",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("listDetalleProgramacionAula")
	public ResponseEntity<List<ProgramacionAulaVirtualDTO>> listDetalleProgramacionAula(@RequestParam("idPlataforma") Long idPlataforma, @RequestParam("idCurso") Long idCurso)
	{
		return new ResponseEntity<>(aulaVirtualService.listDetalleProgramacionAula(idPlataforma, idCurso), HttpStatus.OK);
	}


    /**
     * Devuelve las calificaciones de las actividades del aula virtual
     *
     * @param idProgramacionAula
     * @param idAula
     * @param anno
     * @param anno
     * @return
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Traernos las calificaciones asociadas a las actividades importadas anteriormente",
    	description = "Traernos las calificaciones asociadas a las actividades importadas anteriormente", responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("importarCalificacionesActividad")
    public ResponseEntity<String> getCalificacionesActividad(
    		@RequestParam("idPonderacion") Long idPonderacion,
    		@RequestParam("idAula") Long idAula,
    		@RequestParam("anno") Long anno,
    		@RequestParam("idProgramacionAula") Long idProgramacionAula,
    		@RequestBody CalificacionActividadesFilter body) {
	    try {
			aulaVirtualService.getCalificacionesActividad(idPonderacion, idAula, body.getAlumnos(), anno, body.getActividades(), null, idProgramacionAula);
		    return new ResponseEntity<>("", HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener el listado de actividades y criterios asociados", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza datos de las actividades que nos hemos traido de moodle
	 *
	 * @param idProgramacionAula
	 * @param idAula
	 * @return
	 * @RequestBody actividadesDto
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Actualiza datos de las actividades que nos hemos traido de moodle",
			description = "Actualiza datos de las actividades que nos hemos traido de moodle", responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("actualizarActividadesMoodle")
	public ResponseEntity<List<ActualizarActividadDTO>> actualizarActividadesMoodle(
			@RequestParam("idPonderacion") Long idPonderacion,
			@RequestParam("idAula") Long idAula,
            @RequestParam("idUnidadProgramacion") Long idUnidadProgramacion,
            @RequestParam("anno") Long anno,
            @RequestParam("idProgramacionAula") Long idProgramacionAula,
			@RequestBody BodyActualizarActividadDTO body) {
		try {
            List<ActualizarActividadDTO> actualizarActividad = aulaVirtualService.actualizarActividadesMoodle(idPonderacion, idAula, idUnidadProgramacion, anno, body, idProgramacionAula);

			return new ResponseEntity<>(actualizarActividad, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al actualizar las actividades", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
