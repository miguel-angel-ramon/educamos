package es.jccm.edu.ausencias.adapter.in.rest.guardias;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.ausencias.adapter.in.rest.BasePath;
import es.jccm.edu.ausencias.adapter.in.rest.guardias.model.DatosProfesoresGuardiasDto;
import es.jccm.edu.ausencias.adapter.in.rest.guardias.model.GuardiasProfesoresDto;
import es.jccm.edu.ausencias.application.domain.guardias.DatosProfesoresGuardias;
import es.jccm.edu.ausencias.application.domain.guardias.GuardiasProfesores;
import es.jccm.edu.ausencias.application.ports.in.guardias.IGuardiasProfesores;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.FixmeAusenciasGuardiasBasePath)
@Tag(name = "Servicio de Guardias de Profesores", description = "Este servicio está dedicado a rescatar los datos realcionados con las guardias de profesores para el dashboard de Eq. Directivo")
@CrossOrigin
public class GuardiasProfesoresRestController {

	@Autowired
	private IGuardiasProfesores guardiasProfesoresService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Conectamos con la BBDD de Delphos y sacamos los tramos horarios con
	 * profesores de guardias.
	 *
	 * @param Long    codCentro
	 * @param Integer anno
	 * @return List<GuardiasProfesoresDto>
	 */
	@PreAuthorize("hasAnyRole('C','RES_CEN')")
	@Operation(summary = "Recupera el listado de tramos con profesores de guardia", description = "Este método consulata la BBDD de Delphos para recuperar el listado de tramos con profesores de guardia", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getGuardiasProfesores")
	public ResponseEntity<List<GuardiasProfesoresDto>> getAusencioProfesoras(@RequestParam("codCentro") Long codCentro,
			@RequestParam("anno") Integer anno) {	

		List<GuardiasProfesores> guardiasProfesoresList = guardiasProfesoresService.getGuardiasProfesores(codCentro,
				anno);

		List<GuardiasProfesoresDto> guardiasProfesoresOut = guardiasProfesoresList.stream()
				.map(x -> modelMapper.map(x, GuardiasProfesoresDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(guardiasProfesoresOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Delphos y rescatamos los datos de los profesores de
	 * guardia de un tramo horario.
	 *
	 * @param Long    codCentro
	 * @param Long    idTramoCentro
	 * @param Integer anno
	 * @param Integer diaSemana
	 * @return List<DatosProfesoresGuardiasDto>
	 */
	@PreAuthorize("hasAnyRole('C','RES_CEN')")
	@Operation(summary = "Recupera el listado de tramos con profesores de guardia", description = "Este método consulata la BBDD de Delphos para recuperar los datos de los profesores de guardia de un tramo horario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosProfesoresGuardias")
	public ResponseEntity<List<DatosProfesoresGuardiasDto>> getDatosProfesoresGuardias(
			@RequestParam("codCentro") Long codCentro, @RequestParam("idTramo") Long idTramo, @RequestParam("diaSemana") Integer diaSemana) {

		List<DatosProfesoresGuardias> datosProfesoresGuardiasList = guardiasProfesoresService
				.getDatosProfesoresGuardias(codCentro, idTramo, diaSemana);

		List<DatosProfesoresGuardiasDto> datosProfesoresGuardiasOut = datosProfesoresGuardiasList.stream()
				.map(x -> modelMapper.map(x, DatosProfesoresGuardiasDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(datosProfesoresGuardiasOut, HttpStatus.OK);
	}

}
