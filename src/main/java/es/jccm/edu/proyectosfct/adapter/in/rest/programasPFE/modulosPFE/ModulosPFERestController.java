package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.modulosPFE;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.modulosPFE.IModulosFPE;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio modulos programas PFE", description = "Servicio con las operaciones sobre modulos programas PFE")
public class ModulosPFERestController {
	
    @Autowired
    private ModelMapper modelMapper;
	
    @Autowired
    private IModulosFPE modulosFPE;
    
	@Operation(summary = "Lista de Modulos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModulosDisponibles/{idProgramaFPE}/{idCurso}/{idCentro}/{cAnno}")
	public ResponseEntity<List<ElementoSelectDto>> getModulosModalidadLOFP(@PathVariable("idProgramaFPE") Long idProgramaFPE,
			                                                                @PathVariable("idCurso") Long idCurso,
			                                                                @PathVariable("idCentro") Long idCentro,
																		    @PathVariable("cAnno") Integer cAnno
																		    ) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> modulos = modulosFPE.getModulosDisponibles(idProgramaFPE, idCurso, idCentro, cAnno);

			List<ElementoSelectDto> modulosDto = modulos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(modulosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Lista de Modulos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getModulosSeleccionados/{idProgramaFPE}")
	public ResponseEntity<List<ElementoSelectDto>> getModulosModalidadLOFP(@PathVariable("idProgramaFPE") Long idProgramaFPE) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> modulos = modulosFPE.getModulosSeleccionados(idProgramaFPE);

			List<ElementoSelectDto> modulosDto = modulos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(modulosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@Operation(summary = "Creación de Alumno Convenio Programa", description = "Este metodo crea Alumno Convenio Programa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/saveModulosFPE")
	public ResponseEntity<Object> saveModulosFPE(@RequestParam("idProgramaFPE") Long idProgramaFPE,
	                                             @RequestBody List<Long> modulos) {
	    try {
	        modulosFPE.saveModulosFPE(idProgramaFPE, modulos);
	        
	        return ResponseEntity.ok().build();
	        
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                Map.of(
	                    "message", "Error asignando módulos",
	                    "detail",  e.getMessage()
	                )
	            );
	    }
	}
    
    
    
    
	
	

}
