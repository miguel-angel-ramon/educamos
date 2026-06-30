package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DocenteProjection;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IPuestoTrabajoEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Puestos de Trabajo", description = "Servicio con las operaciones sobre los Puestos de Trabajo de un centro")
public class PuestoTrabajoEmpleadoRestController {
	
	@Autowired
	private IPuestoTrabajoEmpleadoService puestoTrabajoEmpleadoService;
	
	// Read 
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener datos del Docente", description = "Este metodo devuelve los datos del Docente", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDocentesByCentro/{centroId}")
	public ResponseEntity<List<DocenteProjection>> getDocentesByCentro(
			@Parameter(description = "Identificador del Docente", required = true) @PathVariable("centroId") Long centroId) {
		List<DocenteProjection> res = new ArrayList<DocenteProjection>();
		
		res = puestoTrabajoEmpleadoService.getDocentesByCentro(centroId);
		
		return new ResponseEntity<List<DocenteProjection>>(res, HttpStatus.OK);
	}

}
