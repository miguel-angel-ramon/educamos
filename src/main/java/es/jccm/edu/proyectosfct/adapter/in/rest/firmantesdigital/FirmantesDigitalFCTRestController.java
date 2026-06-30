package es.jccm.edu.proyectosfct.adapter.in.rest.firmantesdigital;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.application.ports.in.firmantesdigital.IFirmantesFCTDigitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Firmantes digital", description = "Servicio con las operaciones sobre los firmantes digitales de las actas documentosgc")
public class FirmantesDigitalFCTRestController {
	
	@Autowired
	private IFirmantesFCTDigitalService firmantesDigitalService;
	
	@Autowired
	ModelMapper modelMapper;   	

	////@PreAuthorize("hasAnyRole('C','P','FCT')")
    @Operation(summary = "Obtener entorno de la firma", description = "Este metodo devuelve el entorno de la firma", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getEntornoFirma")
    public ResponseEntity<List<String>> getEntornoFirma(){
    	try {
			return new ResponseEntity<>(firmantesDigitalService.getEntornoFirma(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}    	
    }
}
