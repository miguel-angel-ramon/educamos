package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModulosCursoDto;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IModulosCursosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Proyectos FCT", description = "Servicio con las operaciones sobre Proyectos FCT")
public class ModulosCursosRestController {
	
	@Autowired
	private IModulosCursosService moduloCursoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// Read
	
		/**
		 * Lista datos.
		 *
		 * @return the response entity
		 */
	    //@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
		@Operation(summary = "Lista de modulos de cursos", description = "Este metodo devuelve una lista con todos los modulos de cursos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getAllModulosCursos")
		public ResponseEntity<List<ModulosCursoDto>> getAllTutoresFctDual(){
			
			List<ModulosCursoDto> modulosOut = moduloCursoService.getAllModulosCurso().stream().map(x -> modelMapper.map(x, ModulosCursoDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ModulosCursoDto>>(modulosOut, HttpStatus.OK);
			
		}
		
		// Read
		
			/**
			 * Lista datos.
			 *
			 * @return the response entity
			 */
	        //@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
			@Operation(summary = "Lista de modulos de cursos", description = "Este metodo devuelve una lista con todos los modulos de cursos dado un id proyecto",
				responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
			@GetMapping("/getAllModulosCursosByProyecto/{idProyecto}")
			public ResponseEntity<List<ModulosCursoDto>> findAllModuloCursoByProyectoId(@PathVariable("idProyecto") Long idProyecto){
				
				List<ModulosCursoDto> modulosOut = moduloCursoService.findAllModuloCursoByProyectoId(idProyecto).stream().map(x -> modelMapper.map(x, ModulosCursoDto.class)).collect(Collectors.toList());
				
				return new ResponseEntity<List<ModulosCursoDto>>(modulosOut, HttpStatus.OK);
				
			}
	
}
