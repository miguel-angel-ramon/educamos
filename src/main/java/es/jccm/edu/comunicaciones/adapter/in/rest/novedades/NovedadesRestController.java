package es.jccm.edu.comunicaciones.adapter.in.rest.novedades;

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

import es.jccm.edu.comunicaciones.adapter.in.rest.BasePath;
import es.jccm.edu.comunicaciones.adapter.in.rest.novedades.model.NovedadDto;
import es.jccm.edu.comunicaciones.application.domain.novedades.Novedad;
import es.jccm.edu.comunicaciones.application.ports.in.novedades.INovedadesService;
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
@RequestMapping(BasePath.ComunicacionesBasePath)
@Tag(name = "Servicio Novedades Escritorio", description = "Servicio para recuperar el módulo de novedades del escritorio")
@CrossOrigin
public class NovedadesRestController {

    @Autowired
    private INovedadesService novedadesService;
    
    @Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Devuelve un listado de novidades para un usuario
     *
     * @param String idUsuario
     * @return List<NovedadDto>
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Devuelve una lista de novedades", description = "Este método devuelve una lista con todas las novedades para un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/novedades")
    public ResponseEntity<List<NovedadDto>> getAllNovedades(@RequestHeader(Constants.AUTHORIZATION) String jwt){

    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
        List<Novedad> novedadesList = novedadesService.getNovedades(datosUsuario.getUsuarioDelphos());

        List<NovedadDto> novedadesOut = novedadesList.stream().map(x -> modelMapper.map(x, NovedadDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(novedadesOut, HttpStatus.OK);

    }

    /**
     * Devuelve una novedad
     *
     * @param Long idNovedad
     * @return NovedadDto
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Devuelve una novedad", description = "Este metodo devuelve los datos de una novedad",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/novedades/{idNovedad}")
    public ResponseEntity<NovedadDto> getNovedadById(@PathVariable("idNovedad") Long idNovedad) {

        NovedadDto novedadOut = modelMapper.map(novedadesService.getNovedad(idNovedad), NovedadDto.class);

        return new ResponseEntity<>(novedadOut, HttpStatus.OK);

    }

}
