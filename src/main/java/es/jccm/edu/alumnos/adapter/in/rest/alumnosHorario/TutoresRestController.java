package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario;

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

import es.jccm.edu.alumnos.adapter.in.rest.BasePath;
import es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model.TutorDto;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Tutor;
import es.jccm.edu.alumnos.application.ports.in.alumnosHorario.ITutoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.FixmeAlumnosTutoresBasePath)
@Tag(name = "Servicio Tutores Escritorio", description = "Servicio para recuperar los tutores del alumno")
//@CrossOrigin
public class TutoresRestController {

	@Autowired
	private ITutoresService tutoresService;
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Devuelve la cantidad de alumnos de una clase matrículados a una asignatura
	 * concreta
	 *
	 * @param idMateria
	 * @param idUnidad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Contador de alumnos por asignatura", description = "Este metodo devuelve el numero de alumnos de una clase matriculados a una asignatura concreta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresByAlumno")
	public ResponseEntity<List<TutorDto>> getTutoresByAlumno(@RequestParam("idAlumno") Long idAlumno) {
		List<Tutor> tutores = tutoresService.getTutoresByAlumnos(idAlumno);

		List<TutorDto> tutoresOut = tutores.stream().map(x -> modelMapper.map(x, TutorDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(tutoresOut, HttpStatus.OK);
	}

}
