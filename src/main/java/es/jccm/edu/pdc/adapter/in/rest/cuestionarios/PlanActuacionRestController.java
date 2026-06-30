package es.jccm.edu.pdc.adapter.in.rest.cuestionarios;

import es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model.FaltaAsistenciaAlumnoDto;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAsistenciaAlumno;
import es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model.*;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.PlanActuacionRepository;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Informe;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.LineaActuacion;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.ObjetivoEspecifico;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.AmbitoCompleto;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.DetalleAmbito;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.ObjetivoEspecificoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionImpactoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionSeguimientoPDC;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.IPlanActuacionService;
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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/pdc" + "/planActuacion")
@Tag(name = "Servicio plan de actuación", description = "Servicio con las operaciones sobre plan de actuación")
@CrossOrigin
@Slf4j
public class PlanActuacionRestController {

	@Autowired
	IPlanActuacionService planActuacionService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PlanActuacionRepository planActuacionRepository;
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Ambitos asistente", description = "Este metodo devuelve una lista de ambitos con el valor calculado tras el cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAmbitosAsistente")
	public ResponseEntity<List<InformeDto>> getAmbitosPlanActuacion(@RequestParam("idSector") Long idSector,
																	@RequestParam("anno") Long anno, @RequestParam("codCentro") Long codCentro) {
		List<Informe> informe = planActuacionService.getAmbitosPlanActuacion(idSector, anno, codCentro);
		List<InformeDto> informeOut = informe.stream().map(x -> modelMapper.map(x, InformeDto.class)).collect(Collectors.toList());
		try {
			if (informeOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<InformeDto>>(informeOut, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Ambitos asistente", description = "Este metodo devuelve una lista de ambitos con el valor calculado tras el cuestionario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAmbitosCompletos/centro")
	public ResponseEntity<List<AmbitoCompletoDto>> getAmbitosCompletosCentro(@RequestParam("idSector") Long idSector,
		@RequestParam("anno") Long anno, @RequestParam("xCentro") Long xCentro, @RequestParam("x_cuepub") Long x_cuepub) {

		List<AmbitoCompleto> ambitos = planActuacionService.getAmbitosCompletosCentro(idSector, anno, xCentro,x_cuepub);
		List<AmbitoCompletoDto> ambitosOut = ambitos.stream().map(x -> modelMapper.map(x, AmbitoCompletoDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(ambitosOut, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Detalles Ámbitos", description = "Este metodo devuelve un objeto con los detalles del ámbito pasado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDetalleAmbitoCentro/{idCompetencia}")
	public ResponseEntity<DetalleAmbitoDTO> getDetalleAmbito(@PathVariable("idCompetencia") Long idCompetencia, @RequestParam("idCentro") Long idCentro
			, @RequestParam("anio") Long anio) {

		DetalleAmbito detalleAmbito = planActuacionService.getDetalleAmbitoCentro(idCompetencia, idCentro,anio);
		DetalleAmbitoDTO detalleAmbitoOut = modelMapper.map(detalleAmbito, DetalleAmbitoDTO.class);

		try {
			if (detalleAmbitoOut.getSugerencia().isEmpty() && detalleAmbitoOut.getObjetivoGeneral().isEmpty() && detalleAmbitoOut.getPuntoPartida().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<DetalleAmbitoDTO>(detalleAmbitoOut, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get objetivo especifico", description = "Este metodo obtiene el objetivo especifico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getObjetivosEspecificos")
	public ResponseEntity<List<ObjetivoEspecificoDto>> getObjetivosEspecificos(@RequestParam("codCentro") Long codCentro,
																			   @RequestParam("idObjetivo") Long idObjetivo, @RequestParam("idAnno")Long idAnno) {

		try {
			List<ObjetivoEspecifico> objetivosEspecificos = planActuacionService.getObjetivosEspecificos(codCentro, idObjetivo,idAnno);
			List<ObjetivoEspecificoDto> objetivosEspecificosOut = objetivosEspecificos.stream().map(x -> modelMapper.map(x, ObjetivoEspecificoDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(objetivosEspecificosOut, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set objetivo especifico", description = "Este metodo setea el objetivo especifico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/setObjetivoEspecifico")
	public ResponseEntity setObjetivoEspecifico(@RequestParam("codCentro") Long codCentro, @RequestParam("anno") Long anno,
												@RequestParam("idObjetivo") Long idObjetivo, @RequestBody String descripcionObjetivo) {

	    System.out.println("Body: "+ descripcionObjetivo);
		try {
			planActuacionService.setObjetivoEspecifico(codCentro, anno, idObjetivo, descripcionObjetivo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set objetivo especifico", description = "Este metodo setea el objetivo especifico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/editObjetivoEspecifico")
	public ResponseEntity editObjetivoEspecifico(@RequestParam("idObjEsp") Long idObjEsp, @RequestBody String descripcion) {

		try {
			planActuacionService.editObjetivosEspecificos(idObjEsp, descripcion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set líneas de actuación", description = "Este metodo setea las líneas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/setLineasActuacion")
	public ResponseEntity setLineasDeActuacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,@RequestBody List<LineaActuacionDto>  lineasActuacion) {
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = dt1.format(new Date());


		System.out.println(lineasActuacion);
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<LineaActuacion> lineasActuacionIn = lineasActuacion.stream()
				.map(x -> modelMapper.map(x, LineaActuacion.class)).collect(Collectors.toList());
		try {
			
			for (LineaActuacion lineaActuacion : lineasActuacionIn) {
				System.out.println("Guardando linea de actuacion: " + lineaActuacion);
				  planActuacionService.setLineasDeActuacion(
						  lineaActuacion.getIdObjEsp(), 
						  lineaActuacion.getTitulo(),
						  lineaActuacion.getDescripcion(),
						  lineaActuacion.getFechaInicio(),
						  lineaActuacion.getFechaFin(),
						  lineaActuacion.getResponsable(),
						  lineaActuacion.getLogro(),
						  lineaActuacion.getInstrumentos(),
						  lineaActuacion.getEstado(),
						  datosUsuario.getXUsuarioComunica()
						  );

			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem delete líneas de actuación", description = "Este metodo desactiva las líneas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/deleteObjetivoEspecifico")
	public ResponseEntity deleteObjetivoEspecifico(@RequestBody ObjetivoEspecifico objetivoEspecifico) {

		try {
			planActuacionService.deleteObjetivoEspecifico(objetivoEspecifico);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem delete líneas de actuación", description = "Este metodo desactiva las líneas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/deleteLineaActuacion")
	public ResponseEntity deleteLineaActuacion(@RequestParam("idLinAct") Long idLinAct ) {

		try {
			planActuacionService.deleteLineaActuacion(idLinAct);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem set líneas de actuación", description = "Este metodo setea las líneas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/editLineasActuacion")
	public ResponseEntity editLineasActuacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,@RequestParam("idObjEsp") Long idObjEsp, 
									 @RequestBody List<LineaActuacionDto>  lineasActuacion) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			List<LineaActuacion> lineasActuacionIn = lineasActuacion.stream()
					.map(x -> modelMapper.map(x, LineaActuacion.class)).collect(Collectors.toList());

			System.out.println("Editando lineas de actuación");
			planActuacionService.editLineasActuacion(idObjEsp, lineasActuacionIn, datosUsuario.getXUsuarioComunica());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Valores Centro", description = "Este metodo devuelve una lista con la media de los valores de todos los centros.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getValoresCentro")
	public ResponseEntity<List<InformeDto>> getValoresCentro(@RequestParam("idSector") Long idSector,
																@RequestParam("anno") Long anno) {
		List<Informe> informe = planActuacionService.getValoresCentro(idSector, anno);
		List<InformeDto> informeOut = informe.stream().map(x -> modelMapper.map(x, InformeDto.class)).collect(Collectors.toList());
		try {
			if (informeOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<InformeDto>>(informeOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Valor Ambito Cinco", description = "Este metodo devuelve el valor del ambito cinco.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getValorAmbitoCinco")
	public ResponseEntity<Double> getValorAmbitoCinco(@RequestParam("codCentro") Long codCentro,
																@RequestParam("Anno") Long Anno) {
		Double informe = planActuacionService.getValorAmbitoCinco(codCentro, Anno);
		Double informeOut = informe;
		try {
			if (informeOut == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Double>(informeOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Valor Ambito Cinco Global", description = "Este metodo devuelve la media del valor del ambito cinco de todos los centros.", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getValorAmbitoCincoGlobal")
	public ResponseEntity<Double> getValorAmbitoCincoGlobal(@RequestParam("Anno") Long Anno) {
		Double informe = planActuacionService.getValorAmbitoCincoGlobal(Anno);
		Double informeOut = informe;
		try {
			if (informeOut == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Double>(informeOut, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "visualizar plan", description = "Este metodo devuelve un boolean para ver el informe", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/visualizarPlan")
	public ResponseEntity<Boolean> visualizarPlan(@RequestParam("codCentro") Long codCentro) {
		Boolean result = planActuacionService.visualizaPlan(codCentro);		
		try {
			if (result == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get objetivo especifico", description = "Este metodo setea el objetivo especifico",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/objetivos/especificos")
	public ResponseEntity<List<ObjetivoEspecificoPDCDto>> getObjetivosEspecificosPDC(@RequestParam("anio") String anio,@RequestParam("x_competencia") String x_competencia, @RequestParam("x_centro") String x_centro) {

		try {
			List<ObjetivoEspecificoPDC> objetivosEspecificos = planActuacionService.getObjetivosEspecificosPorAmbito(anio, x_competencia,x_centro);
			List<ObjetivoEspecificoPDCDto> objetivosEspecificosOut = objetivosEspecificos.stream().map(x -> modelMapper.map(x, ObjetivoEspecificoPDCDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(objetivosEspecificosOut, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get histórico lineas de actuación", description = "Este metodo obtiene el histórico de las lineas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/historico/lineas-actuacion")
	public ResponseEntity<List<LineaActuacionHistoricoDto>> getHistoricoLineasActuacion(@RequestParam("idActuacion") Integer idActuacion) {

		try {
			List<LineaActuacionHistoricoDto> lineasDeAct = planActuacionService.getHistoricoLineasActuacion(idActuacion).stream().map(x -> modelMapper.map(x, LineaActuacionHistoricoDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(lineasDeAct, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem put mejora y observacion de un ambito", description = "Este metodo añade una mejora y observación a un ambito",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/ambito/observaciones/insert")
	public ResponseEntity<Object> insertarPropuestasYMejorasPorAmbito(@RequestBody ObservacionesYMejorasAmbitoDto o) {
		try {
			planActuacionService.insertarPropuestasYMejorasPorAmbito(o.getCentro(),o.getCuepub(),o.getCompetencia(),o.getAnno(),o.getObservacion(),o.getPropuesta());
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem delete mejora y observacion de un ambito", description = "Este metodo elimina una mejora y observación de un ambito",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/ambito/observaciones/delete")
	public ResponseEntity<Object> eliminarPropuestasYMejorasPorAmbito( @RequestParam("x_competencia") Integer x_competencia,@RequestParam("x_centro") Integer x_centro
			,@RequestParam("x_cuepub") Integer x_cuepub,@RequestParam("anno") Integer anno) {
		try {
			planActuacionService.eliminarPropuestasYMejorasPorAmbito(x_competencia,x_centro,x_cuepub,anno);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem put mejora y observacion a nivel global", description = "Este metodo añade una mejora y observación a nivel global",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/global/observaciones/insert")
	public ResponseEntity<Object> insertarPropuestasYMejorasGlobal(@RequestBody ObservacionesYMejorasGlobalDto o) {
		try {
			planActuacionService.insertarPropuestasYMejorasGlobal(o.getCentro(), o.getCuepub() , o.getAnno(), o.getMejoragestion(),o.getMejoraaprendizaje(),o.getOtrastareas());
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem delete mejora y observacion a nivel global", description = "Este metodo elimina una mejora y observación a nivel global",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/global/observaciones/delete")
	public ResponseEntity<Object> eliminarPropuestasYMejorasGlobal(@RequestParam("x_centro") Integer x_centro
			,@RequestParam("x_cuepub") Integer x_cuepub,@RequestParam("anno") Integer anno) {
		try {
			planActuacionService.eliminarPropuestasYMejorasGlobal(x_centro,x_cuepub,anno);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get mejora y observacion a nivel global", description = "Este metodo obtiene una mejora y observación a nivel global",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/global/observaciones/get")
	public ResponseEntity<ObservacionesYMejorasGlobalDto> obtenerPropuestasYMejorasGlobal(@RequestParam("x_centro") Integer x_centro
			,@RequestParam("x_cuepub") Integer x_cuepub,@RequestParam("anno") Integer anno) {
		try {
			InformacionImpactoPDC a  = planActuacionService.obtenerPropuestasYMejorasGlobal(x_centro,x_cuepub,anno);
			ObservacionesYMejorasGlobalDto ob = modelMapper.map(a, ObservacionesYMejorasGlobalDto.class);
			return new ResponseEntity<ObservacionesYMejorasGlobalDto>(ob, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get mejora y observacion de un ambito", description = "Este metodo obtiene una mejora y observación de un ambito",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/ambito/observaciones/get")
	public ResponseEntity<ObservacionesYMejorasAmbitoDto> obtenerPropuestasYMejorasPorAmbito( @RequestParam("x_competencia") Integer x_competencia,@RequestParam("x_centro") Integer x_centro
			,@RequestParam("x_cuepub") Integer x_cuepub,@RequestParam("anno") Integer anno) {
		try {
			InformacionSeguimientoPDC a = planActuacionService.obtenerPropuestasYMejorasPorAmbito(x_competencia,x_centro,x_cuepub,anno);
			ObservacionesYMejorasAmbitoDto ob = modelMapper.map(a, ObservacionesYMejorasAmbitoDto.class);
			return new ResponseEntity<ObservacionesYMejorasAmbitoDto>(ob, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem Get Ambitos con objetivos específicos", description = "Este metodo devuelve una lista de ambitos con objetivos específicos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/ambitos-con-objesp")
	public ResponseEntity<List<InformeDto>> getAmbitosConObjetivosEspecificos(
																	@RequestParam("anno") String anno, @RequestParam("x_centro") String x_centro) {
		List<Informe> informe = planActuacionService.getAmbitosConObjetivosEspecificos(anno, x_centro);
		List<InformeDto> informeOut = informe.stream().map(x -> modelMapper.map(x, InformeDto.class)).collect(Collectors.toList());
		try {
			if (informeOut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<InformeDto>>(informeOut, HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasAnyRole('C','PRO', 'P', 'INZ','I','INC','ICO','PDC')")
	@Operation(summary = "Parsem get años con objetivos especificos y lineas de actuación", description = "Este metodo obtiene los años con objetivos especificos y lineas de actuación",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/historico/objetivos-esp-con-lineas-actuacion")
	public ResponseEntity<List<Integer>> getHistoricoAniosConObjEspYLineasActuacion(@RequestParam("x_centro") String x_centro) {

		try {
			List<Integer> annios = planActuacionService.getHistoricoAniosConObjEspYLineasActuacion(x_centro);
			return new ResponseEntity<>(annios, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}