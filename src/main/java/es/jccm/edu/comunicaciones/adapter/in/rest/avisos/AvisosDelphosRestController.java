package es.jccm.edu.comunicaciones.adapter.in.rest.avisos;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.comunicaciones.application.domain.avisos.Aviso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model.AvisoDelphosDto;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.IAvisosDelphosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Avisos Delphos Escritorio", description = "Servicio para recuperar el módulo de avisos de delphos del escritorio")
//@CrossOrigin
public class AvisosDelphosRestController {
	
	@Autowired
	private IAvisosDelphosService avisosDelphosService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Trae todos los avisos recibidos de un usuario en función de su perfil y nivel educativo.
	 *
	 * @param String idUsuario
	 * @return List<AvisoDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recuperar avisos recibidos", description = "Este metodo devuelve un objeto List con todos los avisos de un usuario en función de su perfil y nivel educativo", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/avisosActivos")
    public ResponseEntity<List<AvisoDelphosDto>> getAvisos(@RequestParam("perfil") String perfil, @RequestParam("nivEducativo") Integer nivEducativo) {
		
		List<AvisoDelphosDto> avisosOut = avisosDelphosService.getAvisosDelphos(perfil, nivEducativo).stream().map(aviso -> modelMapper.map(aviso, AvisoDelphosDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(avisosOut, HttpStatus.OK);
    }

	/**
	 * Recupera todos los avisos en función del centro.
	 *
	 * @param Long idCentro
	 * @return List<AvisoDelphosDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recuperar avisos por centro", description = "Este metodo devuelve un objeto List con todos los avisos correspondientes a un mismo centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/avisosByCentro/{idCentro}")
	public ResponseEntity<List<AvisoDelphosDto>> getAvisosByCentro(@PathVariable("idCentro") Long idCentro) {

		List<Aviso> avisosList = avisosDelphosService.getAvisosByCentro(idCentro);

		List<AvisoDelphosDto> avisosOut = avisosList.stream().map(x -> modelMapper.map(x, AvisoDelphosDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(avisosOut, HttpStatus.OK);
	}
	
}