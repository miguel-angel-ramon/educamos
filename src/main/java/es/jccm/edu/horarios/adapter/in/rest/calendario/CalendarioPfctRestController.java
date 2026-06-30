package es.jccm.edu.horarios.adapter.in.rest.calendario;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.horarios.adapter.in.rest.BasePath;
import es.jccm.edu.horarios.application.domain.calendario.Calendario;
import es.jccm.edu.horarios.application.ports.in.calendario.ICalendarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.FixmeHorariosCalendarioPfcBasePath)
@Tag(name = "Servicio Calendario Pfct", description = "Servicio con las operaciones sobre Calendario proyectosfct")
public class CalendarioPfctRestController {
	
	@Autowired
	private ICalendarioService calendarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
		/**
		 * Lista datos.
		 *
		 * @return the response entity
		 */
		@PreAuthorize("hasAnyRole('P','PRO','C')")
		@Operation(summary = "Lista de Festivos", description = "Este metodo devuelve una lista con todos los festivos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getAllFestivos")
		public ResponseEntity<List<Calendario>> getAllConvenios(){
			
			List<Calendario> festivos =  calendarioService.getFestivos();
			
			List<Calendario> festivosDto = festivos.stream().map(x -> modelMapper.map(x, Calendario.class)).collect(Collectors.toList());
					
			return new ResponseEntity<>(festivosDto, HttpStatus.OK);
		}

}
