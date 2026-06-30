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
import es.jccm.edu.documentosGC.adapter.in.rest.datosterritoriales.model.MunicipioDto;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import es.jccm.edu.documentosGC.application.ports.in.datosterritoriales.IMunicipioDocService;
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
@Tag(name = "Servicio de municipios", description = "Servicio con las operaciones sobre los municipios")
public class MunicipiosDocRestController {
	
	@Autowired
	private IMunicipioDocService municipioService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
	/**
	 * Municipios de una provincia
	 *
	 * @param idProvincia Id de la provincia
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener municipios", description = "Este metodo devuelve el listado de municipios de una provincia", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMunicipioProvincia/{idProvincia}")
	public ResponseEntity<List<MunicipioDto>> getListadoMunicipiosProvincia(@PathVariable("idProvincia") Long idProvincia) {
		
		List<MunicipioDoc> municipios = municipioService.findMunicipioByProvincia(idProvincia);
		municipios.sort((MunicipioDoc o1, MunicipioDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<MunicipioDto> municipiosDto = municipios.stream().map(x -> modelMapper.map(x, MunicipioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<MunicipioDto>>(municipiosDto, HttpStatus.OK);
	}
	
	/**
	 * Municipios de una provincia para inspector de zona
	 *
	 * @param idProvincia Id de la provincia
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener municipios", description = "Este metodo devuelve el listado de municipios de una provincia para un inspector de zona", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMunicipioProvinciaZona/{idProvincia}/{idPerfil}/{idUsuario}")
	public ResponseEntity<List<MunicipioDto>> getMunicipioProvinciaZona(@PathVariable("idProvincia") Long idProvincia,
																		@PathVariable("idPerfil") Long idPerfil,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<MunicipioDoc> municipios = municipioService.getMunicipioProvinciaZona(idProvincia,idPerfil,datosUsuario.getXUsuarioDelphos());
		municipios.sort((MunicipioDoc o1, MunicipioDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<MunicipioDto> municipiosDto = municipios.stream().map(x -> modelMapper.map(x, MunicipioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<MunicipioDto>>(municipiosDto, HttpStatus.OK);
	}
	
	/**
	 * Municipios de una provincia para inspector de centro
	 *
	 * @param idProvincia Id de la provincia
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener municipios", description = "Este metodo devuelve el listado de municipios de una provincia para un inspector de centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMunicipioProvinciaCentro/{idProvincia}/{xEmpleado}/{fTomapos}")
	public ResponseEntity<List<MunicipioDto>> getMunicipioProvinciaCentro(@PathVariable("idProvincia") Long idProvincia,
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt,
																		  @PathVariable("fTomapos") String fTomapos) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<MunicipioDoc> municipios = municipioService.getMunicipioProvinciaCentro(idProvincia,datosUsuario.getIdEmpleadoDelphos(),fTomapos);
		//municipios.sort((MunicipioDoc o1, MunicipioDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<MunicipioDto> municipiosDto = municipios.stream().map(x -> modelMapper.map(x, MunicipioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<MunicipioDto>>(municipiosDto, HttpStatus.OK);
	}
	
	/**
	 * Municipios de una provincia para inspector de zona
	 *
	 * @param idProvincia Id de la provincia
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener municipios", description = "Este metodo devuelve el listado de municipios de una provincia para un inspector provincial", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMunicipioInspectorProvincial/{idProvincia}/{idPerfil}/{idUsuario}")
	public ResponseEntity<List<MunicipioDto>> getMunicipioInspectorProvincial( @PathVariable("idProvincia") Long idProvincia,
																			   @PathVariable("idPerfil") Long idPerfil,
																			   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<MunicipioDoc> municipios = municipioService.getMunicipioInspectorProvincial(idProvincia,idPerfil,datosUsuario.getXUsuarioDelphos());
		municipios.sort((MunicipioDoc o1, MunicipioDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<MunicipioDto> municipiosDto = municipios.stream().map(x -> modelMapper.map(x, MunicipioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<MunicipioDto>>(municipiosDto, HttpStatus.OK);
	}
	
	/**
	 * Municipios de una provincia
	 *
	 * @param idProvincia Id de la provincia
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Obtener municipios", description = "Este metodo devuelve el listado de municipios de una provincia", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMunicipioConsejeria/{idProvincia}")
	public ResponseEntity<List<MunicipioDto>> getMunicipioConsejeria(@PathVariable("idProvincia") Long idProvincia) {
		
		List<MunicipioDoc> municipios = municipioService.getMunicipioConsejeria(idProvincia);
		municipios.sort((MunicipioDoc o1, MunicipioDoc o2) -> o1.getDescripcionLarga().compareTo(o2.getDescripcionLarga()));

		List<MunicipioDto> municipiosDto = municipios.stream().map(x -> modelMapper.map(x, MunicipioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<MunicipioDto>>(municipiosDto, HttpStatus.OK);
	}
	
	
	
}