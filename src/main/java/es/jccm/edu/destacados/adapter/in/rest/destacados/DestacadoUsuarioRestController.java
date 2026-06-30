package es.jccm.edu.destacados.adapter.in.rest.destacados;

import es.jccm.edu.destacados.adapter.in.rest.BasePath;
import es.jccm.edu.destacados.adapter.in.rest.destacados.model.DestacadoUsuarioDto;
import es.jccm.edu.destacados.application.domain.destacados.Destacado;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuario;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuarioDTO;
import es.jccm.edu.destacados.application.ports.in.destacados.IDestacadoUsuarioService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//TODO TAG
@RestController
@RequestMapping(BasePath.DESTACADOS_BASE_PATH)
@CrossOrigin
public class DestacadoUsuarioRestController {

    private final IDestacadoUsuarioService destacadoUsuarioService;

    private final IDatosUsuarioJwtService datosUsuarioJwtService;

    public DestacadoUsuarioRestController(IDestacadoUsuarioService destacadoUsuarioService, IDatosUsuarioJwtService datosUsuarioJwtService) {
        this.destacadoUsuarioService = destacadoUsuarioService;
        this.datosUsuarioJwtService = datosUsuarioJwtService;
    }

    @PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "DestacadosUsuariosByNif", description = "Este metodo devuelve los destacadoUsuario de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDestacadosUsuariosByNif")
    public ResponseEntity<List<DestacadoUsuarioDto>> getDestacadosUsuariosByNif(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        return new ResponseEntity<>(destacadoUsuarioService.getDestacadosUsuariosByNif(datosUsuario.getNif()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "DestacadosUsuariosActivosByNif", description = "Este metodo devuelve los destacadoUsuario activos de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDestacadosUsuariosActivosByNif")
    public ResponseEntity<List<DestacadoUsuarioDto>> getDestacadosUsuariosActivosByNif(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        return new ResponseEntity<>(destacadoUsuarioService.getDestacadosUsuariosActivosByNif(datosUsuario.getNif()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "UpdateOrSaveDestacadosUsuarios", description = "Este metodo recibe una lista de destacadosUsuarios y los persiste/modifica en bbdd",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/updateOrSaveDestacadosUsuarios")
    public HttpStatus updateOrSaveDestacadosUsuarios(@RequestBody List<DestacadoUsuarioDTO> destacadosUsuarios) {
    	List<DestacadoUsuario> destacadosSave = new ArrayList<>(); 
    	 for (DestacadoUsuarioDTO dto : destacadosUsuarios) {
    	        DestacadoUsuario destacadoUsuario = new DestacadoUsuario();
    	        destacadoUsuario.setId(dto.getId());
    	        destacadoUsuario.setOidUsuario(dto.getOidUsuario());
    	        destacadoUsuario.setNumeroOrden(dto.getNumeroOrden());
    	        destacadoUsuario.setActivo(dto.isActivo());

    	        // Supongamos que tienes un servicio para buscar el objeto Destacado
    	        Destacado destacado = destacadoUsuarioService.findById(dto.getDestacado());
    	        destacadoUsuario.setDestacado(destacado);

    	        // Guarda el objeto en la base de datos
    	        destacadosSave.add(destacadoUsuario);
    	    }
        destacadoUsuarioService.updateOrSaveDestacadosUsuarios(destacadosSave);
        return HttpStatus.OK;
    }

}
