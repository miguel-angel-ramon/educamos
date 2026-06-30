package es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesInvidualesDTO;
import es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model.NotificacionesMovilDTO;
import es.jccm.edu.comunicacionesMovil.application.domain.NotificacionDetalles;
import es.jccm.edu.comunicacionesMovil.application.ports.in.INotificacionesMovilService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("${spring.data.rest.base-path:/api}" + "/notificacionesMovil")
@Tag(name = "Servicio envio Notificaciones Firebase", description = "Envio para notificaciones Firebase a dispositivos moviles")
public class NotificacionesMovilRestController {

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
    @PostMapping("/clients")
    public ResponseEntity<String> postToClient(@RequestBody NotificacionesMovilDTO notificacionesDTO) {
        try {
            String response = notificacionesService.enviarNotificacionesMasivas(notificacionesDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send messages: " + e.getMessage());
            return new ResponseEntity<>("Error al enviar la notificación: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Registro de Token móvil", description = "Este metodo registra en base de datos un token de un dispositivo móvil",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/registerToken")
    public ResponseEntity<String> registerToken(@RequestHeader(Constants.AUTHORIZATION) String jwt , @RequestBody String  tokenMovil) {
        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        try {
            String response = notificacionesService.registrarToken(tokenMovil, datosUsuario.getXUsuarioComunica());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Envío Notificaciones", description = "Este metodo devuelve ejecuta el envio de notificaciones",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/clientIndividual")
    public ResponseEntity<String> postToClientIndividual(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody NotificacionesInvidualesDTO notificacionesIndividualesDTO ) {
        try {
        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
            String response = notificacionesService.enviarNotificacionesIndividual(datosUsuario.getXUsuarioComunica() ,notificacionesIndividualesDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FirebaseMessagingException e) {
            return new ResponseEntity<>("Error al enviar la notificación: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Lista de notificaciones.
     *
     * @return the response entity
     */

    @Operation(summary = "Lista Notificaciones", description = "Este metodo devuelve la lista de notificaciones",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/notificaciones")
    public ResponseEntity<List<NotificacionDetalles>> getNotificaciones(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam(defaultValue = "0") int pagina,
    		@RequestParam(defaultValue = "10") int size) {
    	 DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	 Pageable p = PageRequest.of(pagina, size);
    	 try {
        	 Page<NotificacionDetalles>  n = notificacionesService.getNotificaciones(datosUsuario.getXUsuarioComunica(),p);
        	 List<NotificacionDetalles> notificacionMovil = n.getContent();
        	 
        	 return new ResponseEntity<>(notificacionMovil, HttpStatus.OK);
        } catch (Error e) {
            System.err.println("Failed to get messages: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}


