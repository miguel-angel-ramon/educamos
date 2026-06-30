package es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessaging;

import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionDetalles;
import es.jccm.edu.comunicacionesMovil.application.ports.in.INotificacionesMovilService;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@CrossOrigin
@RequestMapping("${educamos.public.rest.base-path:/publico}" + "/notificacionesMovil")
@Tag(name = "Servicio envio Notificaciones Firebase", description = "Envio para notificaciones Firebase a dispositivos moviles")
public class NotificacionesMovilPublicoRestController {

    @Autowired
    private INotificacionesMovilService notificacionesService;

    @Autowired
    private ModelMapper modelMapper;
    /*@Autowired
    private FirebaseMessaging firebaseMessaging;*/

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;
    // Read

    /**
     * Lista datos.
     *
     * @return the response entity
     */

    @Operation(summary = "Envío Notificaciones", description = "Este metodo devuelve ejecuta el envio de notificaciones",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/notificaciones")
    public ResponseEntity<List<NotificacionDetalles>> getNotificaciones() {
        try {
        	 List<NotificacionDetalles>  notificacionMovil = notificacionesService.getNotificaciones();
            return new ResponseEntity<>(notificacionMovil, HttpStatus.OK);
        } catch (Error e) {
            System.err.println("Failed to get messages: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

}


