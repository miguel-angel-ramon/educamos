package es.jccm.edu.documentosGC.adapter.in.rest.datosterritoriales;

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
import es.jccm.edu.documentosGC.adapter.in.rest.datosterritoriales.model.ProvinciaDto;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.ProvinciaDoc;
import es.jccm.edu.documentosGC.application.ports.in.datosterritoriales.IProvinciaDocService;
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
@Tag(name = "Servicio de Provincias", description = "Servicio con las operaciones sobre las provincias")
public class ProvinciasDocRestController {
	
	@Autowired
	private IProvinciaDocService provinciaService;
	
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
	@Operation(summary = "Obtener provincias", description = "Este metodo devuelve el listado de provincias de castilla la mancha", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvincias")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvincias() {
		
		List<ProvinciaDoc> provincias = provinciaService.getListadoProvincias();
		provincias.sort((ProvinciaDoc o1, ProvinciaDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<ProvinciaDto> provinciasDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(provinciasDto, HttpStatus.OK);
	}
	
	/**
	 * Provincias de castilla la mancha.
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener provincias perfil Inspeccion Zona", description = "Este metodo devuelve el listado de provincias para el inspector de zona", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvinciasZona/{idPerfil}/{idUsuario}")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvinciasZona(@PathVariable("idPerfil") Long idPerfil, 
																	   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ProvinciaDoc> provincias = provinciaService.getListadoProvinciasZona(idPerfil,datosUsuario.getXUsuarioDelphos());
		provincias.sort((ProvinciaDoc o1, ProvinciaDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<ProvinciaDto> provinciasDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(provinciasDto, HttpStatus.OK);
	}
	
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener provincias perfil Inspeccion Centro", description = "Este metodo devuelve el listado de provincias para el inspector de centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvinciasCentro/{xEmpleado}/{fTomapos}")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvinciasCentro(@RequestHeader(Constants.AUTHORIZATION) String jwt,
																		 @PathVariable("fTomapos") String fTomapos) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ProvinciaDoc> provincias = provinciaService.getListadoProvinciasCentro(datosUsuario.getIdEmpleadoDelphos(),fTomapos);
		//provincias.sort((ProvinciaDoc o1, ProvinciaDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<ProvinciaDto> provinciasDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(provinciasDto, HttpStatus.OK);
	}
	
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener provincias perfil Inspeccion Provincial", description = "Este metodo devuelve el listado de provincias para el inspector provincial", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvinciasProvincial/{idPerfil}/{idUsuario}/{idCentroProvincia}")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvinciasProvincial(@PathVariable("idPerfil") Long idPerfil, 
																			 @RequestHeader(Constants.AUTHORIZATION) String jwt,
																			 @PathVariable("idCentroProvincia") Long idCentroProvincia) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ProvinciaDoc> provincias = provinciaService.getListadoProvinciasProvincial(idPerfil,datosUsuario.getXUsuarioDelphos(),idCentroProvincia);
		provincias.sort((ProvinciaDoc o1, ProvinciaDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<ProvinciaDto> provinciasDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(provinciasDto, HttpStatus.OK);
	}
	
}