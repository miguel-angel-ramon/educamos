package es.jccm.edu.documentosGC.adapter.in.rest.centrodoc;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.centrodoc.model.CentroDocInspeccionDto;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocInspeccion;
import es.jccm.edu.documentosGC.application.ports.in.centrodoc.ICentroDocInspeccionService;
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
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicio de Centros", description = "Servicio con las operaciones sobre los centros relacionados al inspector")
public class CentroDocInspeccionRestController {
	
	@Autowired
	private ICentroDocInspeccionService inspeccionService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
	/**
	 * Provincias de castilla la mancha.
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener centros", description = "Este metodo devuelve el listado de centros de un municipio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosMunicipio/{c_provincia}/{c_municipio}")
		public ResponseEntity<List<CentroDocInspeccionDto>> getListadoMunicipiosProvincia(@PathVariable("c_provincia") Long c_provincia,@PathVariable("c_municipio") Long c_municipio) {
		
		List<CentroDocInspeccion> centros = inspeccionService.getCentroMunicipio(c_provincia, c_municipio);
		centros.sort((CentroDocInspeccion o1, CentroDocInspeccion o2) -> o1.getDescripcion().compareTo(o2.getDescripcion()));

		List<CentroDocInspeccionDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, CentroDocInspeccionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDocInspeccionDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Provincias de castilla la mancha.
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener centros zona", description = "Este metodo devuelve el listado de centros de un municipio para el perfil inspector zona", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosMunicipioZona/{c_provincia}/{c_municipio}/{idPerfil}/{idUsuario}")
		public ResponseEntity<List<CentroDocInspeccionDto>> getListadoCentrosMunicipioZona(@PathVariable("c_provincia") Long c_provincia,
																						   @PathVariable("c_municipio") Long c_municipio,
																						   @PathVariable("idPerfil") Long idPerfil,
																						   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<CentroDocInspeccion> centros = inspeccionService.getListadoCentrosMunicipioZona(c_provincia, c_municipio,idPerfil,datosUsuario.getXUsuarioDelphos());
		centros.sort((CentroDocInspeccion o1, CentroDocInspeccion o2) -> o1.getDescripcion().compareTo(o2.getDescripcion()));

		List<CentroDocInspeccionDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, CentroDocInspeccionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDocInspeccionDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener centros inspector centro", description = "Este metodo devuelve el listado de centros de un municipio para el perfil inspector centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosMunicipioInspectorCentro/{c_provincia}/{c_municipio}/{xEmpleado}/{fTomaPos}")
		public ResponseEntity<List<CentroDocInspeccionDto>> getListadoCentrosMunicipioInspectorCentro(@PathVariable("c_provincia") Long c_provincia,
																									  @PathVariable("c_municipio") Long c_municipio,
																									  @RequestHeader(Constants.AUTHORIZATION) String jwt,
																									  @PathVariable("fTomaPos") String fTomaPos) {
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<CentroDocInspeccion> centros = inspeccionService.getListadoCentrosMunicipioInspectorCentro(c_provincia,c_municipio,datosUsuario.getIdEmpleadoDelphos(),fTomaPos);
		//centros.sort((CentroDocInspeccion o1, CentroDocInspeccion o2) -> o1.getDescripcion().compareTo(o2.getDescripcion()));

		List<CentroDocInspeccionDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, CentroDocInspeccionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDocInspeccionDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener centros inspector provincial", description = "Este metodo devuelve el listado de centros de un municipio para el perfil inspector provincial", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosMunicipioInspectorProvincial/{c_provincia}/{c_municipio}/{idPerfil}/{idUsuario}")
		public ResponseEntity<List<CentroDocInspeccionDto>> getListadoCentrosMunicipioInspectorProvincial(@PathVariable("c_provincia") Long c_provincia,
																										  @PathVariable("c_municipio") Long c_municipio,
																										  @PathVariable("idPerfil") Long idPerfil,
																										  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
																								
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<CentroDocInspeccion> centros = inspeccionService.getListadoCentrosMunicipioInspectorProvincial(c_provincia, c_municipio,idPerfil,datosUsuario.getXUsuarioDelphos());
		centros.sort((CentroDocInspeccion o1, CentroDocInspeccion o2) -> o1.getDescripcion().compareTo(o2.getDescripcion()));

		List<CentroDocInspeccionDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, CentroDocInspeccionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDocInspeccionDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	/**
	 * Provincias de castilla la mancha.
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener centros consejeria", description = "Este metodo devuelve el listado de centros de un municipio para el perfil inspector consejeria", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosConsejeria/{c_provincia}/{c_municipio}")
		public ResponseEntity<List<CentroDocInspeccionDto>> getListadoCentrosConsejeria(@PathVariable("c_provincia") Long c_provincia,
																						@PathVariable("c_municipio") Long c_municipio) {
		
		List<CentroDocInspeccion> centros = inspeccionService.getListadoCentrosConsejeria(c_provincia, c_municipio);
		centros.sort((CentroDocInspeccion o1, CentroDocInspeccion o2) -> o1.getDescripcion().compareTo(o2.getDescripcion()));

		List<CentroDocInspeccionDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, CentroDocInspeccionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CentroDocInspeccionDto>>(centrosDto, HttpStatus.OK);
		
		
	}
	
	
}