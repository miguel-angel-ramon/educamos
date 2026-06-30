package es.jccm.edu.alumnos.adapter.in.rest.familiasAlumnado;

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

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FamiliaAlumnado;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.TutorFamilia;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IAlumnosFamiliaService;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IAlumnosTutorService;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IFamiliasCentroService;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.ITutoresCentroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Familias de alumnos de un Centro", description="Recupera datos sobre los alumnos de un centro r")
//@CrossOrigin
public class FamiliasAlumnadoRestController {
	
	@Autowired
	private IFamiliasCentroService familiasService;
	@Autowired
	private ITutoresCentroService tutoresService;
	@Autowired
	private IAlumnosFamiliaService alumnosFamiliaService;
	@Autowired
	private IAlumnosTutorService alumnosTutorService;
	
	
		@Operation (summary="Recuperar datos de las familias del alumnado de un centro",
		responses= {@ApiResponse(content=@Content(mediaType="application/json", schema=@Schema(implementation=ResponseEntity.class))) })
		@ApiResponses(value = {@ApiResponse(responseCode="200" , description="OK, obtenido correctamente"),@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/familiascentro/{idCentro}/{annio}")
		public ResponseEntity<CollectionModel<FamiliaAlumnado>>getFamiliasCentro(@PathVariable Long idCentro,@PathVariable int annio){
			
			return ResponseEntity.ok().body(CollectionModel.of(familiasService.getFamiliasAlumnadoCentro(idCentro, annio)));
			
		}
		
		@Operation (summary="Recupera los tutores de los alumnos de un centro ",
				responses= {@ApiResponse(content=@Content(mediaType="application/json", schema=@Schema(implementation=ResponseEntity.class))) })
				@ApiResponses(value = {@ApiResponse(responseCode="200" , description="OK, obtenido correctamente"),@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/tutorescentro/{idCentro}/{annio}")
		public ResponseEntity <CollectionModel<TutorFamilia>>getTutoresCentro(@PathVariable Long idCentro,@PathVariable int annio){
			
			return ResponseEntity.ok().body(CollectionModel.of(tutoresService.getTutoresAlumnadoCentro(idCentro, annio)	));
		}
		
		@Operation (summary="Recupera los alumnos de una familia ",
				responses= {@ApiResponse(content=@Content(mediaType="application/json", schema=@Schema(implementation=ResponseEntity.class))) })
				@ApiResponses(value = {@ApiResponse(responseCode="200" , description="OK, obtenido correctamente"),@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/alumnosfamilia/{idFamilia}")
		public ResponseEntity <CollectionModel<FAlumno>>getAlumnosFamilia(@PathVariable Long idFamilia){
			
			return ResponseEntity.ok().body(CollectionModel.of(alumnosFamiliaService.getAlumnosDeUnaFamilia(idFamilia)	));
		}
		
		@Operation (summary="Recupera los alumnos de un tutor ",
				responses= {@ApiResponse(content=@Content(mediaType="application/json", schema=@Schema(implementation=ResponseEntity.class))) })
				@ApiResponses(value = {@ApiResponse(responseCode="200" , description="OK, obtenido correctamente"),@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/alumnostutor/{idTutor}")
		public ResponseEntity <CollectionModel<FAlumno>>getAlumnosTutor(@PathVariable Long idTutor,@RequestParam Optional<Long>idCentro){
			
			return ResponseEntity.ok().body(CollectionModel.of(alumnosTutorService.getAlumnosDeUnTutor(idTutor,idCentro	)));
		}
	
}
