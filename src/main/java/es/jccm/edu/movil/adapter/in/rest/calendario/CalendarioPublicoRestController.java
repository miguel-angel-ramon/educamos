package es.jccm.edu.movil.adapter.in.rest.calendario;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.movil.adapter.in.rest.calendario.model.CalendarioPublicoDto;
import es.jccm.edu.movil.application.domain.CalendarioPublico;
import es.jccm.edu.movil.application.domain.projection.CalendarioPublicoProjection;
import es.jccm.edu.movil.application.ports.in.ICalendarioPublicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@CrossOrigin
@RequestMapping("${educamos.public.rest.base-path:/publico}" + "/calendario")
@Tag(name = "Servicio Público de Calendario", description = "Servicio con las operaciones sobre Calendario")
public class CalendarioPublicoRestController {
	
	@Autowired
	private ICalendarioPublicoService calendarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
		/**
		 * Lista datos.
		 *
		 * @return the response entity
		 */

		@Operation(summary = "Lista de Festivos", description = "Este metodo devuelve una lista con todos los festivos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getAllFestivos")
		public ResponseEntity<List<CalendarioPublicoProjection>> getAllConvenios() {
			List<CalendarioPublicoProjection> festivos =  calendarioService.getFestivos();
			return new ResponseEntity<>(festivos, HttpStatus.OK);
		}
		
		
		/**
		 * Devuelve las familias profesionales de una empresa
		 *
		 * @return the response entity
		 */

		@Operation(summary = "Festivos by identificador", description = "Este metodo devuelve los festivos según ubucación",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getFestivos/{xCentro}")
		public ResponseEntity<List<CalendarioPublicoProjection>> getFestivoByIdentificador(@PathVariable("xCentro") Long xCentro){

			List<CalendarioPublicoProjection> festivos = calendarioService.getFestivosByIdentificador(xCentro);
			

			
			return new ResponseEntity<>(festivos, HttpStatus.OK);
		}

}
