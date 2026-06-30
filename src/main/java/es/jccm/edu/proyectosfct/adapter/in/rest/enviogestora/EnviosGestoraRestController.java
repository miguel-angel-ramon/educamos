package es.jccm.edu.proyectosfct.adapter.in.rest.enviogestora;

import es.jccm.edu.proyectosfct.adapter.in.rest.enviogestora.model.EnviosGestoraDto;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosGestora;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestora.IEnviosGestoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio para obtener el listado de veces que se ha consultado la información de la gestora")
public class EnviosGestoraRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEnviosGestoraService enviosGestoraService;

    /**
     * Combos cursos anexo anexo.
     *
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
    @Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getEnviosGestora/{idCentro}/{anno}")
    public ResponseEntity<List<EnviosGestoraDto>> getListadoHistorico(@PathVariable("idCentro") Long idCentro,
                                                                      @PathVariable("anno") Integer anno){

        List<EnviosGestora> listadoEnviosGestora =  enviosGestoraService.getEnviosGestora(idCentro, anno);

        List<EnviosGestoraDto> listadoEnviosGestoraDto = listadoEnviosGestora.stream().map(x -> modelMapper.map(x, EnviosGestoraDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(listadoEnviosGestoraDto, HttpStatus.OK);
    }

}
