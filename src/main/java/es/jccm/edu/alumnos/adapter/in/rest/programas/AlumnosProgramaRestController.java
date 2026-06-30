package es.jccm.edu.alumnos.adapter.in.rest.programas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.application.domain.programas.AlumnoProgramaDTO;
import es.jccm.edu.alumnos.application.domain.programas.MateriaAsociada;
import es.jccm.edu.alumnos.application.ports.in.programas.IAlumnosProgramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Alumnos asociados a un programa", description="Servicio para recuperar los alumnos y materias de un curso/unidad asociados a programas educativos")
//@CrossOrigin

public class AlumnosProgramaRestController {
	
	@Autowired
	private IAlumnosProgramaService alumnosPrograma;
	
	
	@Operation (summary="Recupera los alumnos y materias asociados a un programa",
	responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/programas/{annio}/{ofertaMatriculacion}")
	public ResponseEntity <CollectionModel<AlumnoProgramaDTO>> getAlumnosPrograma (@PathVariable Long ofertaMatriculacion, @PathVariable int annio,
			@RequestParam Optional<Long> unidad,@RequestParam 	Optional<Long> programa){
		return ResponseEntity.ok().body(CollectionModel.of(alumnosPrograma.getAlumnosProgama(ofertaMatriculacion, annio, unidad, programa)));   
	}
	
	@Operation(summary="Recupera las materias asociadas a un programa", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/programas/{annio}/{ofertaMatriculacion}/materias")
	public ResponseEntity <CollectionModel<MateriaAsociada>> getMateriasAsociadas(@PathVariable int annio, @PathVariable Long ofertaMatriculacion,
			@RequestParam Long programa){
		
		return ResponseEntity.ok().body(CollectionModel.of(alumnosPrograma.getMateriasAsociadas(programa,  ofertaMatriculacion, annio)));
	}
	
	
}
