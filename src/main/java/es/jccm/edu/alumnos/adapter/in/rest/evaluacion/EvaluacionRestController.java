package es.jccm.edu.alumnos.adapter.in.rest.evaluacion;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.AlumnoEvalIndDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.ConvUnidadDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.ConvocatoriaDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.CursoCentroDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.EstadoPromocionDto;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.EvaluacionDto;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.GrupoActividadConvocatoriaDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.ListCalificacionesDto;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.MateriaUnidadDto;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.PromocionDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.SistemaCalificacionDto;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.UnidadConvDTO;
import es.jccm.edu.alumnos.application.domain.evaluacion.AlumnoEvalInd;
import es.jccm.edu.alumnos.application.domain.evaluacion.ConvUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.Convocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.Evaluacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.GrupoActividadConvocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.MateriaUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.UnidadConv;
import es.jccm.edu.alumnos.application.ports.in.evaluacion.IEvaluacionService;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.CursoAcademicoDTO;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos" + "/evaluacion")
@Tag(name = "Servicio Evaluacion Escritorio", description = "Servicio para recuperar las notas de  los alumnos")
//@CrossOrigin
public class EvaluacionRestController {

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private IEvaluacionService evaluacionService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Devuelve la lista de alumnos con sus notas de un grupo actividad con un tutor determinado
	 * 
	 *
	 * @param idGrupoAct
	 * @param jwt
	 * @return the response entity
	 */
	@Operation(summary = "Lista de alumnos con sus notas de un grupo actividad con un tutor determinado", description = "Este metodo devuelve lista de alumnos con sus notas de un grupo actividad con un tutor determinado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNotasByGrupoActividad")
	public ResponseEntity<List<EvaluacionDto>> getNotasByGrupoActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idGrupoAct") String idGrupoAct, @RequestParam("idConvocatoria") Long idConvocatoria) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<Evaluacion> evaluacion = evaluacionService.getNotasByGrupoActividad(idGrupoAct, datosUsuario.getIdEmpleadoComunica(), idConvocatoria);

		List<EvaluacionDto> evaluacionOut = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(evaluacionOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista calificaciones del sistema de calificación que tiene asignado el Grupo de actividad
	 * 
	 *
	 * @param idGrupoAct
	 * @return the response entity
	 */
	@Operation(summary = "Lista de sistema de calificación de un grupo actividad", description = "Este metodo devuelve lista de sistema de calificación de un grupo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTipoSistemaCalificacion")
	public ResponseEntity<SistemaCalificacionDto> getTipoSistemaCalificacion(@RequestParam("idGrupoAct") Long idGrupoAct, @RequestParam("anno") Integer anno) {
		
		SistemaCalificacionDto sisCalOut = modelMapper.map(evaluacionService.getTipoSistemaCalificacion(idGrupoAct, anno), SistemaCalificacionDto.class);

		return new ResponseEntity<>(sisCalOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista calificaciones del sistema de calificación que tiene asignado la matrículad de un alumno
	 * 
	 *
	 * @param idMatMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Lista de sistema de calificación de un grupo actividad", description = "Este metodo devuelve lista de sistema de calificación de un grupo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTipoSistemaCalificacionPorAlumno")
	public ResponseEntity<List<ListCalificacionesDto>> getTipoSistemaCalificacionPorAlumno(@RequestParam("idMatMatricula") Long idMatMatricula, @RequestParam("anno") Integer anno) {
		
		List<ListCalificacionesDto> calificacionesOut = evaluacionService.getTipoSistemaCalificacionPorAlumno(idMatMatricula, anno)
				.stream().map(x -> modelMapper.map(x, ListCalificacionesDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(calificacionesOut, HttpStatus.OK);
	}
	
	/**
	 * Crea o modifica la nota de un alumno para un grupo de actividad en BBDD
	 * 
	 *
	 * @param idConvCentroOmc
	 * @param idMatMatricula
	 * @param idCalifica
	 * @param idConvocatoria
	 * @param accion
	 * @return HttpStatus
	 */
	@Operation(summary = "Settea en BBDD una nota para un alumno", description = "Este metodo inserta o actualiza la nota de un alumno para una materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setNotaAlumno")
	public HttpStatus setNotaAlumnoParaGrupoActividad(@RequestParam("idConvCentroOmc") Long idConvCentroOmc, 
			@RequestParam("idMatMatricula") Long idMatMatricula, @RequestParam(value = "idCalifica", required = false) Long idCalifica, 
			@RequestParam(value = "idConvocatoria", required = false) Long idConvocatoria, @RequestParam("accion") Integer accion) {

		HttpStatus status;
		
		try {
			
			evaluacionService.setNotaAlumnoParaGrupoActividad(idConvCentroOmc, idMatMatricula, idCalifica, idConvocatoria, accion);
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Crea, modifica o elimina la nota de un alumno para una convocatoria final en BBDD
	 * 
	 *
	 * @param idConvCentroOmc
	 * @param idMatMatricula
	 * @param idMatricula
	 * @param idConvUnidad
	 * @param idCalifica
	 * @param apruebaMateria
	 * @param fechaSesion
	 * @param accion
	 * @return HttpStatus
	 */
	@Operation(summary = "Settea en BBDD una nota para un alumno", description = "Este metodo inserta o actualiza la nota de un alumno para una materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setNotaConvocatoriaFinalAlumno")
	public HttpStatus setNotaConvocatoriaFinalAlumno(@RequestParam("idConvCentroOmc") Long idConvCentroOmc, 
			@RequestParam("idMatMatricula") Long idMatMatricula, 
			@RequestParam("idMatricula") Long idMatricula, 
			@RequestParam(value = "idCalifica", required = false) Long idCalifica,
			@RequestParam(value = "apruebaMateria", required = false) String apruebaMateria,
			@RequestParam("idConvUnidad") Long idConvUnidad,
			@RequestParam("fechaSesion") String fechaSesion, 
			@RequestParam("accion") Integer accion) {

		HttpStatus status;
		
		try {
			evaluacionService.setNotaConvocatoriaFinalAlumno(idConvCentroOmc, idMatMatricula, idMatricula, idCalifica, apruebaMateria, idConvUnidad, fechaSesion, accion);
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Crea, modifica o elimina el resultado de la promoción de un alumno en BBDD
	 * 
	 *
	 * @param idConvCentroOmc
	 * @param idMatricula
	 * @param idConvUnidad
	 * @param resultado
	 * @param idEstGenMatr
	 * @param fechaSesion
	 * @param accion
	 * @return HttpStatus
	 */
	@Operation(summary = "Settea en BBDD una nota para un alumno", description = "Este metodo inserta o actualiza la nota de un alumno para una materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setResultadoPromocionAlumno")
	public HttpStatus setResultadoPromocionAlumno(@RequestParam("idConvCentroOmc") Long idConvCentroOmc,  
			@RequestParam("idMatricula") Long idMatricula, 
			@RequestParam(value = "resultado", required = false) Long resultado, 
			@RequestParam(value = "idEstGenMatr", required = false) Long idEstGenMatr, 
			@RequestParam("idConvUnidad") Long idConvUnidad,
			@RequestParam("fechaSesion") String fechaSesion, 
			@RequestParam("accion") Integer accion) {

		HttpStatus status;
		
		try {
			evaluacionService.setResultadoPromocionAlumno(idConvCentroOmc, idMatricula, resultado, idEstGenMatr, idConvUnidad, fechaSesion, accion);
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Devuelve la lista de alumnos con sus notas de una unidad
	 * 
	 *
	 * @param idUnidad
	 * @return the response entity
	 */
	@Operation(summary = "Lista de alumnos con sus notas de una unidad", description = "Este metodo devuelve lista de alumnos con sus notas de una unidad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNotasByUnidad")
	public ResponseEntity<List<EvaluacionDto>> getNotasByUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																@RequestParam("idUnidad") Long idUnidad,
																@RequestParam("idConvocatoria") Long idConvocatoria,
																@RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

		List<Evaluacion> evaluacion = evaluacionService.getNotasByUnidad(idUnidad, idConvocatoria, idOfertaMatrig);

		List<EvaluacionDto> evaluacionOut = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(evaluacionOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de materia por unidad
	 * 
	 *
	 * @param idUnidad
	 * @return the response entity
	 */
	@Operation(summary = "Lista de materia por unidad", description = "Este metodo devuelve lista de materia de una unidad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMateriaByUnidad")
	public ResponseEntity<List<MateriaUnidadDto>> getMateriaByUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idUnidad") Long idUnidad,
			@RequestParam("anno") Integer anno, @RequestParam("idOferMatring") Long idOferMatring) {

		List<MateriaUnidad> materia = evaluacionService.getMateriaByUnidad(idUnidad, anno, idOferMatring);

		List<MateriaUnidadDto> materiaOut = materia.stream().map(x -> modelMapper.map(x, MateriaUnidadDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(materiaOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de convocatorias de un centro por año
	 * 
	 * 
	 * @param idCentro
	 * @param anno
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de convocatorias del centro", description = "Devuelve un listado de Convocatorias del año del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvocatorias")
	public ResponseEntity<List<ConvocatoriaDTO>> getConvocatorias(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCentro") Long idCentro,
			@RequestParam("anno") Integer anno) {
		
		List<Convocatoria> convocatorias = evaluacionService.getConvocatorias(idCentro, anno);
		
		List<ConvocatoriaDTO> listaConvocatoriasOut = convocatorias.stream().map(x -> modelMapper.map(x, ConvocatoriaDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listaConvocatoriasOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de unidades de una convocatoria
	 * 
	 * Si tiene unidades es profesor, sino es director
	 * @param idConvocatoria
	 * @param idUnidad
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de unidades de la convocatoria", description = "Devuelve un listado de Unidades de la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesConvocatoria")
	public ResponseEntity<List<UnidadConvDTO>> getUnidadesConvocatoria(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idConvocatoria") Long idConvocatoria, 
							@RequestParam(value = "idOfertamatrig", required = false) Long idOfertamatrig, 
							@RequestParam(value = "idUnidad", required = false) String idUnidad) {
		
		List<UnidadConv> unidadesConvocatoria = evaluacionService.getUnidadesConvocatoria(idConvocatoria, idOfertamatrig, idUnidad);
		
		List<UnidadConvDTO> listaUnidadesConvocatoriaOut = unidadesConvocatoria.stream().map(x -> modelMapper.map(x, UnidadConvDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listaUnidadesConvocatoriaOut, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve la lista de grupos de actividad de la convocatoria del docente con las unidades
	 * 
	 * 
	 * @param idConvocatoria
	 * @return the response entity
	 */
	
	@Operation(summary = "Devuelve un listado de grupos de actividad de la convocatoria", description = "Devuelve un listado de Grupos de Actividad de la convocatoria del docente", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGruposActividadwithUnidad")
	public ResponseEntity<List<GrupoActividadConvocatoriaDTO>> getGruposActividadUnidadConvocatoria(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idConvocatoria") Long idConvocatoria) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<GrupoActividadConvocatoria> gruposActividadConvocatoria = evaluacionService.getGruposActividadUnidades(idConvocatoria, datosUsuario.getIdEmpleadoDelphos());
		
		List<GrupoActividadConvocatoriaDTO> listagruposActividadConvocatoriaOut = gruposActividadConvocatoria.stream().map(x -> modelMapper.map(x, GrupoActividadConvocatoriaDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listagruposActividadConvocatoriaOut, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve la lista de grupos de actividad de la convocatoria del docente
	 * 
	 * 
	 * @param idConvocatoria
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de grupos de actividad de la convocatoria", description = "Devuelve un listado de Grupos de Actividad de la convocatoria del docente", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGruposActividadConvocatoria")
	public ResponseEntity<List<GrupoActividadConvocatoriaDTO>> getGruposActividadConvocatoria(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idConvocatoria") Long idConvocatoria) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		
		List<GrupoActividadConvocatoria> gruposActividadConvocatoria = evaluacionService.getGruposActividadConvocatoria(idConvocatoria, datosUsuario.getIdEmpleadoDelphos());
		
		List<GrupoActividadConvocatoriaDTO> listagruposActividadConvocatoriaOut = gruposActividadConvocatoria.stream().map(x -> modelMapper.map(x, GrupoActividadConvocatoriaDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listagruposActividadConvocatoriaOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de las unidades correspondientes a un grupo actividad y una convocatoria
	 * 
	 * 
	 * @param idGrupoAct
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de unidades del grupo actividad", description = "Devuelve un listado de unidades del grupo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesByGrupoActividad")
	public ResponseEntity<List<UnidadConvDTO>> getUnidadesByGrupoActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idConvocatoria") Long idConvocatoria, 
			@RequestParam("idGrupoAct") Long idGrupoAct) {
																															
		List<UnidadConv> gruposActividadUnidades = evaluacionService.getUnidadesByGrupo(idConvocatoria,idGrupoAct);
		
		List<UnidadConvDTO> listagruposActividadConvocatoriaOut = gruposActividadUnidades.stream().map(x -> modelMapper.map(x, UnidadConvDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listagruposActividadConvocatoriaOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de las fechas sesiones de una unidad y una convocatoria
	 * 
	 * 
	 * @param unidad
	 * @param convocatoria
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve la lista de las fechas sesiones de una unidad y una convocatoria", description = "Devuelve la lista de las fechas sesiones de una unidad y una convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechaSesion")
	public ResponseEntity<List<ConvUnidadDTO>> getFechaSesion(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("unidad") Long unidad, 
			@RequestParam("convocatoria") Long convocatoria) {
																															
		List<ConvUnidad> convUnidad = evaluacionService.getFechaSesion(unidad, convocatoria);
		
		List<ConvUnidadDTO> convUnidadDto = convUnidad.stream().map(x -> modelMapper.map(x, ConvUnidadDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(convUnidadDto, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de alumnos breve de un grupo actividad con un tutor determinado
	 * 
	 *
	 * @param idGrupoAct
	 * @param jwt
	 * @return the response entity
	 */
	@Operation(summary = "Lista de alumnos breve de un grupo actividad con un tutor determinado", description = "Este metodo devuelve lista de alumnos breve de un grupo actividad con un tutor determinado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosByGruposActividad")
	public ResponseEntity<List<AlumnoEvalIndDTO>> getAlumnosByGruposActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idGrupoAct") String idGrupoAct, @RequestParam("idConvocatoria") Long idConvocatoria) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<AlumnoEvalInd> alumnos = evaluacionService.getAlumnosByGruposActividad(idGrupoAct, datosUsuario.getIdEmpleadoComunica(), idConvocatoria);

		List<AlumnoEvalIndDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoEvalIndDTO.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de alumnos breve de una unidad
	 * 
	 *
	 * @param idUnidad
	 * @return the response entity
	 */
	@Operation(summary = "Lista de alumnos breve de una unidad", description = "Este metodo devuelve lista de alumnos breve de una unidad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosByUnidad")
	public ResponseEntity<List<AlumnoEvalIndDTO>> getAlumnosByUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																	 @RequestParam("idUnidad") Long idUnidad,
																	 @RequestParam("idConvocatoria") Long idConvocatoria,
																	 @RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

		List<AlumnoEvalInd> alumnos = evaluacionService.getAlumnosByUnidad(idUnidad, idConvocatoria, idOfertaMatrig);

		List<AlumnoEvalIndDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoEvalIndDTO.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
	}

	
	/**
	 * Devuelve la lista de alumnos con su promocion
	 * 
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve la lista de alumnos con su promocion", description = "Devuelve la lista de alumnos con su promocion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPromotion")
	public ResponseEntity<PromocionDTO> getPromotionUnit(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("MatMatricula") Long MatMatricula, 
			@RequestParam("convocatoria") Long convocatoria) {
																															
		PromocionDTO promDTO = modelMapper.map(evaluacionService.getPromotion(MatMatricula, convocatoria), PromocionDTO.class);
		
		
		return new ResponseEntity<>(promDTO, HttpStatus.OK);
	}
	
	
	
	/**
	 * Devuelve la lista estados de promocion
	 * 
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve la lista estados de promocion", description = "Devuelve la lista estados de promocion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getStatesPromotion")
	public ResponseEntity<List<EstadoPromocionDto>> getPromotionStates(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idOfermatrig") Long idOfermatrig) {
																															
		List<EstadoPromocionDto> statePro = evaluacionService.getStatesPromocion(idOfermatrig).stream().map(x -> modelMapper.map(x, EstadoPromocionDto.class)).collect(Collectors.toList());
		
		
		return new ResponseEntity<>(statePro, HttpStatus.OK);
	}
	
	/**
	 * En referencía a ciclos formativos
	 * 
	 * 
	 * 
	 */
	@Operation(summary = "En referencía a ciclos formativos", description = "En referencía a ciclos formativos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getInfoCicles")
	public ResponseEntity<Long> getCicle(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("idOfermatrig") Long idOfermatrig, 
			@RequestParam("action") Long action) {
		
			Long statePro = evaluacionService.getPromotionStates(idOfermatrig, action);
			
		return new ResponseEntity<>(statePro, HttpStatus.OK);
	}

	
	/**
	 * En referencía a ciclos formativos
	 * 
	 * 
	 * 
	 */
	@Operation(summary = "En referencía a ciclos formativos", description = "En referencía a ciclos formativos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getReprobedHoursFCT")
	public ResponseEntity<HashMap<String, Long>> getReprobedHours(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
			@RequestParam("idMatricula") Long idMatricula, 
			@RequestParam("idConvOmc") Long idConvOmc) {
		
		HashMap<String, Long> statePro = evaluacionService.getReprobedHours(idMatricula, idConvOmc);
			
		return new ResponseEntity<>(statePro, HttpStatus.OK);
	}



	/**
	 * Devuelve resultado de la media de las notas de un alumno
	 * 
	 * 
	 * 
	 */
	@Operation(summary = "Devuelve resultado de la media de las notas de un alumno", description = "Devuelve resultado de la media de las notas de un alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMediaNotas")
	public ResponseEntity<String> getMediaNotas(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
			@RequestParam("idAlumno") Long idAlumno, 
			@RequestParam("idOfertMatrig") Long idOfertMatrig,
			@RequestParam("notas") String notas, 
			@RequestParam("decimales") Long decimales) {
		
		String media = evaluacionService.getMediaEvaluacion( idAlumno,   idOfertMatrig,  notas,  decimales);
			
		return new ResponseEntity<>(media, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve los cursos de un centro (dirección)
	 * 
	 * 
	 * @param idCentro
	 * @param anno
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de unidades de la convocatoria", description = "Devuelve un listado de Unidades de la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/CursosCentro")
	public ResponseEntity<List<CursoCentroDTO>> getCursosCentro(@RequestParam("idCentro") Long idCentro, 
			@RequestParam("anno") Long anno) {
		
		List<CursoCentroDTO> cursosOut = evaluacionService.getCursosByCentroAndAnno(idCentro, anno);
		
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);
	}



}
