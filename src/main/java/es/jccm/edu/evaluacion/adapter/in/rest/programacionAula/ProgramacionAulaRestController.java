package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.AlumnosPorGrupoProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.DocenteProgAulaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.ProgramacionAulaProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaAlumnoService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaInstrumentoEvaluacionService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaProgramacionAulaService;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaUnidadCentroService;
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

import org.apache.commons.beanutils.ConvertUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(BasePath.EvaluacionBasePath + "/programacionAula")
@Tag(name = "Servicio Programación de aulas", description = "Servicio para recuperar las programaciones de las aulas")
@CrossOrigin
public class ProgramacionAulaRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IEvaProgramacionAulaService programAulaService;

	@Autowired
	private IEvaAlumnoService alumnoService;

	@Autowired
	private IEvaUnidadCentroService unidadCentroService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private IEvaInstrumentoEvaluacionService instrumentoEvaluacionService;

	/**
	 * Método extrae la lista alumnos por grupo del año académico pasado por
	 * parámetro
	 * 
	 * @param jwt  Datos del usuario
	 * @param anno Año Académico
	 * R
	 * @return List<AlumnosPorGrupoDTO> lista alumnos por grupo del año académico
	 *         indicado
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista alumnos por grupo del año académico indicado", description = "Este metodo devuelve una lista de alumnos por grupo del año académico pasado por parámetro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/alumnosPorGrupo")
	public ResponseEntity<List<AlumnosPorGrupoDTO>> alumnosPorGrupo(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("anno") Long anno,
			@RequestParam("idCentro") Long idCentro,
			@RequestParam("idDocenteSustituto") Optional<String> idDocenteSustituto) {

		try {

			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			List<AlumnosPorGrupoProjection> alumnos = new ArrayList<>();


			if (idDocenteSustituto.isPresent()) {
				alumnos = programAulaService.findAlumnosByAnyoAndEmpleados(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro, idDocenteSustituto.get());
			} else {
				alumnos = programAulaService.findAlumnosByEmpleadoAndAnyo(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro);
			}


			List<AlumnosPorGrupoDTO> alumsByEmpleadoOut = alumnos.stream()
					.map(x -> modelMapper.map(x, AlumnosPorGrupoDTO.class)).collect(Collectors.toList());
			if (!alumsByEmpleadoOut.isEmpty()) {
				alumsByEmpleadoOut.forEach(alumno -> alumno.setNumeroAlumnosSinProgramacion(alumno.getNumeroAlumnos() - alumno.getNumeroAlumnosConProgramacion()));
			}

			return new ResponseEntity<>(alumsByEmpleadoOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método extrae las Programaciones Aula del empleado
	 * 
	 * @param jwt Datos del usuario
	 * 
	 * @return List<ProgramacionAulaDTO> lista de Programaciones Aula del empleado
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Lista de Programaciones Aula del empleado", description = "Este metodo devuelve una lista con todas las Programaciones Aula del empleado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/aulasProgramacion")
	public ResponseEntity<List<ProgramacionAulaProjection>> programacionesAulaByEmpleado(
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("anno") Long anno,
			@RequestParam("idCentro") Long idCentro,
			@RequestParam(value = "idsEmpleadosCompartidas") Optional<String> idsEmpleadosCompartidas,
			@RequestParam(value = "idEmpleado", required = false) Long idEmpleado) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			List<ProgramacionAulaProjection> programacionesAula = null;
			if (idsEmpleadosCompartidas.isPresent()) {
				programacionesAula = programAulaService.findProgramacionesAulaByEmpleadosAndAnyo(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro, idsEmpleadosCompartidas.get());
			} else {
				programacionesAula = programAulaService.findProgramacionesAulaByEmpleadoAndAnyo(idEmpleado, anno, idCentro);
			}

			return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

    /**
     * Método extrae las Programaciones Aula del empleado para el rol direccion
     *
     * @param jwt Datos del usuario
     *
     * @return List<ProgramacionAulaDTO> lista de Programaciones Aula del empleado
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
    @Operation(summary = "Lista de Programaciones Aula del empleado", description = "Este metodo devuelve una lista con todas las Programaciones Aula del empleado", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/aulasProgramacionDireccion")
    public ResponseEntity<List<ProgramacionAulaProjection>> programacionesAulaByCursoMateriaUnidad(
            @RequestHeader(Constants.AUTHORIZATION) String jwt,
            @RequestParam("anno") Long anno,
            @RequestParam("idCentro") Long idCentro,
            @RequestParam("idOfertaMatrig") Long idOfertaMatrig,
            @RequestParam("idMateriaOMG") Long idMateriaOMG,
            @RequestParam("idUnidad") Long idUnidad) {

        try {

            List<ProgramacionAulaProjection> programacionesAula = programAulaService.findProgramacionesAulaByCursoMateriaUnidad(anno, idCentro, idOfertaMatrig, idMateriaOMG, idUnidad);


            return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


	/**
	 * Método actualiza una programación de aula
	 *
	 * @RequestBody programacionAulaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Actualiza una programación de aula", description = "Este método actualiza una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateActividad")
	public ResponseEntity<HttpStatus> updateActividad(@RequestBody ActividadDTO actividad) {
		try {
			programAulaService.updateActividad(actividad);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método extrae los alumnos de una materia
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de alumnos por materia", description = "Lista de alumnos por materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/alumnosByProgramacionDidactica")
	public ResponseEntity<List<AlumnosPorMateriaDTO>> findAlumnosByProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																						 @RequestParam("fechaTomaPosesion") String fechaTomaPosesion,
																						 @RequestParam("idDidac") Long idDidac,
																						 @RequestParam(value = "nivelCurricular", required = false) Long nivelCurricular) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<AlumnosPorMateriaDTO> programacionesAula = alumnoService
					.findAlumnosByMateria(datosUsuario.getIdEmpleadoDelphos(), fechaTomaPosesion, idDidac, nivelCurricular).stream()
					.map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que rescata los alumnos disponibles y ya asociados a una programación de aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de alumnos por materia", description = "Lista de alumnos por materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGestionarAlumnadoProgramacionAula")
	public ResponseEntity<List<AlumnosPorMateriaDTO>> getGestionarAlumnadoProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																						   @RequestParam("idProgramacionAula") Long idProgramacionAula,
																						   @RequestParam("nivelCurricular") Long nivelCurricular,
																						   @RequestParam Optional<Long> idEmpleadoProg,
																						   @RequestParam Long idCentro) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			Long idEmpleado = datosUsuario.getIdEmpleadoDelphos();

			if(idEmpleadoProg.isPresent()) {
				idEmpleado = idEmpleadoProg.get();
			}

			List<AlumnosPorMateriaDTO> programacionesAula = alumnoService
					.getGestionarAlumnadoProgramacionAula(idEmpleado, idProgramacionAula, nivelCurricular,idCentro).stream()
					.map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que rescata los alumnos asociados a una programación de aula para poder asociarlos a las actividades
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de alumnos por materia", description = "Lista de alumnos por materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosProgramacionAula")
	public ResponseEntity<List<AlumnosPorMateriaDTO>> getAlumnosProgramacionAula(@RequestParam("idProgramacionAula") Long idProgramacionAula,
																				 @RequestParam(value = "idActividad", required = false) Long idActividad) {

		try {

			List<AlumnosPorMateriaDTO> alumnosOut = alumnoService.getAlumnosProgramacionAula(idProgramacionAula, idActividad);

			return new ResponseEntity<>(alumnosOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método extrae las Plantillas de las Programaciones Aula del empleado
	 * 
	 * @param jwt Datos del usuario
	 * 
	 * @return List<ProgramacionAulaDTO> lista de Programaciones Aula del empleado
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de Plantillas de Programaciones Aula del empleado", description = "Este metodo devuelve una lista con todas las Plantillas de Programaciones Aula del empleado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/plantillas")
	public ResponseEntity<List<ProgramacionAulaDTO>> getPlantillasProgramacionAulaByEmpleado(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			List<ProgramacionAulaDTO> programacionesAula = programAulaService
					.getPlantillasProgramacionAulaByEmpleado(datosUsuario.getIdEmpleadoDelphos());

			return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método extrae las unidades de una materia y centro indicados
	 * 
	 * @param idDidac      Id. de la materia
	 * @param codigoCentro Código del centro
	 * @param anno
	 * 
	 * @return List<UnidadPorMateriaDTO> Lista de unidades de una materia y centro
	 *         indicados
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de unidades por materia y centro", description = "Lista de alumnos por materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/unidadesPorProgramacionDidactica")
	public ResponseEntity<List<UnidadPorMateriaDTO>> findUnidadesByProgramacionDidactica(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																						 @RequestParam("idDidac") Long idDidac, @RequestParam("codigoCentro") Long codigoCentro,
																						 @RequestParam("anno") Long anno,
																						 @RequestParam Optional<Long> idEmpleadoProg) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			Long idEmpleado = datosUsuario.getIdEmpleadoDelphos();

			if(idEmpleadoProg.isPresent()) {
				idEmpleado = idEmpleadoProg.get();
			}

			List<UnidadPorMateriaDTO> unidadesPorMateria = unidadCentroService
					.findUnidadesByProgramacionDidactica(idEmpleado, idDidac, codigoCentro, anno);

			return new ResponseEntity<>(unidadesPorMateria, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método que devuelve un objeto de una Programación de Aula por su
	 * identificador
	 * 
	 * @param idProgAula
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Programación de Aula", description = "Este metodo devuelve un objeto de una Programación de Aula por su identificador", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProgramacionAulaById")
	public ResponseEntity<ProgramacionAulaDTO> getProgramacionAulaById(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idProgAula") Long idProgAula) {
		try {
			ProgramacionAulaDTO programacionAulaOut = programAulaService.getProgramacionAulaById(idProgAula);
			return new ResponseEntity<>(programacionAulaOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que inserta en la BBDD de Delphos la plantilla de la programación de
	 * aula asociada a un empleado
	 *
	 * @RequestBody programacionAulaDTO
	 */
	/*
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Inserta plantilla de la programación de aula", description = "Este método inserta una plantilla de la programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/insertPlantillaProgramacionAula")
	public ResponseEntity<HttpStatus> insertPlantillaProgramacionAula(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam Long idMateriaOmg,
			@RequestParam Long idCentro, @RequestParam Long anno, @RequestParam Long idOfertamatrig,
			@RequestParam Long idnivelCurricular, @RequestBody ProgramacionAulaDTO programacionAula) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			programAulaService.insertPlantillaProgramacionAula(datosUsuario.getIdEmpleadoDelphos(), idMateriaOmg,
					idCentro, anno, idOfertamatrig, idnivelCurricular, programacionAula);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
*/
	/**
	 * Método que actualiza en la BBDD de Delphos la programación de aula
	 *
	 * @RequestBody programacionAulaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Actualiza programación de aula", description = "Este método actualiza una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateProgramacionAula")
	public ResponseEntity<HttpStatus> updateProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestBody ProgramacionAulaDTO programacionAula) {
		try {
			programAulaService.updateProgramacionAula(programacionAula);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que elimina en la BBDD de Delphos la programación de aula asociada a
	 * un empleado
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Eliminar programación de aula", description = "Este método elimina una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteProgramacionAula")
	public ResponseEntity<Void> deleteProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam Long idProgramacionAula) {

		if (programAulaService.deleteProgramacionAula(idProgramacionAula)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Método que devuelve curso programacion de aula
	 * 
	 * @param idCentro
	 * @param anno
	 * @param direccion
	 * @return List<OfertaMatriculaGenericoDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve una lista con todos los cursos para un centro para programación aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cursosProgramacionAula")
	public ResponseEntity<List<CursoProgramacionAulaDTO>> getCursosProgramacionAula(
			@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("idCentro") Long idCentro,
			@RequestParam("anno") Integer anno,
			@RequestParam("direccion") Boolean direccion) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<CursoProgramacionAulaDTO> cursosOut = programAulaService.getCursosProgramacionAulaByCentroAndAnno(datosUsuario.getIdEmpleadoDelphos(), idCentro, anno, direccion);
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);

	}


	/**
	 * Método que devuelve materia programacion de aula
	 * 
	 * @param idCentro
	 * @param idOfermatrig
	 * @param anno
	 * @param direccion
	 * @return List<MateriaProgramacionAulaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Lista de materias", description = "Este metodo devuelve una lista con todos las materias para un centro para programación aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/materiasProgramacionAula")
	public ResponseEntity<List<MateriaProgramacionAulaDTO>> getMateriasProgramacionAula(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCentro") Long idCentro,
			@RequestParam("idOfermatrig") Long idOfermatrig, @RequestParam("anno") Integer anno,
			@RequestParam("idsDocenteSust") List<Long> idsDocenteSust,
			@RequestParam("direccion") Boolean direccion) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			List<MateriaProgramacionAulaDTO> materiasOut = null;

			if(!direccion){
				materiasOut = programAulaService.getMateriasProgramacionAula(datosUsuario.getIdEmpleadoDelphos(), idCentro,
						idOfermatrig, anno, idsDocenteSust);
			} else{
				materiasOut = programAulaService.getMateriasProgramacionAula_dir(idCentro,
						idOfermatrig, anno);
			}

			return new ResponseEntity<>(materiasOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error al obtener las materias de la programación aula", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que devuelve la lista de docentes con programacion de aula de un centro, para la preseleccion de prog. aula por profesores del perfil direccion
	 *
	 * @param idCentro
	 * @param anno
	 * @return List<DocenteProgAulaDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de Programaciones Aula del empleado", description = "Este metodo devuelve una lista con todas las Programaciones Aula del empleado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/docentesProgAula")
	public ResponseEntity<List<DocenteProgAulaDTO>> getDocentesProgAula(
			@RequestParam("anno") Long anno,
			@RequestParam("idCentro") Long idCentro) {

		try {

			List<DocenteProgAulaProjection>  docentesProjection = programAulaService.getDocentesProgAula(anno, idCentro);

			List<DocenteProgAulaDTO> docentesOut = docentesProjection.stream()
					.map(x -> modelMapper.map(x, DocenteProgAulaDTO.class)).collect(Collectors.toList());


			return new ResponseEntity<>(docentesOut, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método que obtiene de la BBDD de Delphos la programación didáctica de las
	 * aulas de programación
	 *
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Obtiene la programación didáctica", description = "Obtiene la programación didáctica de las aulas de programación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/programacionDidacticaAulas")
	public ResponseEntity<ProgramacionDidacticaDTO> programacionDidacticaAulas(@RequestParam Long idDidactica) {
		try {
			ProgramacionDidacticaDTO programacionDidacticaOut = programAulaService
					.programacionDidacticaAulas(idDidactica);

			return new ResponseEntity<>(programacionDidacticaOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que devuelve curso programacion de aula
	 * 
	 * @param idCentro
	 * @param anno
	 * @return List<OfertaMatriculaGenericoDTO>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve una lista con todos los cursos para un centro para plantilla", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cursosPlantilla")
	public ResponseEntity<List<CursoProgramacionAulaDTO>> getCursosPlantillaByCentroAndAnno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCentro") Long idCentro, @RequestParam("anno") Integer anno, @RequestParam("anno") Boolean direccion) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<CursoProgramacionAulaDTO> cursosOut = programAulaService.getCursosProgramacionAulaByCentroAndAnno(datosUsuario.getIdEmpleadoDelphos(), idCentro, anno, direccion);
    	return new ResponseEntity<>(cursosOut, HttpStatus.OK);
    	
	}
    
    /** Método que inserta una programación de aula
     * 
     * @RequestBody DataProgramacionAulaInsertDTO
	 *
     * @return HttpStatus
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Inserta una programación de aula", description = "Inserta una programación de aula (EVA_PROGAULA)", 
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/insertProgramacionAula")

    public ResponseEntity<Long> insertProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
											    		@RequestParam("idDidac") Long idDidac,
											    		@RequestParam(value = "idProAula", required = false) Long idProAula,
											    		@RequestBody DataProgramacionAulaInsertDTO data) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        	
		    Long idAula = programAulaService.insertProgramacionAula(datosUsuario.getIdEmpleadoDelphos(), idDidac, data, idProAula);
		    return new ResponseEntity<>(idAula, HttpStatus.OK);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

	/**
	 * Método obtiene la lista con todos los intrumentos de evaluación
	 * 
	 * @return List<InstrumentoEvaluacionDTO> lista de intrumentos de evaluación
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de intrumentos de evaluación", description = "Este metodo devuelve una lista de intrumentos de evaluación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/instrumentosEvaluacion")
	public ResponseEntity<List<InstrumentoEvaluacionDTO>> getInstrumentosEvaluacion() {
		try {
			return new ResponseEntity<>(instrumentoEvaluacionService.findAll(), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener los intrumentos de evaluación", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método obtiene la lista con todas las actividades de una unidad de programación
	 *
	 * @return ResponseEntity<HttpStatus> estatus
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Obtener actividades", description = "Este método obtiene la lista con todas las actividades de una unidad de programación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getActividades")
	public ResponseEntity<List<ActividadDTO>> getActividades(@RequestParam("idUnidadProgramacion") Long idUnidadProgramacion, @RequestParam("idProgramacionAula") Long idProgramacionAula) {
		try {
			List<ActividadDTO> actividadesOut = programAulaService.getActividades(idUnidadProgramacion, idProgramacionAula);
			return new ResponseEntity<>(actividadesOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener las actividades ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método inserta una actividad con sus criterios y alumnos
	 *
	 * @return ResponseEntity<HttpStatus> estatus
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Inserta una actividad", description = "Este método inserta una actividad con sus criterios y alumnos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/insertActividad")
	public ResponseEntity<HttpStatus> insertActividad(@RequestBody ActividadDTO actividad) {
		try {
			programAulaService.insertActividad(actividad);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al insertar la actividad ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
     * Comprobamos si el docente tiene horario y programación didáctica cerrada y si es true, le permitimos crear una programación de aula.
     *
     *
     * @return ResponseEntity<HttpStatus>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Comprueba si el docente tiene horario y programación didáctica cerrada y si es true, le permitimos crear una programación de aula", description = "Este método revisa si existen valoraciónes de criterios asociados a una ponderación "
    		+ "y en caso de que no existan actualiza el campo que la hace editable",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/compruebaDocenteProgramacionAula")
    public ResponseEntity<Boolean> isDocenteValidoProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno, @RequestParam("idCentro") Long idCentro, @RequestParam(value = "idsEmpleadosCompartidas") Optional<String> idsEmpleadosCompartidas) {
        try {
        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        	Boolean permitido = false;
        	if(idsEmpleadosCompartidas.isPresent()) {
        		permitido = programAulaService.isDocenteValidoProgramacionAula(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro, idsEmpleadosCompartidas.get());
        	} else {
        		permitido = programAulaService.isDocenteValidoProgramacionAula(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro);
        	}
            return new ResponseEntity<>(permitido, HttpStatus.OK);
        } catch (Exception ex) {
			log.error("Se ha producido un error al comprobar el docente ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
    
    /**
	 * Método que elimina en la BBDD de Delphos la programación de aula asociada a
	 * un empleado
	 *
	 * @RequestBody programacionDidacticaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Eliminar programación de aula", description = "Este método elimina una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteActividad")
	public ResponseEntity<Void> deleteActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam Long idActividad) {

		if (programAulaService.deleteActividad(idActividad).booleanValue()) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Edita el nombre de una programación de aula
	 *
	 *
	 * @return ResponseEntity<HttpStatus>
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Edita el nombre de una programación de aula", description = "Edita el nombre de una programación de aula",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/editNombreProgramacionAula")
	public ResponseEntity<HttpStatus> editNombreProgramacionAula(@RequestParam("idProgramacionAula") Long idProgramacionAula,
																 @RequestBody String nombre) {
		try {
			programAulaService.editNombreProgramacionAula(idProgramacionAula, nombre);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al editar el nombre de la programación didáctica", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtiene la lista con todas las actividades de una unidad de programación
	 *
	 * @return ResponseEntity<HttpStatus> estatus
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Obtener relacion actividades y criterios con ponderacion en los cr", description = "Este método obtiene la lista con todas las actividades con su relacion de criterios ponderados de una unidad de programación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getActividadesCriteriosPonderacion")
	public ResponseEntity<List<ActividadDTO>> getActividadesCriteriosPonderacion(@RequestParam("idUnidadProgramacion") Long idUnidadProgramacion, @RequestParam("idProgramacionAula") Long idProgramacionAula) {
		try {
			List<ActividadDTO> actividadesOut = programAulaService.getActividadesCriteriosPonderacion(idUnidadProgramacion, idProgramacionAula);
			return new ResponseEntity<>(actividadesOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener las actividades ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
     * Comprobamos si la Programación de Aula puede eliminarse.
     *
     * @param idProgramacionAula Id. de la Prog. Aula
     * @return true si puede eliminarse, false en caso contrario
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Comprueba si la Programación de Aula puede eliminarse",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/compruebaEliminable")
    public ResponseEntity<Boolean> isProgramacionAulaEliminable(@RequestParam("idProgramacionAula") Long idProgramacionAula) {
        try {
            Boolean eliminable = programAulaService.isProgramacionAulaEliminable(idProgramacionAula);
            return new ResponseEntity<>(eliminable, HttpStatus.OK);
        } catch (Exception ex) {
			log.error("Se ha producido un error al comprobar si la Prog. Aula puede eliminarse ", ex);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		}
    }
    
    /**
	 * Método que rescata las competencias específicas asociadas a las actividades de una programación de aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de competencias específicas de las actividades de una programación de aula", description = "Lista de competencias específicas de las actividades de una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCompetenciasEspecificasProgramacionAula")
	public ResponseEntity<List<CompetenciaEspecificaDidacticaDTO>> getCompetenciasEspecificasByProgramacionAula(@RequestParam("idProgramacionAula") Long idProgramacionAula) {
		try {
			List<CompetenciaEspecificaDidacticaDTO> competenciasEspecificasOut = programAulaService.getCompetenciasEspecificasByProgramacionAula(idProgramacionAula);
			return new ResponseEntity<>(competenciasEspecificasOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener las competencias específicas ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtiene la lista con todas las actividades de una unidad de programación
	 *
	 * @return ResponseEntity<HttpStatus> estatus
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Obtener actividades", description = "Este método obtiene la lista con todas las actividades de una unidad de programación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCriteriosActividadesPonderaciones")
	public ResponseEntity<Page<CriterioActividadPonderacionDTO>> getCriteriosActividadesPonderaciones(@RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("idCompetenciaEspecifica") Long idCompetenciaEspecifica, @RequestParam("idCriterioEvaluacion") Long idCriterioEvaluacion, @RequestParam("page") int page, @RequestParam("numItems") int numItems) {
		try {
			Page<CriterioActividadPonderacionDTO> criteriosOut = programAulaService.getCriteriosActividadesPonderaciones(idProgramacionAula, idCompetenciaEspecifica, idCriterioEvaluacion, page, numItems);
			return new ResponseEntity<>(criteriosOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener las actividades ponderaciones criterios ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método que rescata los criterios de evaluación asociados a una competencia específica de las actividades de una programación de aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Lista de criterios de evaluación de una competencia específica de las actividades de una programación de aula", description = "Lista de criterios de evaluación de una competencia específica de las actividades de una programación de aula", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCriteriosEvaluacionProgramacionAulaCompetenciaEspecifica")
	public ResponseEntity<List<CriterioEvaluacionDTO>> getCriteriosEvaluacionByProgramacionAulaAndCompetenciaEspecifica(@RequestParam("idProgramacionAula") Long idProgramacionAula, @RequestParam("idCompetenciaEspecifica") Long idCompetenciaEspecifica) {
		try {
			List<CriterioEvaluacionDTO> criteriosEvaluacionOut = programAulaService.getCriteriosEvaluacionByProgramacionAulaAndCompetenciaEspecifica(idProgramacionAula, idCompetenciaEspecifica);
			return new ResponseEntity<>(criteriosEvaluacionOut, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al obtener los criterios de evaluación ", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método inserta una lista de actividades con sus criterios y alumnos
	 *
	 * @return true si ha ido todo bien, false en caso contrario
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
	@Operation(summary = "Inserta una lista de actividades", description = "Este método inserta una lista de actividades con sus criterios y alumnos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/insertActividades")
	public ResponseEntity<Boolean> insertActividades(@RequestBody List<ActividadDTO> actividades) {
		try {
			Boolean res = programAulaService.insertActividades(actividades);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Se ha producido un error al insertar la actividad ", ex);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
		}
	}
	
	/**
	 * Método extrae los alumnos ACNEE que no están asignados a una programación de aula
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
	@Operation(summary = "Lista de alumnos por materia", description = "Lista de alumnos por materia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/alumnosACNEESinProgramacionAula")
	public ResponseEntity<List<AlumnosPorMateriaDTO>> findAllAlumnosACNEESinProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																						@RequestParam("fechaTomaPosesion") String fechaTomaPosesion,
																			            @RequestParam("idCentro") Long idCentro,
																			            @RequestParam("anno") Integer anno,
																			            @RequestParam("idsEmpleadosCompartidas") Optional<String> idsEmpleadosCompartidas,
																			            @RequestParam("fechasTomaPosesionCompartidas") Optional<String> fechasTomaPosesionCompartidas,
																			            @RequestParam("direccion") Optional<Long> direccion) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			List<Long> idsEmpleado = new ArrayList<>();
	    	List<String> fechasTomaPosesion = new ArrayList<>();
	    	
	    	idsEmpleado.add(datosUsuario.getIdEmpleadoDelphos());
	    	fechasTomaPosesion.add(fechaTomaPosesion);
	    	
	    	if (idsEmpleadosCompartidas.isPresent() && fechasTomaPosesionCompartidas.isPresent()) {
	    		idsEmpleado.addAll(Arrays.asList((Long[]) ConvertUtils.convert(idsEmpleadosCompartidas.get().split(","), Long[].class)));
	    		fechasTomaPosesion.addAll(Arrays.asList(fechasTomaPosesionCompartidas.get().split(",")));
	    	}

			List<AlumnosPorMateriaDTO> programacionesAula = alumnoService
					.findAllAlumnosACNEESinProgramacionAula(idsEmpleado, fechasTomaPosesion, idCentro, anno, direccion.orElse(0L))
					.stream().map(x -> modelMapper.map(x, AlumnosPorMateriaDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(programacionesAula, HttpStatus.OK);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Método que añade los alumnos seleccionados a las actividades seleccionadas
	 *
	 * @RequestBody programacionAulaDTO
	 */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
	@Operation(summary = "Añade los alumnos seleccionados a las actividades seleccionadas", description = "Este método añade los alumnos seleccionados a las actividades seleccionadas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/asignarAlumnosActividades")
	public ResponseEntity<HttpStatus> insertAlumnosActividades(@RequestParam("idsMatricula") List<Long> idsMatricula, @RequestParam("idsActividad") List<Long> idsActividad) {
		try {
			programAulaService.insertAlumnosActividades(idsMatricula, idsActividad);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
