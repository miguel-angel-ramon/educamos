package es.jccm.edu.horarios.adapter.in.rest.materias;

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

import es.jccm.edu.horarios.adapter.in.rest.BasePath;
import es.jccm.edu.horarios.adapter.in.rest.materias.model.MateriaListDto;
import es.jccm.edu.horarios.application.domain.materias.MateriaList;
import es.jccm.edu.horarios.application.ports.in.materias.IMateriasService;
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
@RequestMapping(BasePath.HorariosBasePath)
@Tag(name = "Servicio Materias Escritorio", description = "Servicio para recuperar las materias del módulo de horarios del escritorio")
@CrossOrigin
public class MateriasRestController {
	
	@Autowired
	private IMateriasService materiaService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
    
    /**
	 * Conectamos con la BBDD de Delphos y sacamos las materias de un profesor.
	 *
	 * @param String idUsuario
	 * @param Integer anno
	 * @return List<MateriaListDto>
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
    @Operation(summary = "Recuperar materias de un profesor", description = "Este metodo devuelve un objeto List con las materias de un profesor", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/materias")
    public ResponseEntity<List<MateriaListDto>> getMaterias(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno) {
    	
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
    	List<MateriaList> materiasList = materiaService.getMaterias(datosUsuario.getUsuarioDelphos(), anno);
		
		List<MateriaListDto> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriaListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }
	
}