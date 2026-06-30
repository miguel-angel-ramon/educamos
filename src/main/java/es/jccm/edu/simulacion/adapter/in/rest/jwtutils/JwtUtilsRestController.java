package es.jccm.edu.simulacion.adapter.in.rest.jwtutils;

import es.jccm.edu.shared.annotations.Totp;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.BasePath;
import es.jccm.edu.simulacion.application.ports.in.token.IJwtUtilsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(BasePath.FixmeSimulacionJwtUtilBasePath)
public class JwtUtilsRestController {

    private final IJwtUtilsService jwtUtilsService;

    private final IDatosUsuarioJwtService datosUsuarioJwtService;

    public JwtUtilsRestController(IJwtUtilsService jwtUtilsService, IDatosUsuarioJwtService datosUsuarioJwtService) {
        this.jwtUtilsService = jwtUtilsService;
        this.datosUsuarioJwtService = datosUsuarioJwtService;
    }

    @Operation(summary = "generateTokenPlataforma", description = "Este metodo devuelve generateTokenPlataforma", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/generateTokenPlataforma")
    public ResponseEntity<String> generateTokenPlataforma(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
        String jwtToken = jwtUtilsService.generateToken(jwt);
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
    }
   
    @GetMapping("/generateTokenSimulacionAlternativo")
    public ResponseEntity<String> generateTokenSimulacionAlternativo(@RequestHeader(Constants.AUTHORIZATION) String jwt,  @RequestParam("oidUsuario") String oidUsuario) {
        if (!datosUsuarioJwtService.hasSimulacionRole(jwt)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(jwtUtilsService.generateToken(jwt, oidUsuario));
    }

    @GetMapping("/generateTokenSimulacion")
    public ResponseEntity<String> generateTokenSimulacion(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("oidUsuario") String oidUsuario) {
        if (!datosUsuarioJwtService.hasSimulacionRole(jwt)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(jwtUtilsService.generateTokenSimulacion(jwt, oidUsuario));
    }
}