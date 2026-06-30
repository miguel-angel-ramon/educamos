package es.jccm.edu.proyectosfct.adapter.in.rest.modalidades;

import java.util.List;
import java.util.stream.Collectors;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.jccm.edu.proyectosfct.adapter.in.rest.modalidades.model.ModalidadDto;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.ports.in.modalidades.IModalidadesService;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Modalidades FCT", description = "Servicio con las operaciones sobre Modalidades FCT")
public class ModalidadRestController {
	
	@Autowired
	IModalidadesService modalidadesService;
	
	@Autowired
	private ModelMapper modelMapper;

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Modalidades del centro", description = "Este metodo devuelve una lista con todas las modalidades para un centro y anno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({"/getAllModalidadesFamiliaCentro/{idCentro}/{cAnno}/{idFamilia}/{idTipo}"})
	public ResponseEntity<List<ModalidadDto>> getAllModalidadesFamiliaCentro(@PathVariable("idCentro") Long idCentro, 
																		     @PathVariable("cAnno") int cAnno,
																			 @PathVariable("idFamilia") Long idFamilia,
																			 @PathVariable("idTipo") Long idTipo) {
		
		List<Modalidad> modalidades = modalidadesService.getAllModalidadesFamiliaCentro(idCentro, cAnno, idFamilia, idTipo);
		
		List<ModalidadDto> modalidadesDto = modalidades.stream()
				.map(entity -> modelMapper.map(entity, ModalidadDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<ModalidadDto>>(modalidadesDto, HttpStatus.OK);	
		
	}
	

}
