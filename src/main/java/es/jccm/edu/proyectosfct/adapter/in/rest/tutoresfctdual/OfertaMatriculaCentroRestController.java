package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual;

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
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.OfertaMatriculaProjection;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IOfertaMatriculaCentroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Ofertas", description = "Servicio con las operaciones sobre las Ofertas dadas por un centro")
public class OfertaMatriculaCentroRestController {
	
	@Autowired
	private IOfertaMatriculaCentroService ofertaMatriculaCentroService;
	
	// Read
	
	/**
	 * Datos de las ofertas de un centro id-descripcion.
	 *
	 * @param centroId Id del centro
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener datos de la Oferta", description = "Este metodo devuelve los datos de la Oferta", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getOfertasByCentro/{centroId}")
	public ResponseEntity<List<OfertaMatriculaProjection>> getOfertasByCentro(
			@Parameter(description = "Identificador del Centro", required = true) @PathVariable("centroId") Long centroId) {
		
		List<OfertaMatriculaProjection> ofertas = ofertaMatriculaCentroService.getOfertasByCentro(centroId);
		
		return new ResponseEntity<List<OfertaMatriculaProjection>>(ofertas, HttpStatus.OK);
	}

}
