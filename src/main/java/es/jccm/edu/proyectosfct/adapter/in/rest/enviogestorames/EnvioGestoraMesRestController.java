package es.jccm.edu.proyectosfct.adapter.in.rest.enviogestorames;

import es.jccm.edu.proyectosfct.adapter.in.rest.enviogestorames.model.EnviosGestoraMesDto;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosGestoraMes;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestorames.IEnvioGestoraMesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Envio Gestora Mes", description = "Servicio que recupera un listado de las veces que se ha consultado los datos de los alumnos en la Seguridad Social")
public class EnvioGestoraMesRestController {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEnvioGestoraMesService envioGestoraMesService;

    //@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
    @Operation(summary = "", description = "",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getEnviosGestoraMes")
    public ResponseEntity<List<EnviosGestoraMesDto>> getEnviosGestoraMes(@RequestParam("idCentro") Long idCentro,
                                                                         @RequestParam("anno") Integer anno,
                                                                         @RequestParam("nuMes") Integer nuMes) {

        try {

           List<EnviosGestoraMes> listadoEnviosGestoraMes = envioGestoraMesService.getEnviosGestoraMes(idCentro, anno, nuMes);

           List<EnviosGestoraMesDto> enviosGestoraMesDtos = listadoEnviosGestoraMes.stream().map(x -> modelMapper.map(x, EnviosGestoraMesDto.class)).collect(Collectors.toList());

            return new ResponseEntity<>(enviosGestoraMesDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
