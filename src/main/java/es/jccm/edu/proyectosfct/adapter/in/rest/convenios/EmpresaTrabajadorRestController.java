package es.jccm.edu.proyectosfct.adapter.in.rest.convenios;

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
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.EmpresaTrabajadorDto;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IEmpresaTrabajadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio de Trabajadores", description = "Servicio con las operaciones sobre los trabajadores de las empresas")
public class EmpresaTrabajadorRestController {
	
	@Autowired
	private IEmpresaTrabajadorService empresaTrabajadorService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Read
	
	/**
	 * Trabajadores de una empresa.
	 *
	 * @param idEmpresa Id de la empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener trabajadores de una empresa", description = "Este metodo devuelve los datos de los trabajadores de una empresa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTrabajadoresByEmpresa/{idEmpresa}")
	public ResponseEntity<List<EmpresaTrabajadorDto>> getTrabajadoresByEmpresa(
			@Parameter(description = "Identificador de la Empresa", required = true) @PathVariable("idEmpresa") Long idEmpresa) {
		
		List<EmpresaTrabajador> trabajadores = empresaTrabajadorService.getTrabajadoresByEmpresa(idEmpresa);
		
		List<EmpresaTrabajadorDto> trabajadoresDto = trabajadores.stream().map(x -> modelMapper.map(x, EmpresaTrabajadorDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaTrabajadorDto>>(trabajadoresDto, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener trabajadores de una empresa", description = "Este metodo devuelve los datos de los trabajadores de una empresa", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getRepresentanteBySede/{idSede}")
	public ResponseEntity<List<EmpresaTrabajadorDto>> getRepresentanteBySede(@PathVariable("idSede")Long idSede) {
			
				List<EmpresaTrabajador> representante = empresaTrabajadorService.getRepresentanteEmpresabySede(idSede);
				List<EmpresaTrabajadorDto> representanteDto = representante.stream().map(entity -> modelMapper.map(entity, EmpresaTrabajadorDto.class)).collect(Collectors.toList());

				return new ResponseEntity<List<EmpresaTrabajadorDto>>(representanteDto,HttpStatus.OK);
				

}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener trabajadores de una empresa", description = "Este metodo devuelve los datos de los trabajadores de una empresa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getResponsablesByEmpresa/{idEmpresa}")
	public ResponseEntity<List<EmpresaTrabajadorDto>> getResponsablesByEmpresa(
			@Parameter(description = "Identificador de la Empresa", required = true) @PathVariable("idEmpresa") Long idEmpresa) {
		
		List<EmpresaTrabajador> trabajadores = empresaTrabajadorService.getResponsablesByEmpresa(idEmpresa);
		
		List<EmpresaTrabajadorDto> trabajadoresDto = trabajadores.stream().map(x -> modelMapper.map(x, EmpresaTrabajadorDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaTrabajadorDto>>(trabajadoresDto, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Obtener trabajadores de una empresa", description = "Este metodo devuelve los datos de los trabajadores de una empresa", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getResponsablesBySede/{idSede}")
	public ResponseEntity<List<EmpresaTrabajadorDto>> getResponsablesBySede(@PathVariable("idSede")Long idSede) {
			
				List<EmpresaTrabajador> responsable = empresaTrabajadorService.getResponsablesBySede(idSede);
				List<EmpresaTrabajadorDto> responsableDto = responsable.stream().map(entity -> modelMapper.map(entity, EmpresaTrabajadorDto.class)).collect(Collectors.toList());

				return new ResponseEntity<List<EmpresaTrabajadorDto>>(responsableDto,HttpStatus.OK);
				

}

}
