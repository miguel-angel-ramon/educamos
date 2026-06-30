package es.jccm.edu.aulasVirtuales.adapter.in.rest.aulasVirtuales;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.aulasVirtuales.adapter.in.rest.BasePath;
import es.jccm.edu.aulasVirtuales.adapter.in.rest.aulasVirtuales.model.AulaVirtualListDto;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.AulaVirtualList;
import es.jccm.edu.aulasVirtuales.application.ports.in.aulasVirtuales.IAulaVirtualService;
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
@RequestMapping(BasePath.AulasVirtualesBasePath)
@Tag(name = "Servicio comunicación con Aulas Virtuales", description = "Servicio para comunicar las distintas aplicaciones con los segmentos de Moodle")
@CrossOrigin
public class AulaVirtualRestController {

    @Autowired
    private IAulaVirtualService aulaVirtualService;
    
    @Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Devuelve las url de las aulas virtuales de las clases de un profesor
     *
     * @param anno anno
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('P','PRO')")
    @Operation(summary = "Aulas virtuales según profesor", description = "Este metodo devuelve las aulas virtuales correspondientes a las clases de un profesor",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("getAulasByProfesor")
    public ResponseEntity<List<AulaVirtualListDto>> getNumeroConvenio(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno)
    {

    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
        List<AulaVirtualList> aulasVirtualesList = aulaVirtualService.getAulasVirtualesByProfesor(datosUsuario.getIdEmpleadoComunica(), anno);

        List<AulaVirtualListDto> aulasVirtualesOut = aulasVirtualesList.stream().map(x -> modelMapper.map(x, AulaVirtualListDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(aulasVirtualesOut, HttpStatus.OK);

    }
    
    /**
     * Devuelve las url de las aulas virtuales de las clases de un profesor
     *
     * @param anno anno
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('P','PRO')")
    @Operation(summary = "Aulas virtuales según profesor", description = "Este metodo devuelve las aulas virtuales correspondientes a las clases de un prfeosr",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("getAulasVirtuales")
    public ResponseEntity<List<AulaVirtualListDto>> getAulasVirtuales(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno)
    {

    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
        List<AulaVirtualList> aulasVirtualesList = aulaVirtualService.getAulasVirtuales(datosUsuario.getIdEmpleadoComunica(), datosUsuario.getOid(), anno);

        List<AulaVirtualListDto> aulasVirtualesOut = aulasVirtualesList.stream().map(x -> modelMapper.map(x, AulaVirtualListDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(aulasVirtualesOut, HttpStatus.OK);

    }

}
