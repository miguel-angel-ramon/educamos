package es.jccm.edu.alumnos.adapter.in.rest.acneae;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.acneae.model.AlumnoNEEDto;
import es.jccm.edu.alumnos.application.domain.acneae.AlumnoNEE;
import es.jccm.edu.alumnos.application.domain.acneae.DatosAlumnoNEE;
import es.jccm.edu.alumnos.application.ports.in.acneae.IAlumnoNEEService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Alumnos ACNEAE", description="Servicio para recuperar datos de alumnos con necesidades educativas especiales")
//@CrossOrigin
public class AlumnosNEERestController {
	
	@Autowired 
	private IAlumnoNEEService alumnoNEEService;
	@Autowired
	private ModelMapper modelMapper;
	
	@Operation(summary="Recuperar datos de alumnos con necesidades educativas especiales",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/acneae/{idCentro}/{annio}/{idofertaMatriculacion}/{unidad}")
	public ResponseEntity <CollectionModel<AlumnoNEEDto>> getAlumnoNEE(@PathVariable Long idCentro,@PathVariable  int annio,@PathVariable Long idOfertaMatriculacion, @PathVariable Long unidad){
		
		List<AlumnoNEE> alumnos= alumnoNEEService.getAlumnosNEE(idCentro, annio, idOfertaMatriculacion, unidad);
		
		List<AlumnoNEEDto> alumnosNEE=alumnos.stream()
				.map(x->modelMapper.map(x,AlumnoNEEDto.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(CollectionModel.of(alumnosNEE));
	}

	@Operation(summary="Recuperar datos de alumnos con necesidades educativas especiales",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/acneae/detallealumno")
	public ResponseEntity <DatosAlumnoNEE>getDatosAlumnoNEE(@RequestParam Long idMatricula){
		
		DatosAlumnoNEE datosAlumno=alumnoNEEService.getDatosAlumnoNEE(idMatricula);
		return ResponseEntity.ok().body(datosAlumno);
		
	}
}
