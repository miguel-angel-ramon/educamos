package es.jccm.edu.gestionauditorias.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto;
import es.jccm.edu.gestionauditorias.adapter.in.rest.model.LoggAccUsuSisAutDto;
import es.jccm.edu.gestionauditorias.application.ports.in.ILoggerAccUsuariosService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/gestionauditorias/logger")
@Tag(name = "Servicio Auditoria Log Acceso Usuarios Sistema Autenticación", description = "Servicio para realizar la auditoria y log de acceso de los usuarios por sistema de autenticación")
@CrossOrigin
public class LoggerAccUsuariosController {
	
	@Autowired
	ILoggerAccUsuariosService loggerAccUsuarios;
	
    //@PreAuthorize("hasAnyRole('P','PRO')")
    @Operation(summary = "Guardar log acceso usuario por sistema autenticación", description = "Este método guarda el log de acceso de un usuario por sistema autenticación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/loggeraccususisaut")
    public ResponseEntity<PonderacionDto> savelogaccususisaut(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                          @RequestBody LoggAccUsuSisAutDto logaccususisaut) {

        
        loggerAccUsuarios.savelogaccususisaut(logaccususisaut);
        	  
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
