package es.jccm.edu.alumnos.adapter.in.rest.historicoDomiciliosAlumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.HistoricoDomicilioAlumnoDTO;
import es.jccm.edu.alumnos.application.ports.in.historicoDomiciliosAlumno.IHistoricoDomiciliosAlumno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Histórico de Domicilios ", description="Servicio para recuperar el histórico de direcciones de un alumno")
//@CrossOrigin
public class HistoricoDomiciliosAlumnoRestController {

	@Autowired 
	private IHistoricoDomiciliosAlumno historicoDomiciliosService;
	
	//@PreAuthorize("hasAnyRole('P','PRO')")
		@Operation (summary="Recuperar todas las direcciones de un alumno ",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/historicodomicilios/{idAlumno}")
		public ResponseEntity <CollectionModel<HistoricoDomicilioAlumnoDTO>> getDomiciliosAlumnosById( @PathVariable Long idAlumno){
			
			return ResponseEntity.ok().body(CollectionModel.of(historicoDomiciliosService.getDomiciliosAlumnosById(idAlumno)));
		}
		

}
