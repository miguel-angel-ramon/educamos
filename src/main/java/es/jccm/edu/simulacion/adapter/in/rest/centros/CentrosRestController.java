package es.jccm.edu.simulacion.adapter.in.rest.centros;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.simulacion.application.domain.centros.entities.DepartamentoCentro;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.annotations.Totp;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.CentroDto;
import es.jccm.edu.simulacion.application.domain.centros.entities.Centros;
import es.jccm.edu.simulacion.application.ports.in.centros.ICentrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/simulacion")
@Tag(name = "Servicio Centros Escritorio", description = "Servicio de centros para el módulo de simulación de usuarios del escritorio")
@CrossOrigin
public class CentrosRestController {

	@Autowired
	private ICentrosService centrosService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${enable.simulation}")
	private Boolean simulationEnabled;

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los datos necesarios del
	 * código del centro introducido.
	 *
	 * @param Long codCentro
	 * @return CentroDto
	 */
	@Operation(summary = "Recupera los datos de un centro", description = "Este metodo devuelve un objeto con los datos de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centro")
	public ResponseEntity<CentroDto> getCentro(@RequestParam("codCentro") Long codCentro) {

//		if (!simulationEnabled) {
//			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
//		}

		try {
			CentroDto centroOut = modelMapper.map(centrosService.getCentro(codCentro), CentroDto.class);

			return new ResponseEntity<>(centroOut, HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
		
	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los datos necesarios del
	 * id del centro introducido.
	 *
	 * @param Long idCentro
	 * @return CentroDto
	 */
	@Operation(summary = "Recupera los datos de un centro por su id", description = "Este metodo devuelve un objeto con los datos de un centro por su id", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centro/id")
	public ResponseEntity<CentroDto> getCentroById(@RequestParam("idCentro") Long idCentro) {

		CentroDto centroOut = modelMapper.map(centrosService.getCentroById(idCentro), CentroDto.class);

		return new ResponseEntity<>(centroOut, HttpStatus.OK);
	}
	
	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Listado Centros", description = "Este metodo devuelve un listado con todos los centros donde el usuario puede rellenar un formulario",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centrosCuestionario")
	public ResponseEntity<List<CentroDto>> getListadoCentros(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codCuestionario") String codCuestionario) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<Centros> centros = centrosService.getListadoCentros(datosUsuario.getXUsuarioComunica(), codCuestionario); 
		
		List<CentroDto> centrosOut = centros.stream().map(x -> modelMapper.map(x, CentroDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDto>>(centrosOut, HttpStatus.OK);
	}

	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Listado Centros del Inspector", description = "Este metodo devuelve un listado con todos los centros del Inspector",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centrosInspector")
	public ResponseEntity<List<Centros>> centrosInspector(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<Centros> centros = centrosService.getCentrosInspector(datosUsuario.getIdEmpleadoDelphos());

		return new ResponseEntity<List<Centros>>(centros, HttpStatus.OK);
	}

	/**
	 * Get Parsem Alumno Programa.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Listado Centros del Inspector", description = "Este metodo devuelve un listado con todos los centros del Inspector",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/departamentosCentro")
	public ResponseEntity<List<DepartamentoCentro>> centroDepartamentos(@RequestParam("anyo") Long anyo,
																		@RequestParam("idCentro") Long idCentro) {
		List<DepartamentoCentro> departamentos = centrosService.getDepartamentosCentro(idCentro, anyo);

		return new ResponseEntity<>(departamentos, HttpStatus.OK);
	}

}