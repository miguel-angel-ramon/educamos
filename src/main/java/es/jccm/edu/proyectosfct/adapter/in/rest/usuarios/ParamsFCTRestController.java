package es.jccm.edu.proyectosfct.adapter.in.rest.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.usuarios.model.ParamsFCTDto;
import es.jccm.edu.proyectosfct.application.domain.usuarios.entities.ParamsFCT;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
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
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Usuarios", description = "Servicio con las operaciones sobre Usuarios")
public class ParamsFCTRestController {
	
	@Autowired
	private IParamsFCTService datosUsuarioService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Operation(summary = "Parametros FCT", description = "Este metodo los datos de parámetros de usuario", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/paramsFCT/{vista}/{idCentro}")
		public ResponseEntity<List<ParamsFCTDto>> datosUsuario(@PathVariable("vista") String vista ,
															   @PathVariable("idCentro") Long idCentro ,
																  @RequestHeader(Constants.AUTHORIZATION) String jwt){
			
			DatosUsuarioJwt datosUsuarioJWT = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			List<ParamsFCT> params = datosUsuarioService.paramsFCT(vista,
					                                               idCentro,
														    	   datosUsuarioJWT.getXUsuarioDelphos(),
														    	   datosUsuarioJWT.getXUsuarioComunica(),
														    	   datosUsuarioJWT.getOid());
			
			List<ParamsFCTDto> paramsDTO = params.stream()
					.map(entity -> modelMapper.map(entity, ParamsFCTDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(paramsDTO, HttpStatus.OK);
			
		}
}
