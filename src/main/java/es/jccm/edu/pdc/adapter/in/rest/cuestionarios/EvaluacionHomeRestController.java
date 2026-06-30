package es.jccm.edu.pdc.adapter.in.rest.cuestionarios;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.pdc.application.domain.evaluacion.projection.CentrosParaInspectoresProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionCompletoProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionVisionGlobalProjection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.AmbitoAsociadoDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.CentrosParaInspectoresDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.EvaluacionCompletoDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.EvaluacionHomeDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.EvaluacionVisionGlobalDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.LineaActuacionDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.LineaSeguimientoDto;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.ObjetivoEspecificoEvaDto;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.LineaActuacion;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionCompleto;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionVisionGlobal;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.ObjetivoEspecificoEva;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaSeguimiento;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.IEvaluacionHomeService;
import es.jccm.edu.pdc.application.services.cuestionarios.EvaluacionHomeService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.ObjetivoEspecificoEvaDto;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionCompleto;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.ObjetivoEspecificoEva;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.IEvaluacionHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/pdc" + "/evaluacion")
@Tag(name = "Servicio Evaluacion", description = "Servicio con las operaciones sobre Evaluacion")
@CrossOrigin
public class EvaluacionHomeRestController {

	@Autowired
	IEvaluacionHomeService evaluacionService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve la tabla Evaluacion completa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEvaluacionHomeAll")
	public ResponseEntity<List<EvaluacionHomeDto>> getEvaluacionHomeAll() {
		try {
			List<EvaluacionHome> evaluacion = evaluacionService.getEvaluacionHomeAll();
			
			List<EvaluacionHomeDto> evaluacionDto = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionHomeDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<EvaluacionHomeDto>>(evaluacionDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve la tabla de porcentajes de evaluacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPorcentajes")
	public ResponseEntity<List<EvaluacionHomeDto>> getPorcentajes() {
		List<EvaluacionHomeDto> evaluacionDto;
		try {
			List<EvaluacionHome> evaluacion = evaluacionService.getPorcentajes();
			evaluacionDto = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionHomeDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<EvaluacionHomeDto>>(evaluacionDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve la tabla de estados de evaluacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadoPorcentajes")
	public ResponseEntity<List<EvaluacionHomeDto>> getEstadoPorcentajes() {
		List<EvaluacionHomeDto> evaluacionDto;
		try {
			List<EvaluacionHome> evaluacion = evaluacionService.getEstadoPorcentajes();
			evaluacionDto = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionHomeDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<EvaluacionHomeDto>>(evaluacionDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve media de porcentajes de evaluacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMediaPorcentajes")
	public ResponseEntity<Double> getMediaPorcentajes() {
		Double evaluacion = evaluacionService.getMediaPorcentajes();
		Double evaluacionOut = evaluacion;
		try {
			if (evaluacionOut == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(evaluacionOut, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve las tres últimas fechas de actualizacion", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechasActualizacion")
	public ResponseEntity<List<EvaluacionHomeDto>> getFechasActualizacion() {
		List<EvaluacionHomeDto> evaluacionDto;
		try {
			List<EvaluacionHome> evaluacion = evaluacionService.getFechasActualizacion();
			evaluacionDto = evaluacion.stream().map(x -> modelMapper.map(x, EvaluacionHomeDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<EvaluacionHomeDto>>(evaluacionDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve el nombre del Ambito asociado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAmbitoAsociado")
	public ResponseEntity<List<AmbitoAsociadoDto>> getAmbitoAsociado() {
		List<AmbitoAsociadoDto> ambitoAsociadoDto;
		try {
			List<AmbitoAsociado> evaluacion = evaluacionService.getAmbitoAsociado();
			ambitoAsociadoDto = evaluacion.stream().map(x -> modelMapper.map(x, AmbitoAsociadoDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<AmbitoAsociadoDto>>(ambitoAsociadoDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve el Objetivo Especifico", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getObjetivoEspecificoEva")
	public ResponseEntity<List<ObjetivoEspecificoEvaDto>> getObjetivoEspecificoEva() {
		List<ObjetivoEspecificoEvaDto> objetivoEspecificoDto;
		try {
			List<ObjetivoEspecificoEva> evaluacion = evaluacionService.getObjetivoEspecificoEva();
			objetivoEspecificoDto = evaluacion.stream().map(x -> modelMapper.map(x, ObjetivoEspecificoEvaDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ObjetivoEspecificoEvaDto>>(objetivoEspecificoDto, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve todos los campos de Evaluación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEvaluacionCompleto")
	public ResponseEntity<List<EvaluacionCompletoProjection>> getEvaluacionCompleto(@RequestParam("x_centro") Long x_centro, @RequestParam("anno") Integer anno) {

		try {
			List<EvaluacionCompletoProjection> evaluacion = evaluacionService.getEvaluacionCompleto(x_centro, anno);
			return new ResponseEntity<List<EvaluacionCompletoProjection>>(evaluacion, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get Parsem Evaluacion.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Evaluacion", description = "Este metodo devuelve todos los campos de Evaluación", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEvaluacionVisionGlobal")
	public ResponseEntity<List<EvaluacionVisionGlobalProjection>> getEvaluacionVisionGlobal(@RequestParam("codCentro") Long codCentro, @RequestParam("anno") Integer anno) {

		try {
			List<EvaluacionVisionGlobalProjection> evaluacion = evaluacionService.getEvaluacionVisionGlobal(codCentro,anno);
			return new ResponseEntity<List<EvaluacionVisionGlobalProjection>>(evaluacion, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set líneas de seguimiento", description = "Este metodo setea las líneas de Seguimiento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/setLineasDeSeguimiento")
	public ResponseEntity setLineasDeSeguimiento(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody List<LineaSeguimientoDto>  lineasSeguimiento) {
		
		System.out.println(lineasSeguimiento);
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<LineaSeguimiento> lineasSeguimientoIn = lineasSeguimiento.stream()
				.map(x -> modelMapper.map(x, LineaSeguimiento.class)).collect(Collectors.toList());
		try {
			for (LineaSeguimiento lineaSeguimiento : lineasSeguimientoIn) {
				System.out.println("Guardando linea de actuacion: " + lineaSeguimiento);
				  evaluacionService.setLineasDeSeguimiento(
						  lineaSeguimiento.getFechaInicioEjecucion(),
						  lineaSeguimiento.getFechaFinEjecucion(),
						  lineaSeguimiento.getPorcentaje(),
						  lineaSeguimiento.getTareas(),
						  lineaSeguimiento.getValoracion(),
						  lineaSeguimiento.getDificultades_acciones(),
						  lineaSeguimiento.getComentarios(),
						  lineaSeguimiento.getIdLinAct(),
						  datosUsuario.getXUsuarioComunica()
						  );	
				  }	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set líneas de seguimiento", description = "Este metodo setea las líneas de Seguimiento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/editLineasDeSeguimiento")
	public ResponseEntity editLineasDeSeguimiento(@RequestHeader(Constants.AUTHORIZATION) String jwt,@RequestParam("idSeguiLinAct") Long idSeguiLinAct, 
									 @RequestBody List<LineaSeguimientoDto>  lineasSeguimiento) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			List<LineaSeguimiento> lineasSeguimientoIn = lineasSeguimiento.stream()
					.map(x -> modelMapper.map(x, LineaSeguimiento.class)).collect(Collectors.toList());

			System.out.println("Editando lineas de seguimiento");
			evaluacionService.editLineasDeSeguimiento(idSeguiLinAct, lineasSeguimientoIn, datosUsuario.getXUsuarioComunica());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Inspectores.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Inspectores", description = "Este metodo devuelve la información necesaria para que un inspector pueda"
			+ "visualizar todos los informes", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/inspector")
	public ResponseEntity<List<CentrosParaInspectoresDto>> getCentrosInspector(@RequestParam("idEmpleado") Long idEmpleado,
			@RequestParam("tipoInforme") Long tipoInforme, @RequestParam("tipoEmpleado") Long tipoEmpleado) {

		try {
			List<CentrosParaInspectoresProjection> out = evaluacionService.getCentrosInspector(idEmpleado,tipoInforme,tipoEmpleado);
			List<CentrosParaInspectoresDto> outDto = out.stream().map(x -> modelMapper.map(x, CentrosParaInspectoresDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<List<CentrosParaInspectoresDto>>(outDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	

}