package es.jccm.edu.movil.adapter.in.rest.novedades;

import es.jccm.edu.movil.adapter.in.rest.novedades.model.NovedadesPublicoDTO;
import es.jccm.edu.movil.application.ports.in.INovedadesPublicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${educamos.public.rest.base-path:/publico}" + "/novedades")
@Tag(name = "Servicio Novedades", description = "Servicio Novedades")
@CrossOrigin
public class NovedadesPublicoRestController {

    @Autowired
    private INovedadesPublicoService novedadesPublicoService;


    @Operation(summary = "Recuperar novedades", description = "Este metodo devuelve un objeto con las novedades",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getAllNovedades")
    public ResponseEntity<List<NovedadesPublicoDTO>> obtenerNovedades() {
        try {
            List<NovedadesPublicoDTO> novedades = novedadesPublicoService.obtenerNovedades();
            return new ResponseEntity<>(novedades, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(summary = "Recuperar novedades", description = "Este metodo devuelve un objeto con las novedades",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getAllNovedadesFiltradas")
    public ResponseEntity<List<NovedadesPublicoDTO>> obtenerNovedadesFiltradas() {
        try {
            List<NovedadesPublicoDTO> novedades = novedadesPublicoService.obtenerNovedadesFiltradas();
            return new ResponseEntity<>(novedades, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}