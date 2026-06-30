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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.horarios.adapter.in.rest.BasePath;
import es.jccm.edu.horarios.adapter.in.rest.calendario.model.CalendarioDto;
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
@RequestMapping(BasePath.HorariosBasePath)
@Tag(name = "Servicio Calendario", description = "Servicio con las operaciones sobre Calendario")
public class CalendarioRestController {
	
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
		@GetMapping("proyectosfct/getAllFestivos")
		public ResponseEntity<List<Calendario>> getAllConvenios(){
			
			List<Calendario> festivos =  calendarioService.getFestivos();
			
			List<Calendario> festivosDto = festivos.stream().map(x -> modelMapper.map(x, Calendario.class)).collect(Collectors.toList());
					
			return new ResponseEntity<>(festivosDto, HttpStatus.OK);
		}
		
		
		/**
		 * Devuelve las familias profesionales de una empresa
		 *
		 * @return the response entity
		 */
		@PreAuthorize("hasAnyRole('P','PRO','C')")
		@Operation(summary = "Festivos by identificador", description = "Este metodo devuelve los festivos según ubucación",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getFestivos/{xCentro}")
		public ResponseEntity<List<CalendarioDto>> getFestivoByIdentificador(@PathVariable("xCentro") Long xCentro){

			List<Calendario> festivos = calendarioService.getFestivosByIdentificador(xCentro);
			
			List<CalendarioDto> festivosOut = festivos.stream().map(x -> modelMapper.map(x, CalendarioDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(festivosOut, HttpStatus.OK);
		}
		
		/**
		 * Devuelve los días festivos de un año académico de un centro
		 *
		 * @return the response entity
		 */
		//@PreAuthorize("hasAnyRole('P','PRO','C')")
		@Operation(summary = "Festivos by año académico y centro", description = "Este metodo devuelve los festivos según año académico y centro",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getDiasFestivos")
		public ResponseEntity<List<CalendarioDto>> getDiasFestivos(@RequestParam("anyo") Long anyo, @RequestParam("idCentro") Long idCentro){

			List<Calendario> festivos = calendarioService.getDiasFestivos(anyo, idCentro);
			
			List<CalendarioDto> festivosOut = festivos.stream().map(x -> modelMapper.map(x, CalendarioDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(festivosOut, HttpStatus.OK);
		}

}
