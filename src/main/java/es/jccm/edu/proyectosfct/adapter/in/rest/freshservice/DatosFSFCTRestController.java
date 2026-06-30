package es.jccm.edu.proyectosfct.adapter.in.rest.freshservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.freshservice.model.DatosFSFCTDto;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosFreshServiceJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
public class DatosFSFCTRestController {
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	private static final String SIN_DATOS = "unknown";  
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT','P')")
	@Operation(summary = "Obtener datos", description = "Este metodo devuelve datos de un usuario logado que hace uso del formulario de sporte técnico", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
							@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
							@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
							@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/datosFS/{xPerfil}")	
	public ResponseEntity<DatosFSFCTDto> datosFS(@PathVariable("xPerfil") Long xPerfil,
											  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<DatosFreshServiceJwt> datos = datosUsuarioJwtService.getDatosFreshService(datosUsuario.getOid()); 
		
		DatosFSFCTDto datosFS = new DatosFSFCTDto();
		datosFS.setNif(SIN_DATOS);
		datosFS.setTelefono(SIN_DATOS);
		datosFS.setCorreo(SIN_DATOS);		
		
		datos.forEach(
	            (dato) -> {
	                if ((xPerfil + "").equals(dato.getPerfil() + "")) {
	                	datosFS.setNif(dato.getNif());
	    				datosFS.setTelefono(dato.getTelefono());
	    				datosFS.setCorreo(dato.getCorreo());
	    				return;
	                } 
	            }
	    );
		
		
		return new ResponseEntity<>(datosFS, HttpStatus.OK);
	}

}
