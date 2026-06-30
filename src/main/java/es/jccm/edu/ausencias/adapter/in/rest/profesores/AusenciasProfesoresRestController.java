package es.jccm.edu.ausencias.adapter.in.rest.profesores;

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
import es.jccm.edu.ausencias.adapter.in.rest.profesores.model.AusenciasProfesoresDto;
import es.jccm.edu.ausencias.application.domain.profesores.AusenciasProfesores;
import es.jccm.edu.ausencias.application.ports.in.profesores.IAusenciasProfesores;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.FixmeAusenciasProfesoresBasePath)
@Tag(name = "Servicio Ausencia Profesores", description = "Servicio para recuperar el módulo de ausencia de profesorado del esritorio de Eq.Directivo")
@CrossOrigin
public class AusenciasProfesoresRestController {

	@Autowired
	private IAusenciasProfesores ausenciasProfesoresService;

	@Autowired
	private ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','RES_CEN')")
	@Operation(summary = "Recuperar ausencias de profesores", description = "Recuperamos una lista con todas las ausencias de los profesores de un determinado centro y año", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAusenciasProfesores")
	public ResponseEntity<List<AusenciasProfesoresDto>> getAusencioProfesoras(@RequestParam("codCentro") Long codCentro,
			@RequestParam("anno") Integer anno, @RequestParam("fecha") String fecha) {

		List<AusenciasProfesores> ausenciasProfesoresList = ausenciasProfesoresService.getAusenciasProfesores(codCentro,
				anno, fecha);

		List<AusenciasProfesoresDto> ausenciasProfesoresOut = ausenciasProfesoresList.stream()
				.map(x -> modelMapper.map(x, AusenciasProfesoresDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(ausenciasProfesoresOut, HttpStatus.OK);

	}
}
