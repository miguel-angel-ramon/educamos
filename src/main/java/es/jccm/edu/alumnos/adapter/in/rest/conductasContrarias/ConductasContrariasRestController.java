package es.jccm.edu.alumnos.adapter.in.rest.conductasContrarias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.AlumnadoDTO;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.CondContrariaDTO;
import es.jccm.edu.alumnos.application.ports.in.conductasContrarias.IConductaContrariaService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Conductas Contrarias", description="Servicio para recuperar las conductas contrarias a la convivencia por parte del alumnado")
//@CrossOrigin
public class ConductasContrariasRestController {
	
	@Autowired 
	private IConductaContrariaService conductaContrariaService;
	
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation (summary="Recuperar datos relativos al alumnado incidente en conductas contrarias ",
	responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/alumnadoIncidente")
	public ResponseEntity <CollectionModel<AlumnadoDTO>>getAlumnadoIncidente(	@RequestParam(defaultValue="0") 
			Long unidad,@RequestParam Long ofm,@RequestParam Integer anno 	){
		return ResponseEntity.ok().body(CollectionModel.of(conductaContrariaService.getAlumnadoIncidente(unidad, ofm, anno)));
		
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation (summary="Recuperar datos relativos a conductas contrarias en una unidad",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
			@GetMapping("/conductascontrarias")
	public ResponseEntity <CollectionModel<CondContrariaDTO>>getCondContUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt,
				@RequestParam(defaultValue="0") Long unidad,@RequestParam Long ofm,@RequestParam Integer anno 	){
	
		return ResponseEntity.ok().body(CollectionModel.of(conductaContrariaService.getCondContUnidad(unidad, ofm, anno)));
				
			}

}
