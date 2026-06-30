package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.*;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.CriterioListDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.NivelCurricularProgramacionAulaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriasCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaCompetenciaEspecificaDidacticaProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaMateriaProgramacionDidacticaService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaMateriasCentroService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaOfertaMatriculaCentroService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaOfertaMatriculaGenericoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaProgramacionDidacticaService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaTipoOperacionService;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaUnidadProgramacionService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(BasePath.EvaluacionBasePath + "/programacionDidactica")
@Tag(name = "Servicio Programaciones Didácticas", description = "Servicio para recuperar las programaciones didácticas")
@CrossOrigin
public class ProgramacionDidacticaRestController {

	@Autowired
	private IEvaProgramacionDidacticaService programacionDidacticaService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IEvaOfertaMatriculaGenericoService evaOfertaMatriculaGenericoService;

	@Autowired
	private IEvaMateriasCentroService materiasCentroService;

	@Autowired
	private IEvaUnidadProgramacionService unidadProgramacionService;

	@Autowired
	private IEvaTipoOperacionService evaTipoOperacionService;

	@Autowired
	private IEvaUnidadProgramacionService evaUnidadProgramacionService;

	@Autowired
	private IEvaMateriaProgramacionDidacticaService materiaProgramacionDidacticaService;

	@Autowired
	private IEvaOfertaMatriculaCentroService evaOfertaMatriculaCentroService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	/**
	 * Método que extrae de las BBDD de Delphos los cursos académicos que oferta un centro educativo
	 *
	 * @param codigoCentro
	 * @param anyo
	 * @return List<OfertaMatriculaGenericoDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de cursos del centro", description = "Este metodo devuelve una lista con todos los cursos para un centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cursosCentro")
	public ResponseEntity<List<OfertaMatriculaGenericoDTO>> getCursosCentro(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("anyo") Integer anyo) {
		List<OfertaMatriculaGenericoDTO> cursosOut = evaOfertaMatriculaGenericoService.getCursosCentro(codigoCentro, anyo);
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);

	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las materias de un centro para el año indicado
	 *
	 * @param jwt  token
	 * @param anyo año académico
	 * @return List<MateriasCentroDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar materias", description = "Este método devuelve una lista con las materias del centro en el año indicado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiasCentro")
	public ResponseEntity<List<MateriasCentroDTO>> getMateriasCentroByAnyo(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro,
																		   @RequestParam("anyo") Integer anyo) {

		try {
			List<EvaMateriasCentro> materiasList = materiasCentroService.getMateriasCentroByAnyo(codigoCentro, anyo);
			List<MateriasCentroDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasCentroDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(materiasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las materias de un centro para el curso y año indicado
	 *
	 * @param jwt  token
	 * @param anyo año académico
	 * @param idCurso Id. del curso
	 * @return List<MateriasCentroDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Recuperar materias", description = "Este método devuelve una lista con las materias del centro en el año indicado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiasCentroCurso")
	public ResponseEntity<List<MateriasCentroDTO>> getMateriasCentroByAnyoAndCurso(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro,
																				   @RequestParam("anyo") Integer anyo, @RequestParam("idCurso") Long idCurso) {

		try {
			List<EvaMateriasCentro> materiasList = materiasCentroService.getMateriasCentroByAnyoAndCurso(codigoCentro, anyo, idCurso);
			List<MateriasCentroDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasCentroDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(materiasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que inserta en la BBDD de Delphos la programación didactica asociada a una ponderación
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Inserta programación didactica", description = "Este método inserta una programación diáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/insertProgramacionDidactica")
	public ResponseEntity<HttpStatus> insertProgramacionDidactica(@RequestBody ProgramacionDidacticaDTO programacionDidactica,
																  @RequestParam Long idMateria) {
		try {
			programacionDidacticaService.insertProgramacionDidactica(programacionDidactica, idMateria);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que comprueba si se puede cerrar la programación didáctica
	 *
	 * @Param idProgramacionDidactica
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Comprueba si se puede cerrar la programación didáctica", description = "Método que comprueba si se puede cerrar la programación didáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/puedeCerrarProgramacion")
	public ResponseEntity<Integer> puedeCerrarProgramacion(@RequestParam("idProgramacionDidactica") Long idProgramacionDidactica,
														   @RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

		try {
			Integer puedeCerrar = programacionDidacticaService.puedeCerrarProgramacion(idProgramacionDidactica, idOfertaMatrig);

			return new ResponseEntity<>(puedeCerrar, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que comprueba si se puede abrir la programación didáctica
	 *
	 * @Param idProgramacionDidactica
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Comprueba si se puede abrir la programación didáctica", description = "Método que comprueba si se puede abrir la programación didáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/puedeAbrirProgramacion")
	public ResponseEntity<Boolean> puedeAbrirProgramacion(@RequestParam("idProgramacionDidactica") Long idProgramacionDidactica) {

		try {
			boolean puedeAbrir = programacionDidacticaService.puedeAbrirProgramacion(idProgramacionDidactica);

			return new ResponseEntity<>(puedeAbrir, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que inserta en la BBDD de Delphos la programación didactica asociada a una ponderación
	 *
	 * @RequestBody programacionDidacticaDto
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Inserta programación didactica", description = "Este método inserta una programación diáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/cerrarProgramacionDidactica")
	public ResponseEntity<HttpStatus> cerrarProgramacionDidactica(@RequestParam Long idProgramacionDidactica,
																  @RequestParam Integer cerrar) {

		try {
			programacionDidacticaService.cerrarProgramacionDidactica(idProgramacionDidactica, cerrar);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que actualiza en la BBDD de Delphos la programación didactica asociada a una ponderación
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Actualiza programación didactica", description = "Este método actualiza una programación diáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/updateProgramacionDidactica")
	public ResponseEntity<HttpStatus> updateProgramacionDidactica(@RequestBody ProgramacionDidacticaDTO programacionDidactica) {

		try {
			programacionDidacticaService.updateProgramacionDidactica(programacionDidactica);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que elimina en la BBDD de Delphos la programación didactica asociada a una ponderación
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Eliminar programación didactica", description = "Este método elimina una programación diáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/deleteProgramacionDidactica")
	public ResponseEntity<Void> deleteProgramacionDidactica(@RequestParam Long idProgramacionDidactica) {

		if (programacionDidacticaService.deleteProgramacionDidactica(idProgramacionDidactica).booleanValue()) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las materias de un centro para el curso, la materia y el año indicados que pudieran ser elegibles de disponer una programación didáctica
	 *
	 * @param jwt  token
	 * @param anyo año académico
	 * @param idCurso Id. del curso
	 * @param idMateria Id. de la materia
	 * @param page
	 * @param numItems
	 * @return List<MateriaProgramacionDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar materias", description = "Este método devuelve una lista con las materias del centro en el año indicado (opcionalmente curso y materia) que pudieran ser elegibles de disponer una programación didáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiasProgramacionDidactica")
	public ResponseEntity<Page<MateriaProgramacionDidacticaDTO>> getMateriasProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro,
																								  @RequestParam("anyo") Integer anyo, @RequestParam("idCurso") Long idCurso, @RequestParam("idMateria") Long idMateria,
																								  @RequestParam("page") int page, @RequestParam("numItems") int numItems, @RequestParam("editor") Long editor,
																								  @RequestParam("estado") String estado, @RequestParam("perfilEditor") Boolean perfilEditor) {

		try {
			Page<EvaMateriaProgramacionDidactica> materias = materiaProgramacionDidacticaService.getMateriasProgramacionDidactica(codigoCentro, anyo, idCurso, idMateria, page, numItems, editor, estado, perfilEditor);
			List<MateriaProgramacionDidacticaDTO> materiasListOut = materias.getContent().stream().map(materia -> modelMapper.map(materia, MateriaProgramacionDidacticaDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(new PageImpl<>(materiasListOut, materias.getPageable(), materias.getTotalElements()), HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista de Unidades de programación por programación didáctica
	 *
	 * @param jwt  token
	 * @return List<UnidadProgramacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar lista de Unidades programacion por criterios", description = "Este método devuelve una lista con la relación entre el criterio y el número de unidades de programación asociados",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/unidadesProgramacion")
	public ResponseEntity<List<UnidadProgramacionDTO>> getUnidadesProgramacion(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idProgramacionDidactica") Long idProgramacionDidactica) {

		try {
			List<UnidadProgramacionDTO> unidadesProgramacionList = evaUnidadProgramacionService.getUnidadesProgramacion(idProgramacionDidactica);

			return new ResponseEntity<>(unidadesProgramacionList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método para crear nuevas unidades de programacion y asociarlas a una programación didáctica
	 *
	 * @param idProgramacionDidactica
	 *
	 * @return List<CursoSelectorDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Crea unidades de programación", description = "Este metodo crea nuevas unidades de programacion y las asocia a una programación didáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/insertUnidadProgramacion")
	public ResponseEntity<EvaUnidadProgramacion> insertUnidadProgramacion(@RequestParam("idProgramacionDidactica") Long idProgramacionDidactica,
																		  @RequestParam( value="idConvCentroOmc", required = false) Long idConvCentroOmc,
																		  @RequestBody UnidadProgramacionDTO unidadProgramacion) {

		return ResponseEntity.ok(unidadProgramacionService.insertUnidadProgramacion(idProgramacionDidactica, idConvCentroOmc, unidadProgramacion));
	}

	/**
	 * Método para actualizar una unidad de programacion, actualiza los cambios en la entidad, se borran los criterios que no estén ya asociados y crea los nuevos
	 *
	 * @body unidadProgramacion
	 *
	 * @return List<CursoSelectorDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Actualiza una unidad de programación", description = "Este metodo actualiza una unidad de programacion y borra o crea las relaciones con los criterios",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateUnidadProgramacion")
	public ResponseEntity<HttpStatus> updateUnidadProgramacion(@RequestParam( value="idConvCentroOmc", required = false) Long idConvCentroOmc,
															   @RequestBody UnidadProgramacionDTO unidadProgramacion) {

		try {
			unidadProgramacionService.updateUnidadProgramacion(idConvCentroOmc, unidadProgramacion);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para borrar una unidad de programacion, sus relaciones con los criterios
	 *
	 * @param idUnidadProgramacion
	 * @return List<CursoSelectorDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Borra una unidad de programación", description = "Este metodo borra una unidad de programación, "
			+ "la relación con la programación didáctica y las relaciones con los criterios",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteUnidadProgramacion")
	public ResponseEntity<Void> deleteUnidadProgramacion(@RequestParam Long idUnidadProgramacion) {

		if (unidadProgramacionService.deleteUnidadProgramacion(idUnidadProgramacion)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Lista de Tipos de Operaciones
	 *
	 * @param jwt  token
	 * @return List<TipoOperacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar tipos de Operaciones", description = "Este método devuelve una lista con los tipos de Operaciones",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/tiposOperacion")
	public ResponseEntity<List<TipoOperacionDTO>> getTiposOperacion(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		try {
			List<TipoOperacionDTO> tiposOperacion = evaTipoOperacionService.getTiposOperacion();
			return new ResponseEntity<>(tiposOperacion, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista de Unidades de programación por criterios
	 *
	 * @param jwt  token
	 * @param jwt  criterios Lista de criterios
	 * @param jwt  idProgramacionDidactica Id. Programación Didáctica
	 * @return List<UnidadesProgramacionCriterioDto>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar lista de Unidades programacion por criterios", description = "Este método devuelve una lista con la relación entre el criterio y el número de unidades de programación asociados",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/unidadesProgramacionCriterio")
	public ResponseEntity<List<UnidadesProgramacionCriterioDTO>> getUnidadesProgramacionCriterios(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																								  @RequestBody List<CriterioListDto> criterios, @RequestParam("idProgramacionDidactica") Long idProgramacionDidactica) {

		try {
			List<UnidadesProgramacionCriterioDTO> unidadesProgramacionCriterios = evaUnidadProgramacionService.getNumUnidadesProgramacionCriterios(criterios, idProgramacionDidactica);
			return new ResponseEntity<>(unidadesProgramacionCriterios, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método para rescatar los criterios de evaluación por Competencias Especificas
	 *
	 * @param idMateriaOmg
	 * @return List<CompetenciaEspecificaDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Crea unidades de programación", description = "Este metodo crea nuevas unidades de programacion y las asocia a una programación didáctica",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCriteriosEvaluacion")
	public ResponseEntity<List<CompetenciaEspecificaDidacticaDTO>> getCriteriosEvaluacionR(@RequestParam("idMateriaOmg") Long idMateriaOmg) {

		try {

			List<CompetenciaEspecificaDidacticaDTO> competenciasOut = unidadProgramacionService.getCompetenciasEspecificas(idMateriaOmg);
			return new ResponseEntity<>(competenciasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Este metodo obtiene los criterios acnee
	 *
	 * @param idPonderacion
	 * @return List<CompetenciaEspecificaDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Obtener criterios acnee", description = "Este metodo obtiene los criterios acnee",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCriteriosEvaluacionAcnee")
	public ResponseEntity<List<CompetenciaEspecificaDidacticaDTO>> getCriteriosEvaluacionAcnee(@RequestParam("idPonderacion") Long idPonderacion) {

		try {

			List<CompetenciaEspecificaDidacticaDTO> competenciasOut = unidadProgramacionService.getCriteriosEvaluacionAcnee(idPonderacion);
			return new ResponseEntity<>(competenciasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Programación didáctica asociada a los parámetros
	 *
	 * @param jwt  token
	 * @param idMateriaOmg Id. materia
	 * @param idOfertamatrig
	 * @param codigoCentro Código Centro
	 * @param anyo Año académico
	 * @return ProgramacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar programación didáctica", description = "Este método devuelve una programación didáctica asociada a la materia, el curso, el centro y el año académico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/programacionDidacticaMateriaCursoCentroAnyo")
	public ResponseEntity<ProgramacionDidacticaDTO> getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyo(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMateriaOmg") Long idMateriaOmg,
																													@RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("anyo") Integer anyo) {

		try {

			ProgramacionDidacticaDTO programacionDidactica = programacionDidacticaService.getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyo(idMateriaOmg, idOfertamatrig, codigoCentro, anyo);
			return new ResponseEntity<>(programacionDidactica, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista de convocatorias
	 *
	 * @return List<ConvocatoriasDto>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar la lista de convocatorias", description = "Este método devuelve la lista de convocatorias",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/convocatoriasUnidadProgramacion")
	public ResponseEntity<List<ConvocatoriasDto>> getConvocatorias(@RequestParam("anno") Long anno, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("idMateria") Long idMateria) {

		List<ConvocatoriasDto> convocatorias = unidadProgramacionService.getConvocatorias(anno, codigoCentro, idMateria);
		return new ResponseEntity<>(convocatorias, HttpStatus.OK);
	}

	/**
	 * Comprueba si existe una programación didáctica para la misma X_MATERIAG en un X_OFERTAMATRIG específico para un mismo centro y año
	 *
	 * @param jwt  token
	 * @return List<UnidadProgramacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "ComprobarDuplicarProgramacionDidactica",
			description = "Comprueba si existe una programación didáctica para la misma X_MATERIAG en un X_OFERTAMATRIG específico para un mismo centro y año",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/comprobarDuplicarProgramacionDidactica")
	public ResponseEntity<EvaCompetenciaEspecificaDidacticaProjection> comprobarDuplicarProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																											  @RequestParam("idMateriaOmg") Long idMateriaOmg,
																											  @RequestParam("idOfermatrig") Long idOfermatrig,
																											  @RequestParam("idCentro") Long idCentro,
																											  @RequestParam("anno") Integer anno) {

		try {
			EvaCompetenciaEspecificaDidacticaProjection programacionDidactica = programacionDidacticaService.comprobarDuplicarProgramacionDidactica(idMateriaOmg, idOfermatrig, idCentro, anno);

			return new ResponseEntity<>(programacionDidactica, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Este método duplica una Programación Didáctica ya existente para una nueva en la pantalla intermedia del proceso de creación
	 *
	 * @param jwt  token
	 * @return List<UnidadProgramacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "duplicarProgramacionDidactica",
			description = "Este método duplica una Programación Didáctica ya existente para una nueva en la pantalla intermedia del proceso de creación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/duplicarProgramacionDidactica")
	public ResponseEntity<EvaProgramacionDidactica> duplicarProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																				  @RequestParam("idProgramacionDidactica") Long idProgramacionDidactica,
																				  @RequestParam("idMateriaOmg") Optional<Long> idMateriaOmg,
																				  @RequestParam("anyo") Optional<Integer> anyo,
																				  @RequestParam("idMateriaOmgAdaptacion") Optional<Long> idMateriaOmgAdaptacion) {

		try {
			EvaProgramacionDidactica programacionDidactica = null;
			if (idMateriaOmgAdaptacion.isEmpty()) { // No es una programación didáctica ACNEE
				if (idMateriaOmg.isPresent() && anyo.isPresent()) {
					programacionDidactica = programacionDidacticaService.duplicarProgramacionDidactica(idProgramacionDidactica, idMateriaOmg.get(), anyo.get());
				} else if (idMateriaOmg.isPresent() && anyo.isEmpty()) { // Distinta materia, mismo año
					programacionDidactica = programacionDidacticaService.duplicarProgramacionDidactica(idProgramacionDidactica, idMateriaOmg.get());
				} else if (idMateriaOmg.isEmpty() && anyo.isPresent()) { // Misma materia, distinto año
					programacionDidactica = programacionDidacticaService.duplicarProgramacionDidactica(idProgramacionDidactica, anyo.get());
				}
			} else {
				if (idMateriaOmg.isPresent() && anyo.isPresent()) {
					programacionDidactica = programacionDidacticaService.duplicarProgramacionDidactica(idProgramacionDidactica, idMateriaOmg.get(), anyo.get(), idMateriaOmgAdaptacion.get());
				}
			}

			if(programacionDidactica == null){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<>(programacionDidactica, HttpStatus.OK);

		} catch (ResponseStatusException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista de nivel curricular
	 *
	 * @param jwt  token
	 * @return List<UnidadProgramacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar lista de nivel curricular", description = "Este método devuelve una lista de nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/nivelCurricular")
	public ResponseEntity<List<NivelCurricularProgramacionAulaDTO>> getNivelCurricular(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno,
																					   @RequestParam("idMateriaOmg") Long idMateriaOmg,
																					   @RequestParam("idCentro") Long idCentro,
																					   @RequestParam("idOfermatrig") Long idOfermatrig) {

		try {
			List<NivelCurricularProgramacionAulaDTO> nivelCurricularList = materiaProgramacionDidacticaService.getNivelCurricular(anno, idMateriaOmg, idCentro, idOfermatrig);

			return new ResponseEntity<>(nivelCurricularList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Lista de nivel curricular para la pantalla de Valoración de Criterios
	 *
	 * @param jwt  token
	 * @return List<UnidadProgramacionDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar lista de nivel curricular", description = "Este método devuelve una lista de nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/nivelCurricularValoracionCriterios")
	public ResponseEntity<List<NivelCurricularProgramacionAulaDTO>> getNivelCurricularValoracionCriterios(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno,
																										  @RequestParam("idMateriaOmg") Long idMateriaOmg,
																										  @RequestParam("idCentro") Long idCentro,
																										  @RequestParam("idOfermatrig") Long idOfermatrig,
																										  @RequestParam("direccion") Optional<Long> direccion) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			List<NivelCurricularProgramacionAulaDTO> nivelCurricularList = materiaProgramacionDidacticaService.getNivelCurricularValoracionCriterios(anno, idMateriaOmg, idCentro, idOfermatrig, datosUsuario.getIdEmpleadoDelphos(), direccion);

			return new ResponseEntity<>(nivelCurricularList, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método para rescatar los criterios de evaluación por unidad de programación
	 *
	 * @param idUnidadProgramacion
	 * @return List<CompetenciaEspecificaDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Rescata criterios de evaluación", description = "Este metodo rescata criterios de evaluación a partir de una unidad de programación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCriteriosEvaluacionUnidad")
	public ResponseEntity<List<CompetenciaEspecificaDidacticaDTO>> getCriteriosEvaluacionUnidad(@RequestParam("idUnidadProgramacion") Long idUnidadProgramacion,
																								@RequestParam(value = "idActividad", required = false) Long idActividad) {
		try {

			List<CompetenciaEspecificaDidacticaDTO> competenciasOut = unidadProgramacionService.getCompetenciasEspecificasUnidad(idUnidadProgramacion, idActividad);
			return new ResponseEntity<>(competenciasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Se ha producido un error al rescatar los criterios de evaluación de la unidad de programación: " + ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método que extrae de las BBDD de Delphos los cursos académicos ACNEAE que oferta un centro educativo
	 *
	 * @param codigoCentro
	 * @param anyo
	 * @return List<OfertaMatriculaGenericoDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de cursos ACNEAE del centro", description = "Este metodo devuelve una lista con todos los cursos ACNEAE para un centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cursosACNEAECentro")
	public ResponseEntity<List<OfertaMatriculaGenericoDTO>> getCursosACNEAECentro(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("anyo") Integer anyo) {
		List<OfertaMatriculaGenericoDTO> cursosOut = evaOfertaMatriculaGenericoService.getCursosACNEAECentro(codigoCentro, anyo);
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);

	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las materias ACNEAE de un centro para el curso y año indicado
	 *
	 * @param jwt  token
	 * @param codigoCentro
	 * @param anyo año académico
	 * @param idOfertaMatrig
	 * @return List<MateriasCentroDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar materias ACNEAE", description = "Este método devuelve una lista con las materias ACNEAE del centro en el año indicado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiasCentroCursoACNEAE")
	public ResponseEntity<List<MateriasCentroDTO>> getMateriasCentroACNEAEByAnyoAndCurso(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro,
																						 @RequestParam("anyo") Integer anyo, @RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

		try {
			List<EvaMateriasCentro> materiasList = materiasCentroService.getMateriasCentroACNEAEByAnyoAndCurso(codigoCentro, anyo, idOfertaMatrig);
			List<MateriasCentroDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasCentroDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(materiasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que extrae de las BBDD de Delphos los niveles curriculares ACNEAE que oferta un centro educativo
	 *
	 * @param codigoCentro
	 * @param anyo
	 * @param idOfertaMatrig
	 * @param idMateriaOmg
	 * @return List<OfertaMatriculaGenericoDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de niveles curriculares ACNEAE del centro", description = "Este metodo devuelve una lista con todos los niveles curriculares ACNEAE para un centro, año, curso y materia",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/nivelesCurricularesACNEAEByCentroAnyoCursoAndMateria")
	public ResponseEntity<List<OfertaMatriculaGenericoDTO>> getNivelesCurricularesACNEAEByCentroAnyoAndCurso(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("anyo") Integer anyo, @RequestParam("idOfertaMatrig") Long idOfertaMatrig, @RequestParam("idMateriaOmg") Long idMateriaOmg) {
		List<OfertaMatriculaGenericoDTO> cursosOut = evaOfertaMatriculaGenericoService.getNivelesCurricularesACNEAEByCentroAnyoCursoAndMateria(codigoCentro, anyo, idOfertaMatrig, idMateriaOmg);
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);

	}

	/**
	 * Conectamos con la BBDD de Delphos y obtenemos la materia ACNEAE para un nivel curricular y una materia indicadas
	 *
	 * @param jwt
	 * @param idMateriaOmg
	 * @param idNivelCurricular
	 * @return MateriasCentroDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar materias", description = "Este método devuelve una lista con las materias del centro en el año indicado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiaACNEAE")
	public ResponseEntity<MateriasCentroDTO> getMateriaACNEAE(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMateriaOmg") Long idMateriaOmg,
															  @RequestParam("idNivelCurricular") Long idNivelCurricular) {

		try {
			EvaMateriasCentro materia = materiasCentroService.getMateriaACNEAE(idMateriaOmg, idNivelCurricular);
			if (materia != null) {
				MateriasCentroDTO materiaOut = modelMapper.map(materia, MateriasCentroDTO.class);
				return new ResponseEntity<>(materiaOut, HttpStatus.OK);
			} else {
				return new ResponseEntity<MateriasCentroDTO>((MateriasCentroDTO) null, HttpStatus.OK);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que inserta en la BBDD de Delphos la programación didactica ACNEAE asociada a una ponderación
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Inserta programación didactica", description = "Este método inserta una programación diáctica ACNEAE",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/insertProgramacionDidacticaACNEAE")
	public ResponseEntity<HttpStatus> insertProgramacionDidacticaACNEAE(@RequestBody ProgramacionDidacticaDTO programacionDidactica,
																		@RequestParam Long idMateriaOmgNivelCurricular,
																		@RequestParam List<Long> criterios) {
		try {
			programacionDidacticaService.insertProgramacionDidacticaACNEAE(programacionDidactica, idMateriaOmgNivelCurricular, criterios);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que extrae de las BBDD de Delphos el curso del centro
	 *
	 * @param codigoCentro
	 * @param idOfertaMatrig
	 * @return OfertaMatriculaCentroDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Curso del centro", description = "Este metodo devuelve un curso del centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursoByCentroOfertamatrigAndAnyo")
	public ResponseEntity<EvaOfertaMatriculaCentro> getCursoIdByCentroAndOfertamatrig(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("idOfertaMatrig") Long idOfertaMatrig, @RequestParam("anyo") Integer anyo) {
		EvaOfertaMatriculaCentro curso = evaOfertaMatriculaCentroService.getCursoByCentroOfertamatrigAndAnyo(codigoCentro, idOfertaMatrig, anyo);
		return new ResponseEntity<>(curso, HttpStatus.OK);

	}

	/**
	 * Programación didáctica asociada a los parámetros
	 *
	 * @param jwt  token
	 * @param idMateriaOmg Id. materia
	 * @param idOfertamatrig
	 * @param codigoCentro Código Centro
	 * @param anyo Año académico
	 * @param idNivelCurricular Nivel curricular
	 * @return ProgramacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar programación didáctica", description = "Este método devuelve una programación didáctica asociada a la materia, el curso, el centro, el año académico, y, en caso de que sea acneae, el nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/programacionDidacticaMateriaCursoCentroAnyoAcneaeNivelCurricular")
	public ResponseEntity<ProgramacionDidacticaDTO> getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyoAndAcneaeAndNivelCurricular(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMateriaOmg") Long idMateriaOmg,
																																			   @RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("codigoCentro") Long codigoCentro, @RequestParam("anyo") Integer anyo, @RequestParam("idNivelCurricular") Long idNivelCurricular) {

		try {
			ProgramacionDidacticaDTO programacionDidactica = programacionDidacticaService.getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyoAndAcneaeAndNivelCurricular(idMateriaOmg, idOfertamatrig, codigoCentro, anyo, idNivelCurricular);
			return new ResponseEntity<>(programacionDidactica, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Programación didáctica asociada a los parámetros
	 *
	 * @param idMateriaOmg Id. materia
	 * @param idNivelCurricular Nivel curricular
	 * @return ProgramacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar programación didáctica", description = "Este método devuelve una programación didáctica asociada a la materia, el curso, el centro, el año académico, y, en caso de que sea acneae, el nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/competenciasCriteriosAcneae")
	public ResponseEntity<List<CompetenciaEspecificaDidacticaDTO>> competenciasCriteriosAcneae( @RequestParam("idMateriaOmg") Long idMateriaOmg, @RequestParam("idNivelCurricular") Long idNivelCurricular) throws Exception {

		try {
			List<CompetenciaEspecificaDidacticaDTO> competenciasOut = programacionDidacticaService.competenciasCriteriosAcneae(idMateriaOmg, idNivelCurricular);
			return new ResponseEntity<>(competenciasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las materias de un centro para el nivel curricular indicado
	 *
	 * @param jwt  token
	 * @param idNivelCurricular id Nivel Curricular
	 * @return List<MateriasCentroDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar materias", description = "Este método devuelve una lista con las materias del centro del nivel curricular indicado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/materiasCentroByNivelCurricular")
	public ResponseEntity<List<MateriasCentroDTO>> getMateriasCentroByNivelCurricular(@RequestHeader(Constants.AUTHORIZATION) String jwt, //@RequestParam("codigoCentro") Long codigoCentro,
																					  @RequestParam("idNivelCurricular") Long idNivelCurricular) {

		try {
			List<EvaMateriasCentro> materiasList = materiasCentroService.getMateriasCentroByNivelCurricular(idNivelCurricular);
			List<MateriasCentroDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasCentroDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(materiasOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Eliminación de duplicados de una programación didáctica asociada a los parámetros
	 *
	 * @param jwt  token
	 * @param idMateriaOmg Id. materia
	 * @param idOfertamatrig
	 * @param idCentro Código Centro
	 * @param anyo Año académico
	 * @param idNivelCurricular idOfertamatrig del nivel de adaptación
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Eliminar duplicados de la programación didáctica", description = "Este método elimina los duplicados de una programación didáctica asociada a la materia, el curso, el centro y el año académico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/deleteDuplicatesProgramacionDidacticaMateriaCursoCentroAnyo")
	public ResponseEntity<HttpStatus> deleteDuplicatesProgramacionDidacticaMateriaCursoCentroAnyo(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMateriaOmg") Long idMateriaOmg,
																								  @RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("idCentro") Long idCentro, @RequestParam("anyo") Integer anyo, @RequestParam(value = "idNivelCurricular") Optional<Long> idNivelCurricular, @RequestParam(value = "idMateriaOmgNivelCurricular") Optional<Long> idMateriaOmgNivelCurricular) {

		try {

			if (idNivelCurricular.isPresent() && idMateriaOmgNivelCurricular.isPresent() && programacionDidacticaService.deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyoAndNivelCurricular(idMateriaOmg, idOfertamatrig, idCentro, anyo, idNivelCurricular.get(), idMateriaOmgNivelCurricular.get())) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else if (programacionDidacticaService.deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyo(idMateriaOmg, idOfertamatrig, idCentro, anyo)) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Borrado masivo de duplicados de programaciones didácticas (no ACNEE)
	 *
	 * @param jwt  token
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Borrado masivo de duplicados de programaciones didácticas", description = "Este método elimina todos los duplicados de las programaciones didácticas (No ACNEE)",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/borrarTodosDuplicadosProgramacionesDidacticas")
	public ResponseEntity<HttpStatus> borrarTodosDuplicadosProgramacionesDidacticas(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		try {

			if (programacionDidacticaService.borrarTodosDuplicadosProgramacionesDidacticas()) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos el historial de empleados responsables de la programación didáctica indicada
	 *
	 * @param jwt
	 * @param idOfertaMatrig
	 * @param idMateriaOmg
	 * @param idCentro
	 * @param anyo
	 * @param idNivelCurricular
	 * @param idMateriaOmgAdap
	 * @return List<HistorialResponsableProgramacionDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Recuperar historial de responsables de una programación didáctica", description = "Este método devuelve un historial en forma de lista con los empleados responsables de la programación didáctica indicada",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getHistorialResponsablesProgramacionDidactica")
	public ResponseEntity<List<HistorialResponsableProgramacionDidacticaDTO>> getHistorialResponsablesProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																															@RequestParam("idOfertaMatrig") Long idOfertaMatrig,
																															@RequestParam("idMateriaOmg") Long idMateriaOmg,
																															@RequestParam("idCentro") Long idCentro,
																															@RequestParam("anyo") Integer anyo,
																															@RequestParam("idNivelCurricular") Optional<Long> idNivelCurricular,
																															@RequestParam("idMateriaOmgAdap") Optional<Long> idMateriaOmgAdap) {

		try {
			List<HistorialResponsableProgramacionDidacticaDTO> historial = programacionDidacticaService.getHistorialResponsablesProgramacionDidactica(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular.orElse(-1L), idMateriaOmgAdap.orElse(-1L));
			return new ResponseEntity<>(historial, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Conectamos con la BBDD de Delphos y sacamos todos los docentes con su respectivo departamento
	 *
	 * @param jwt
	 * @param idCentro
	 * @param anyo
	 * @return List<HistorialResponsableProgramacionDidacticaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Recuperar historial de responsables de una programación didáctica", description = "Este método devuelve una lista con los empleados del centro que están en activo junto con su departamento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getProfesoresDepartamentoProgramacionDidactica")
	public ResponseEntity<List<HistorialResponsableProgramacionDidacticaDTO>> getProfesoresDepartamentoProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																															 @RequestParam("idCentro") Long idCentro,
																															 @RequestParam("anyo") Integer anyo,
																															 @RequestParam("idEmpleado") Long idEmpleado
	) {

		try {
			List<HistorialResponsableProgramacionDidacticaDTO> profesores = programacionDidacticaService.getProfesoresDepartamentoProgramacionDidactica(idCentro, anyo, idEmpleado);
			return new ResponseEntity<>(profesores, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Modificación de los empleados editores de programaciones didácticas
	 *
	 * @param jwt
	 * @param idEmpleado
	 * @param idOfertaMatrig
	 * @param idMateriaOmg
	 * @param idCentro
	 * @param anyo
	 * @param idNivelCurricular
	 * @param idMateriaOmgAdap
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Editar los editores de una programacion didactica", description = "Este método modifica el empleado responsable de la programación didáctica indicada",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/updateEditorProgramacionDidactica")
	public ResponseEntity<HttpStatus> designarResponsableProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																			   @RequestParam("idEmpleado") Long idEmpleado,
																			   @RequestParam("idOfertaMatrig") Long idOfertaMatrig,
																			   @RequestParam("idMateriaOmg") Long idMateriaOmg,
																			   @RequestParam("idCentro") Long idCentro,
																			   @RequestParam("anyo") Integer anyo,
																			   @RequestParam("idNivelCurricular") Optional<Long> idNivelCurricular,
																			   @RequestParam("idMateriaOmgAdap") Optional<Long> idMateriaOmgAdap) {

		try {

			programacionDidacticaService.appointResponsableProgramacionDidactica(idEmpleado, idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular.orElse(null), idMateriaOmgAdap.orElse(null));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminación de los empleados editores de programaciones didácticas
	 *
	 * @param jwt
	 * @param idEmpleado
	 * @param idOfertamatrig
	 * @param idMateriaOmg
	 * @param idCentro
	 * @param anyo
	 * @param idNivelCurricular
	 * @param idMateriaOmgAdap
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Eliminar los editores de una programacion didactica", description = "Este método elimina el empleado responsable de la programación didáctica indicada",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/deleteEditorProgramacionDidactica")
	public ResponseEntity<HttpStatus> eliminarResponsableProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																			   @RequestParam("idOfertaMatrig") Long idOfertaMatrig,
																			   @RequestParam("idMateriaOmg") Long idMateriaOmg,
																			   @RequestParam("idCentro") Long idCentro,
																			   @RequestParam("anyo") Integer anyo,
																			   @RequestParam("idNivelCurricular") Optional<Long> idNivelCurricular,
																			   @RequestParam("idMateriaOmgAdap") Optional<Long> idMateriaOmgAdap) {

		try {

			programacionDidacticaService.revokeResponsableProgramacionDidactica(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular.orElse(null), idMateriaOmgAdap.orElse(null));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminación de los empleados editores de programaciones didácticas
	 *
	 * @param jwt
	 * @param idCentro
	 * @param anyo
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Obtiene los departamentos de un centro", description = "Obtiene los departamentos de un centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/departamentosCentro")
	public ResponseEntity<List<EvaDepartamento>> getDepartamentosCentro(@RequestParam("idCentro") Long idCentro,
																		@RequestParam("anyo") Long anyo) {

		try {

			List<EvaDepartamento> departamentos = programacionDidacticaService.getDepartamentosCentro(idCentro, anyo);
			return new ResponseEntity<>(departamentos, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Obtiene los bloques de los saberes por materia", description = "Obtiene los bloques de los saberes por materia",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/bloquesSaberesByMateria")
	public ResponseEntity<List<RelacionBloqueSaberBasicoMateriaDTO>> bloquesSaberesByMateria(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																							 @RequestParam("idMateria") Long idMateria) {
		try {

			List<RelacionBloqueSaberBasicoMateriaDTO> bloques = unidadProgramacionService.getBloquesSaberes(idMateria);
			return new ResponseEntity<>(bloques, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método que obtiene los saberes básicos asociados a una unidad de programación
	 *
	 * @param jwt
	 * @param idUnidadProgramacion
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Obtiene los saberes básicos de una unidad de programación", description = "Método que obtiene los saberes básicos asociados a una unidad de programación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getSaberesBasicosByUnidadProgramacion")
	public ResponseEntity<List<SaberBasicoDTO>> getSaberesBasicosByUnidadProgramacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																					  @RequestParam("idUnidadProgramacion") Long idUnidadProgramacion) {
		try {

			List<SaberBasicoDTO> saberesBasicos = unidadProgramacionService.getSaberesBasicosByUnidadProgramacion(idUnidadProgramacion);
			return new ResponseEntity<>(saberesBasicos, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param codigoCentro
	 * @param idMateriaOmg
	 * @param idOfertamatrig
	 * @param idNivelCurricular
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar programaciones didácticas de cursos anteriores", description = "Este método devuelve una lista resumida de programaciones didácticas anteriores asociadas a la materia, el curso, el centro, y, en caso de que sea ACNEE, el nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getProgramacionesDidacticasAnyosAnteriores")
	public ResponseEntity<List<HistoricoProgramacionDidacticaDTO>> getProgramacionesDidacticasAnyosAnteriores(@RequestParam("codigoCentro") Long codigoCentro, @RequestParam("idMateriaOmg") Long idMateriaOmg,
																											  @RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("nivelCurricular") Optional<Long> idNivelCurricular) {

		try {
			List<HistoricoProgramacionDidacticaDTO> historicoProgramacionesDidacticas = null;
			if(idNivelCurricular.isPresent()) {
				historicoProgramacionesDidacticas = programacionDidacticaService.getProgramacionesDidacticasACNEEAnyosAnteriores(codigoCentro, idMateriaOmg, idOfertamatrig, idNivelCurricular.get());
			} else {
				historicoProgramacionesDidacticas = programacionDidacticaService.getProgramacionesDidacticasAnyosAnteriores(codigoCentro, idMateriaOmg, idOfertamatrig);
			}
			return new ResponseEntity<>(historicoProgramacionesDidacticas, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param codigoCentro
	 * @param idMateriaOmg
	 * @param idOfertamatrig
	 * @param idNivelCurricular
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Recuperar programaciones didácticas de cursos anteriores", description = "Este método devuelve una lista resumida de programaciones didácticas anteriores asociadas a la materia, el curso, el centro, y, en caso de que sea ACNEE, el nivel curricular",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/hasProgramacionDidacticaAnnoAcademicoActual")
	public ResponseEntity<Integer> hasProgramacionDidacticaAnnoAcademicoActual(@RequestParam("codigoCentro") Long codigoCentro, @RequestParam("idMateriaOmg") Long idMateriaOmg,
																			   @RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("nivelCurricular") Optional<Long> idNivelCurricular) {

		try {
			Integer hasProgramacionDidactica = null;
			if(idNivelCurricular.isPresent()) {
				hasProgramacionDidactica = programacionDidacticaService.hasProgramacionDidacticaAnnoAcademicoActualAcnee(codigoCentro, idMateriaOmg, idOfertamatrig, idNivelCurricular.get());
			} else {
				hasProgramacionDidactica = programacionDidacticaService.hasProgramacionDidacticaAnnoAcademicoActual(codigoCentro, idMateriaOmg, idOfertamatrig);
			}
			return new ResponseEntity<>(hasProgramacionDidactica, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}