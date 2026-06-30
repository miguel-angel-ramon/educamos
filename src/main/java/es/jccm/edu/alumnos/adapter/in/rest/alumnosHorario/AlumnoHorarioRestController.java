package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.AlumnoAndFaltasListDto;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.AlumnoDetalleDto;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.AlumnoHorarioDto;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.AlumnosDto;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.AsignaturaAlumnoDTO;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.HorarioSemanalAlumnoDTO;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.ListaAlumnosGrupoActividadDto;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.TutorAlumnoDTO;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoAndFaltasList;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoHorario;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Alumnos;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AsignaturaAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.HorarioSemanalAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.ListaAlumnosGrupoActividad;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.TutorAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorAlumnoProjection;
import es.jccm.edu.alumnos.application.ports.in.alumnosHorario.IAlumnoHorarioService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@SecurityRequirement(name = "apieducamosclm")
@Tag(name = "Servicio Alumnos Escritorio", description = "Servicio para recuperar los alumnos, las faltas y retrasos del escritorio")
//@CrossOrigin
public class AlumnoHorarioRestController {

	@Autowired
	private IAlumnoHorarioService alumnoHorarioService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Devuelve la cantidad de alumnos de una clase matrículados a una asignatura
	 * concreta
	 *
	 * @param idMateria
	 * @param idUnidad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Contador de alumnos por asignatura", description = "Este metodo devuelve el numero de alumnos de una clase matriculados a una asignatura concreta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/countAlumnosByAsignatura")
	public ResponseEntity<Long> getCountAlumnoHorarioByAsignatura(@RequestParam("idUnidad") Long idUnidad,
			@RequestParam("idMateria") Long idMateria) {
		return new ResponseEntity<>(alumnoHorarioService.countAlumnoHorarioByAsignatura(idUnidad, idMateria),
				HttpStatus.OK);
	}

	/**
	 * Devuelve un listado de alumnos que hayan faltado tres o más veces en el
	 * último mes
	 *
	 * @param idMateria
	 * @param idUnidad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Devuelve un listado de alumnos", description = "Devuelve un listado de alumnos que hayan faltado tres o más veces en el último mes", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosByFaltasRecurrentes")
	public ResponseEntity<List<AlumnoHorarioDto>> getAlumnosByFaltasRecurrentes(@RequestParam("idUnidad") Long idUnidad,
			@RequestParam("idMateria") Long idMateria) {

		List<AlumnoHorario> alumnosHorario = alumnoHorarioService.getAlumnosHorarioByFaltasRecurrentes(idUnidad,
				idMateria);

		List<AlumnoHorarioDto> alumnosHorarioOut = alumnosHorario.stream()
				.map(x -> modelMapper.map(x, AlumnoHorarioDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(alumnosHorarioOut, HttpStatus.OK);

	}

	/**
	 * Devuelve un listado de alumnos que hayan llegado tarde tres o más veces en el
	 * último mes
	 *
	 * @param idMateria
	 * @param idUnidad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Devuelve un listado de alumnos", description = "Devuelve un listado de alumnos que hayan llegado tarde tres o más veces en el último mes", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosByRetrasosRecurrentes")
	public ResponseEntity<List<AlumnoHorarioDto>> getAlumnosByRetrasosRecurrentes(
			@RequestParam("idUnidad") Long idUnidad, @RequestParam("idMateria") Long idMateria) {

		List<AlumnoHorario> alumnosHorario = alumnoHorarioService.getAlumnosHorarioByRetrasosRecurrentes(idUnidad,
				idMateria);

		List<AlumnoHorarioDto> alumnosHorarioOut = alumnosHorario.stream()
				.map(x -> modelMapper.map(x, AlumnoHorarioDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(alumnosHorarioOut, HttpStatus.OK);

	}

	/**
	 * Devuelve el detalle del alumno seleccionado
	 *
	 * @param idMatricula
	 * @param idMatMatricula
	 * @return AlumnoHorarioDto
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Devuelve detalle del alumno seleccionado", description = "Devuelve detalle del alumno seleccionado de una clase", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosDetalle")
	public ResponseEntity<AlumnoHorarioDto> getAlumnoDetalle(@RequestParam("idMatricula") Long idMatricula,
			@RequestParam("idMatMatricula") Long idMatMatricula) {

		AlumnoHorarioDto alumnosHorarioOut = modelMapper.map(
				alumnoHorarioService.getAlumnosHorariosDetalle(idMatricula, idMatMatricula), AlumnoHorarioDto.class);

		return new ResponseEntity<>(alumnosHorarioOut, HttpStatus.OK);

	}
	
	/**
	 * Devuelve el detalle del alumno seleccionado
	 *
	 * @param idMatricula
	 * @param anno
	 * @return AlumnoDetalleDto
	 * @throws Exception 
	 */
	@Operation(summary = "Devuelve detalle del alumno seleccionado", description = "Devuelve detalle del alumno seleccionado de una clase", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosDetalleYGruposActividad")
	public ResponseEntity<AlumnoDetalleDto> getAlumnoDetalleYGruposActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMatricula") Long idMatricula, 
			@RequestParam("anno") Integer anno) throws Exception {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
				
		AlumnoDetalleDto alumnosDetalleOut = modelMapper.map(
				alumnoHorarioService.getAlumnoDetalle(datosUsuario.getIdEmpleadoComunica(), idMatricula, anno), AlumnoDetalleDto.class);

		return new ResponseEntity<>(alumnosDetalleOut, HttpStatus.OK);

	}

	/**
	 * Devuelve un listado de alumnos por materia y día
	 *
	 * @param idTramo
	 * @param idGrupo
	 * @param fecha
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Devuelve un listado de alumnos", description = "Devuelve un listado de alumnos por materia y día", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/alumnosConFaltas")
	public ResponseEntity<List<AlumnoAndFaltasListDto>> getAlumnosAndFaltas(@RequestParam("idTramo") Long idTramo,
			@RequestParam("idGrupo") Long idGrupo, @RequestParam("fecha") String fecha) {

		List<AlumnoAndFaltasList> alumnoAndFaltasList = alumnoHorarioService.getAlumnosAndFaltas(idTramo, idGrupo,
				fecha);

		List<AlumnoAndFaltasListDto> alumnoAndFaltasOut = alumnoAndFaltasList.stream()
				.map(x -> modelMapper.map(x, AlumnoAndFaltasListDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(alumnoAndFaltasOut, HttpStatus.OK);

	}
	
	
	/**
	 * Cambia la observación del alumno
	 *
	 * @param idMatMatricula
	 * @param observacion
	 * @param idMatricula
	 * @throws UnsupportedEncodingException 
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Cambia la observación del alumno", description = "Cambia la observación del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setAlumnosObservacion")
	public ResponseEntity<AlumnoHorarioDto> setAlumnosObservacion(@RequestParam("idMatMatricula") Long idMatMatricula, 
			@RequestParam("observacion") String observacion, @RequestParam("idMatricula") Long idMatricula) throws UnsupportedEncodingException {
		
		String getobservacion = new String(Base64.getDecoder().decode(observacion),"ISO-8859-1");

		alumnoHorarioService.setAlumnoObservacion(idMatMatricula, getobservacion, idMatricula);

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	/**
	 * Devuelve un listado de alumnos por grupo
	 *
	 * @param idEmpleado
	 * @return the response entity
	 * @throws UnsupportedEncodingException 
	 */
	@Operation(summary = "Devuelve un listado por grupo", description = "Devuelve un listado de alumnos por grupo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/misAlumnos")
	public ResponseEntity<Page<AlumnosDto>> getMisAlumnos(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Long anno,
			@RequestParam("page") int page, @RequestParam("numItems") int numItems, @RequestParam("idGrupoActividad") Long idGrupoActividad, 
			@RequestParam("nombre") String nombre, @RequestParam("order") String order, @RequestParam("idCentro") Long idCentro) throws UnsupportedEncodingException {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		String nombre64 = new String(Base64.getDecoder().decode(nombre),"ISO-8859-1");

		Page<Alumnos> alumnos = alumnoHorarioService.getMisAlumnos(datosUsuario.getIdEmpleadoComunica(), anno, page, numItems, idGrupoActividad, nombre64.toLowerCase(), order, idCentro);

		List<AlumnosDto> alumnosOut = alumnos.stream()
				.map(x -> modelMapper.map(x, AlumnosDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(new PageImpl<>(alumnosOut, alumnos.getPageable(), alumnos.getTotalElements()), HttpStatus.OK);
	}
	
	/**
	 * Devuelve un listado de alumnos por grupo actividad
	 *
	 * @param idGrupoActividad
	 * @param idEmpleado
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de alumnos", description = "Devuelve un listado de alumnos a partir del grupo actividad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListaAlumnosGrupoActividad")
	public ResponseEntity<List<ListaAlumnosGrupoActividadDto>> getListaAlumnosGrupoActividad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idGrupoActividad") Long idGrupoActividad) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ListaAlumnosGrupoActividad> listaAlumnos = alumnoHorarioService.getListaAlumnosGrupoActividad(idGrupoActividad, datosUsuario.getIdEmpleadoComunica());

		List<ListaAlumnosGrupoActividadDto> listaAlumnosOut = listaAlumnos.stream()
				.map(x -> modelMapper.map(x, ListaAlumnosGrupoActividadDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(listaAlumnosOut, HttpStatus.OK);

	}
	
	/**
	 * Devuelve un listado de alumnos por unidad
	 *
	 * @param idEmpleado
	 * @return the response entity
	 * @throws UnsupportedEncodingException 
	 */
	@Operation(summary = "Devuelve un listado por grupo", description = "Devuelve un listado de alumnos por grupo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/misAlumnosByUnidad")
	public ResponseEntity<Page<AlumnosDto>> getMisAlumnosByUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Long anno,
			@RequestParam("page") int page, @RequestParam("numItems") int numItems, @RequestParam("idUnidad") Long idUnidad, 
			@RequestParam("nombre") String nombre, @RequestParam("order") String order) throws UnsupportedEncodingException {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		String nombre64 = new String(Base64.getDecoder().decode(nombre),"ISO-8859-1");

		Page<Alumnos> alumnos = alumnoHorarioService.getMisAlumnosByUnidad(datosUsuario.getIdEmpleadoComunica(), anno, page, numItems, idUnidad, nombre64.toLowerCase(), order);

		List<AlumnosDto> alumnosOut = alumnos.stream()
				.map(x -> modelMapper.map(x, AlumnosDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(new PageImpl<>(alumnosOut, alumnos.getPageable(), alumnos.getTotalElements()), HttpStatus.OK);
	}
	
	/**
	 * Devuelve el detalle del alumno seleccionado
	 *
	 * @param idMatricula
	 * @param anno
	 * @return AlumnoDetalleDto
	 * @throws Exception 
	 */
	@Operation(summary = "Devuelve detalle del alumno seleccionado", description = "Devuelve detalle del alumno seleccionado de una clase", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosDetalleYMaterias")
	public ResponseEntity<AlumnoDetalleDto> getAlumnosDetalleYMaterias(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMatricula") Long idMatricula, 
			@RequestParam("anno") Integer anno) throws Exception {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
				
		AlumnoDetalleDto alumnosDetalleOut = modelMapper.map(
				alumnoHorarioService.getAlumnosDetalleYMaterias(datosUsuario.getIdEmpleadoComunica(), idMatricula, anno), AlumnoDetalleDto.class);

		return new ResponseEntity<>(alumnosDetalleOut, HttpStatus.OK);

	}
	
	/**
	 * Devuelve un listado de alumnos por unidad
	 *
	 * @param idGrupoActividad
	 * @param idEmpleado
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de alumnos", description = "Devuelve un listado de alumnos a partir de la unidad", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListaAlumnosByUnidad")
	public ResponseEntity<List<ListaAlumnosGrupoActividadDto>> getListaAlumnosByUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idUnidad") Long idUnidad) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ListaAlumnosGrupoActividad> listaAlumnos = alumnoHorarioService.getListaAlumnosByUnidad(idUnidad, datosUsuario.getIdEmpleadoComunica());

		List<ListaAlumnosGrupoActividadDto> listaAlumnosOut = listaAlumnos.stream()
				.map(x -> modelMapper.map(x, ListaAlumnosGrupoActividadDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(listaAlumnosOut, HttpStatus.OK);

	}
	
	/**
	 * Devuelve el horario de un alumno según su matrícula
	 *
	 * @param idMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve el horario semanal de un alumno", description = "Devuelve el horario de un alumno según su matrícula", responses = {
	        @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
	        @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
	        @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
	        @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getHorarioSemanalAlumno")
	public ResponseEntity<List<HorarioSemanalAlumnoDTO>> getHorarioSemanalAlumno(@RequestParam("idMatricula") Long idMatricula) {
	    
	    List<HorarioSemanalAlumno> horarioEntities = alumnoHorarioService.getHorarioSemanalAlumno(idMatricula);

	    List<HorarioSemanalAlumnoDTO> horarioDtos = horarioEntities.stream()
	        .map(x -> modelMapper.map(x, HorarioSemanalAlumnoDTO.class))
	        .collect(Collectors.toList());

	    return new ResponseEntity<>(horarioDtos, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve las asignaturas de un alumno según su matrícula
	 *
	 * @param idMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve las asignaturas de un alumno", description = "Devuelve las asignaturas según su matrícula", responses = {
	        @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
	        @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
	        @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
	        @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAsignaturaAlumno")
	public ResponseEntity<List<AsignaturaAlumnoDTO>> getAsignaturaAlumno(@RequestParam("idMatricula") Long idMatricula) {
	    
	    List<AsignaturaAlumno> horarioEntities = alumnoHorarioService.getAsignaturaAlumno(idMatricula);

	    List<AsignaturaAlumnoDTO> horarioDtos = horarioEntities.stream()
	        .map(x -> modelMapper.map(x, AsignaturaAlumnoDTO.class))
	        .collect(Collectors.toList());

	    return new ResponseEntity<>(horarioDtos, HttpStatus.OK);
	}
	
	/**
	 * Devuelve el tutor y las horas de visita
	 *
	 * @param idMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve tutor y horario visitas", description = "Devuelve tutor y horario visitas según su matrícula", responses = {
	        @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
	        @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
	        @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
	        @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getObtenerTutorYHorario")
	public ResponseEntity<TutorAlumnoDTO> obtenerTutorYHorario(@RequestParam("idMatricula") Long idMatricula) {
	    
		TutorAlumnoDTO tutorAlumno = modelMapper.map(
				alumnoHorarioService.obtenerTutorYHorario(idMatricula),TutorAlumnoDTO.class);
	    		

	    return new ResponseEntity<>(tutorAlumno, HttpStatus.OK);
	}
}


