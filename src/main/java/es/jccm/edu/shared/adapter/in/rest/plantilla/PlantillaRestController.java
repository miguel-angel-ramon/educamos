package es.jccm.edu.shared.adapter.in.rest.plantilla;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.adapter.in.rest.plantilla.model.PlantillaDto;
import es.jccm.edu.shared.application.domain.plantilla.entities.Plantilla;
import es.jccm.edu.shared.application.ports.in.IPlantillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Plantilla", description = "Servicio con las operaciones sobre plantilla de formulario")
public class PlantillaRestController {

	@Autowired
	private IPlantillaService plantillaService;
	
	@Autowired
	private ModelMapper modelMapper;


	/**
	 * Creación de los Datos de Alumno Convenio Programa.
	 *
	 * @param programaFctDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	@Operation(summary = "Creación de los datos de una plantilla para formulario", description = "Este metodo crea los datos de una plantilla para formulario", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
		@PostMapping("/createPlantilla")
		public ResponseEntity<PlantillaDto> createPlantilla(
				@Parameter(description = "Datos del Convenio", required = true) @RequestBody PlantillaDto plantillaDto) {
			
			try {
				
				Plantilla plantilla = modelMapper.map(plantillaDto, Plantilla.class);
				
				plantillaService.createPlantilla(plantilla);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
	
		
}
