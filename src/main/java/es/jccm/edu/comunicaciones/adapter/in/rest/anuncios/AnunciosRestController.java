package es.jccm.edu.comunicaciones.adapter.in.rest.anuncios;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.comunicaciones.adapter.in.rest.anuncios.model.AnuncioListDto;
import es.jccm.edu.comunicaciones.application.domain.anuncios.AnuncioList;
import es.jccm.edu.comunicaciones.application.ports.in.anuncios.IAnunciosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Anuncios Escritorio", description = "Servicio para recuperar el módulo de anuncios del escritorio")
//@CrossOrigin
public class AnunciosRestController {

    @Autowired
    private IAnunciosService anunciosService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Devuelve un listado de anuncios para un centro
     *
     * @param Long idCentro
     * @return List<AnuncioListDto>
     */
    ////@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Devuelve una lista de anuncios", description = "Este metodo devuelve una lista con todos los anuncios para un centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/anuncios/{idCentro}")
    public ResponseEntity<List<AnuncioListDto>> getAllAnuncios(@PathVariable("idCentro") Long idCentro){

        List<AnuncioList> anunciosList = anunciosService.getAnuncios(idCentro);

        List<AnuncioListDto> anunciosOut = anunciosList.stream().map(x -> modelMapper.map(x, AnuncioListDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(anunciosOut, HttpStatus.OK);

    }

}