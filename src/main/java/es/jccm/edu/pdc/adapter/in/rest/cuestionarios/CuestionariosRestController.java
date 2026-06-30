package es.jccm.edu.pdc.adapter.in.rest.cuestionarios;

import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.*;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.*;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.CursoAcademicoProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.SugerenciasMejorasProjection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import es.jccm.edu.pdc.application.ports.in.cuestionarios.ICuestionarioService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/pdc" + "/cuestionario")
@Tag(name = "Servicio Cuestionarios", description = "Servicio con las operaciones sobre Cuestionarios")
@CrossOrigin
public class CuestionariosRestController {

	@Autowired
	ICuestionarioService cuestionarioService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private ModelMapper modelMapper;
	
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Cuestionario docente", description = "Este metodo devuelve el Cuestionario docente", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/docente")
	public ResponseEntity<CuestionarioDto> getCuestionarioDocenteByCodigo(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("codCentro") Long codCentro, @RequestParam("codCuestionario") String codCuestionario) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		Cuestionario cuestionario = cuestionarioService.getCuestionarioDocenteByCodigo(datosUsuario.getXUsuarioComunica(),
				codCentro, codCuestionario);
		CuestionarioDto cuestionarioDto = modelMapper.map(cuestionario, CuestionarioDto.class);

		try {
			if (cuestionario == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CuestionarioDto>(cuestionarioDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Cuestionario centro", description = "Este metodo devuelve el Cuestionario del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centro")
	public ResponseEntity<CuestionarioDto> getCuestionarioCentro(@RequestParam("idCentro") Long idCentro,
			@RequestParam("anio") Long anio) {

		
		Cuestionario cuestionario = cuestionarioService.getCuestionarioCentro(idCentro,anio);
		CuestionarioDto cuestionarioDto = modelMapper.map(cuestionario, CuestionarioDto.class);

		try {
			if (cuestionario == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CuestionarioDto>(cuestionarioDto, HttpStatus.OK);
	}
	

	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Secciones", description = "Este metodo devuelve las secciones de un Cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/secciones")
	public ResponseEntity<List<SeccionDto>> getSeccionesByIdCuestionario(
			@RequestParam("idCuestionario") Long idCuestionario) {

		List<Seccion> secciones = cuestionarioService.getSeccionesByIdCuestionario(idCuestionario);
		List<SeccionDto> seccionesOut = secciones.stream().map(x -> modelMapper.map(x, SeccionDto.class))
				.collect(Collectors.toList());
		try {
			if (seccionesOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<SeccionDto>>(seccionesOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Preguntas", description = "Este metodo devuelve las preguntas de una seccion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/preguntas")
	public ResponseEntity<List<PreguntaDto>> getSeccionesByIdSeccion(@RequestParam("idSeccion") Long idSeccion) {

		List<Pregunta> preguntas = cuestionarioService.getPreguntasByIdSeccion(idSeccion);
		List<PreguntaDto> preguntasOut = preguntas.stream().map(x -> modelMapper.map(x, PreguntaDto.class))
				.collect(Collectors.toList());
		try {
			if (preguntasOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<PreguntaDto>>(preguntasOut, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Respuestas", description = "Este metodo devuelve las respuestas de una pregunta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/respuestas")
	public ResponseEntity<List<RespuestaDto>> getRespuestasbyIdPregunta(@RequestParam("idPregunta") Long idPregunta) {

		List<Respuesta> respuestas = cuestionarioService.getRespuestasByIdPregunta(idPregunta);
		List<RespuestaDto> respuestasOut = respuestas.stream().map(x -> modelMapper.map(x, RespuestaDto.class))
				.collect(Collectors.toList());

		try {
			if (respuestasOut.isEmpty()) {
				return new ResponseEntity<>(respuestasOut, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(respuestasOut, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<RespuestaDto>>(respuestasOut, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Set Respuesta", description = "Este metodo setea la respuesta de una pregunta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setRespuesta")
	public HttpStatus setRespuesta(@RequestParam("idCuePubUsu") Long idCuePubUsu,
			@RequestParam("idPregunta") Long idPregunta, @RequestParam("idRespuesta") Long idRespuesta,
			@RequestParam("textoRespuesta") String textoRespuesta) {
		try {
			cuestionarioService.setRespuesta(idCuePubUsu, idPregunta, idRespuesta, textoRespuesta);
		} catch (Exception e) {
			return HttpStatus.BAD_REQUEST;
		}
		return HttpStatus.OK;
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Finalizar Cuestionario", description = "Este metodo ejecuta el procedimiento para finalizar el cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/finalizarCuestionario")
	public HttpStatus finalizarCuestionario(@RequestParam("idCuePubUsu") Long idCuePubUsu,
			@RequestParam("idCuePub") Long idCuePub) {
		try {
			cuestionarioService.finalizarCuestionario(idCuePubUsu, idCuePub);
		} catch (Exception e) {
			return HttpStatus.BAD_REQUEST;
		}
		return HttpStatus.OK;
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Respuesta Seleccionada", description = "Este metodo devuelve una Lista de las repsuestas seleccionadas por el usuario",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getRespuestaSeleccionada")
	public ResponseEntity<List<RespuestaSeleccionadaDto>> getRespuestaSeleccionada(
			@RequestParam("idCuePubUsu") Long idCuePubUsu, @RequestParam("idPregunta") Long idPregunta) {

		List<RespuestaSeleccionada> respuestaSeleccionadas = cuestionarioService.getRespuestaSeleccionada(idCuePubUsu,
				idPregunta);
		List<RespuestaSeleccionadaDto> respuestasOut = respuestaSeleccionadas.stream()
				.map(x -> modelMapper.map(x, RespuestaSeleccionadaDto.class)).collect(Collectors.toList());

		try {
			if (respuestasOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<RespuestaSeleccionadaDto>>(respuestasOut, HttpStatus.OK);
	}

	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Secciones", description = "Este metodo devuelve las secciones de un Cuestionario",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cuestionarioCompleto")
	public ResponseEntity<List<SeccionDto>> getCuestionarioCompleto(@RequestParam("idCuestionario") Long idCuestionario) {

		
		List<Seccion> secciones = cuestionarioService.getSeccionesByIdCuestionario(idCuestionario);
		List<SeccionDto> seccionesOut = secciones.stream().map(x -> modelMapper.map(x, SeccionDto.class)).collect(Collectors.toList());
		for (Iterator iterator = seccionesOut.iterator(); iterator.hasNext();) {
			SeccionDto seccionDto = (SeccionDto) iterator.next();
			List<Pregunta> preguntas = cuestionarioService.getPreguntasByIdSeccion(seccionDto.getIdSeccion());
			List<PreguntaDto> preguntasOut = preguntas.stream().map(x -> modelMapper.map(x, PreguntaDto.class)).collect(Collectors.toList());
			List<PreguntaDto> tmp = new ArrayList<PreguntaDto>();
			for (Iterator iterator2 = preguntasOut.iterator(); iterator2.hasNext();) {
				PreguntaDto preguntasDto = (PreguntaDto) iterator2.next();
				List<Respuesta> respuestas = cuestionarioService.getRespuestasByIdPregunta(preguntasDto.getIdPregunta());
				List<RespuestaDto> respuestasOut = respuestas.stream().map(x -> modelMapper.map(x, RespuestaDto.class)).collect(Collectors.toList());
				preguntasDto.setListaRespuesta(respuestas);
				
				tmp.add(preguntasDto);
				seccionDto.setPreguntas(tmp);
			}			
		}
		try {
			if (seccionesOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<SeccionDto>>(seccionesOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Areas Ambito Cinco", description = "Este metodo devuelve una lista con los datos de los de las areas del ambito cinco.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAreasAmbitoCinco")
	public ResponseEntity<List<ValoresAmbitoCincoDto>> getAreasAmbitoCinco(@RequestParam("codCentro") Long codCentro, @RequestParam("x_cuepub") String x_cuepub){
		List<ValoresAmbitoCinco> informe = cuestionarioService.getAreasAmbitoCinco(codCentro,x_cuepub);
		List<ValoresAmbitoCincoDto> informeOut = informe.stream().map(x -> modelMapper.map(x, ValoresAmbitoCincoDto.class)).collect(Collectors.toList());
		try {
			if (informeOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ValoresAmbitoCincoDto>>(informeOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Sum Areas Ambito Cinco", description = "Este metodo devuelve una lista con la suma total de los datos del las areas del ambito cinco.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSumAreasAmbitoCinco")
	public ResponseEntity<List<ValoresAmbitoCincoDto>> getSumAreasAmbitoCinco(@RequestParam("codCentro") Long codCentro, @RequestParam("x_cuepub") String x_cuepub){
		List<ValoresAmbitoCinco> informe = cuestionarioService.getSumAreasAmbitoCinco(codCentro,x_cuepub);
		List<ValoresAmbitoCincoDto> informeOut = informe.stream().map(x -> modelMapper.map(x, ValoresAmbitoCincoDto.class)).collect(Collectors.toList());
		try {
			if (informeOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ValoresAmbitoCincoDto>>(informeOut, HttpStatus.OK);
	}


	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Sugerencias Mejoras cuestionario docente", description = "Este metodo devuelve las sugerencias y mejoras del cuestionario docente",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSugerenciasMejoras/docente")
	public ResponseEntity<List<SugerenciasMejorasProjection>> getSugMejDocente(@RequestParam("xCuepub") Long xCuepub,
																		@RequestParam("xCentro") Long xCentro,
																		@RequestParam("xUsuario") Long xUsuario){

		List<SugerenciasMejorasProjection> sugerenciasMejoras = cuestionarioService.getSugerenciasMejorasCuestionarioDocente(xCentro, xUsuario, xCuepub);


		try {
			if (sugerenciasMejoras.equals(null)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<SugerenciasMejorasProjection>>(sugerenciasMejoras, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Sugerencias Mejoras del cuestionario del centro", description = "Este metodo devuelve las sugerencias y mejoras del cuestionario del centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSugerenciasMejoras/centro")
	public ResponseEntity<List<SugerenciasMejorasProjection>> getSugMejCentro(@RequestParam("xCuepub") Long xCuepub,@RequestParam("xCentro") Long xCentro){

		List<SugerenciasMejorasProjection> sugerenciasMejoras = cuestionarioService.getSugerenciasMejorasCuestionarioCentro(xCentro,xCuepub);

		try {
			if (sugerenciasMejoras.equals(null)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<SugerenciasMejorasProjection>>(sugerenciasMejoras, HttpStatus.OK);
	}

	
	/**
	 * Get Lista de años donde se han finalizado un cuestionario.
	 *
	 * @return lista de años
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Historico cuestionario docente", description = "Este metodo devuelve el historico cuestionario docente", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/historico/docente")
	public ResponseEntity<List<HistoricoCuestionarioDto>> getHistoricoDocente(@RequestParam("idCentro") Long idCentro) {
			
		List<HistoricoCuestionario> l= cuestionarioService.getHistoricoCuestionarioDocente(idCentro);
		List<HistoricoCuestionarioDto> listado = l.stream().map(x -> modelMapper.map(x, HistoricoCuestionarioDto.class)).collect(Collectors.toList());
		try {
			if (listado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<HistoricoCuestionarioDto>>(listado, HttpStatus.OK);
	}
	
	/**
	 * Get Lista de años donde se han finalizado un cuestionario.
	 *
	 * @return lista de años
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Historico cuestionario docente", description = "Este metodo devuelve el historico cuestionario docente", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/historico/docente/individual")
	public ResponseEntity<List<HistoricoCuestionarioDto>> getHistoricoDocente(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("idCentro") Long idCentro) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		Long xUsuario = datosUsuario.getXUsuarioComunica();
		
		List<HistoricoCuestionario> l= cuestionarioService.getHistoricoCuestionarioDocenteIndividual(xUsuario,idCentro);
		List<HistoricoCuestionarioDto> listado = l.stream().map(x -> modelMapper.map(x, HistoricoCuestionarioDto.class)).collect(Collectors.toList());
		try {
			if (listado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<HistoricoCuestionarioDto>>(listado, HttpStatus.OK);
	}
	
	/**
	 * Get Lista de años donde se han finalizado un cuestionario.
	 *
	 * @return lista de años
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Historico cuestionario centro", description = "Este metodo devuelve el historico del cuestionario del centro donde se ha finalizado un cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/historico/centro")
	public ResponseEntity<List<HistoricoCuestionarioDto>> getHistoricoCuestionarioCentro(@RequestParam("idCentro") Long idCentro) {
			
		List<HistoricoCuestionario> l= cuestionarioService.getHistoricoCuestionarioCentro(idCentro);
		List<HistoricoCuestionarioDto> listado = l.stream().map(x -> modelMapper.map(x, HistoricoCuestionarioDto.class)).collect(Collectors.toList());
		try {
			if (listado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<HistoricoCuestionarioDto>>(listado, HttpStatus.OK);
	}
	
	/**
	 * Get Media por area para un usuario dado.
	 *
	 * @return lista de areas con su media
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Lista medias por area para un usuario dado", description = "Este metodo devuelve una lista de areas con su media", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/media/usuario/area")
	public ResponseEntity<List<MediaPorAreaDto>> getMediaPorAreaUsuario(@RequestParam("x_cuepubusu") String x_cuepubusu) {
		boolean isFormatoValido = (x_cuepubusu != null&& x_cuepubusu.matches("[0-9]+"));
		if(!isFormatoValido) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<MediaPorArea> l = cuestionarioService.getMediaPorAreaUsuario(x_cuepubusu);
		List<MediaPorAreaDto> listado = l.stream().map(x -> modelMapper.map(x, MediaPorAreaDto.class)).collect(Collectors.toList());
		
		try {
			if (listado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<MediaPorAreaDto>>(listado, HttpStatus.OK);
	}
	
	/**
	 * Get Media por area para todos los centros de castilla la mancha.
	 *
	 * @return lista de areas con su media
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Lista medias por area para todos los centros de castilla la mancha", description = "Media por area para todos los centros de castilla la mancha", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/media/castillaLaMancha/area")
	public ResponseEntity<List<MediaPorAreaDto>> getMediaPorCastillaLaMancha(@RequestParam("x_cuepub") String x_cuepub) {
		boolean isFormatoValido = (x_cuepub != null&& x_cuepub.matches("[0-9]+"));
		if(!isFormatoValido) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<MediaPorArea> l = cuestionarioService.getMediaPorCastillaLaMancha(x_cuepub);
		
		List<MediaPorAreaDto> listado = l.stream().map(x -> modelMapper.map(x, MediaPorAreaDto.class)).collect(Collectors.toList());
		try {
			if (listado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<MediaPorAreaDto>>(listado, HttpStatus.OK);
	}
	
	/**
	 * Get Media total y el nivel de un usuario concreto.
	 *
	 * @return lista de areas con su media
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Media total y el nivel de un usuario concreto", description = "Media total y el nivel de un usuario concreto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/media/usuario/total")
	public ResponseEntity<MediaPorAreaDto> getMediaTotalUsuario(@RequestParam("x_cuepubusu") String x_cuepubusu) {
		boolean isFormatoValido = (x_cuepubusu != null&& x_cuepubusu.matches("[0-9]+"));
		if(!isFormatoValido) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		MediaPorArea m = cuestionarioService.getMediaTotalUsuario(x_cuepubusu);
		MediaPorAreaDto media = modelMapper.map(m, MediaPorAreaDto.class);
		
		try {
			if (media == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MediaPorAreaDto>(media, HttpStatus.OK);
	}
	
	
	/**
	 * Get Cuestionario
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Cuestionario del docente", description = "Este metodo devuelve el Cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/xcuestionario/docente")
	public ResponseEntity<CuestionarioDto> getCuestionarioDocenteByXCUEPUB(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestParam("xcentro") String xcentro, @RequestParam("xcues") String xcues) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		Cuestionario cuestionario = cuestionarioService.getCuestionarioDocenteByXCUEPUB(datosUsuario.getXUsuarioComunica(),
				xcentro, xcues);
		CuestionarioDto cuestionarioDto = modelMapper.map(cuestionario, CuestionarioDto.class);

		try {
			if (cuestionario == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CuestionarioDto>(cuestionarioDto, HttpStatus.OK);
	}
	/**
	 * Get Cuestionario
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Cuestionario del centro", description = "Este metodo devuelve el Cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/xcuestionario/centro")
	public ResponseEntity<CuestionarioDto> getCuestionarioByXCUEPUB(
			@RequestParam("xcentro") String xcentro, @RequestParam("xcues") String xcues) {

		Cuestionario cuestionario = cuestionarioService.getCuestionarioCentroByXCUEPUB(xcentro, xcues);
		CuestionarioDto cuestionarioDto = modelMapper.map(cuestionario, CuestionarioDto.class);

		try {
			if (cuestionario == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CuestionarioDto>(cuestionarioDto, HttpStatus.OK);
	}
	
	/**
	 * Get Porcentaje de participantes con respecto al número de docentes del centro
	 *
	 * @return porcentaje (double)
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Get Porcentaje", description = "Este metodo devuelve el porcentaje de participantes con respecto al número de docentes del centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/porcentaje/participantes/docentes")
	public ResponseEntity<Double> getPorcentajeParticipantesDocentes(@RequestParam("x_centro") String x_centro, @RequestParam("x_cuepub") String x_cuepub) {

		
		Double porcentaje = cuestionarioService.getPorcentajeParticipantesDocentes(x_centro, x_cuepub);
			
		try {
			if (porcentaje == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Double>(porcentaje, HttpStatus.OK);
	}
	
	/**
	 * Get Cargos
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Parsem Cargos", description = "Este metodo devuelve la lista de cargos del usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cargos/usuario")
	public ResponseEntity<List<CargoPDCDto>> getCargos(@RequestHeader(Constants.AUTHORIZATION) String jwt,@RequestParam("xCentro") Long xCentro ) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		Long x_empleado = datosUsuario.getIdEmpleadoDelphos();
		List<CargoPDCDto> cargos = cuestionarioService.getCargos(x_empleado,xCentro).stream().map(x -> modelMapper.map(x, CargoPDCDto.class)).collect(Collectors.toList());;

		try {
			if (cargos == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<CargoPDCDto>>(cargos, HttpStatus.OK);
	}
	
	/**
	 * Get Curso academico
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Curso", description = "Este metodo devuelve la fecha inicio y fecha fin del curso", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/curso")
	public ResponseEntity<CursoAcademicoDTO> getCursoAcademico(@RequestParam("anio") Long anio) {		

		try {
			CursoAcademicoProjection curso = cuestionarioService.getCursoAcademino(anio);
			
			if (curso == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			CursoAcademicoDTO cursoDTO = modelMapper.map(curso,CursoAcademicoDTO.class);
			return new ResponseEntity<CursoAcademicoDTO>(cursoDTO, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Get Perfiles
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Perfiles", description = "Este metodo devuelve la lista de perfiles del usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/perfiles/usuario")
	public ResponseEntity<List<String>> getPerfiles(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Long x_empleado = datosUsuario.getIdEmpleadoDelphos();
			List<String> perfiles = cuestionarioService.getPerfiles(x_empleado);

			return new ResponseEntity<List<String>>(perfiles, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		
	}
}