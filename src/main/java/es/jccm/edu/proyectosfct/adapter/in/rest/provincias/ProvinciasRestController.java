package es.jccm.edu.proyectosfct.adapter.in.rest.provincias;

import java.util.List;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.datosterritoriales.model.ProvinciaDto;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Provincia;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.IProvinciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio de Trabajadores", description = "Servicio con las operaciones sobre los trabajadores de las empresas")
public class ProvinciasRestController {
	
	@Autowired
	private IProvinciaService provinciaService;
	
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
	@Operation(summary = "Obtener provincias", description = "Este metodo devuelve el listado de provincias", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvincias")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvincias() {
		
		List<Provincia> provincias = provinciaService.getListadoProvincias();
		
		List<ProvinciaDto> trabajadoresDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(trabajadoresDto, HttpStatus.OK);
	}
	
	
	/**
	 * Trabajadores de una empresa.
	 *
	 * @param idEmpresa Id de la empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener provincias", description = "Este metodo devuelve el listado de provincias manchegas", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoProvinciasManchegas")
	public ResponseEntity<List<ProvinciaDto>> getListadoProvinciasManchega() {
		
		List<Provincia> provincias = provinciaService.getListadoProvinciasManchegas();
		
		List<ProvinciaDto> trabajadoresDto = provincias.stream().map(x -> modelMapper.map(x, ProvinciaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ProvinciaDto>>(trabajadoresDto, HttpStatus.OK);
	}
}
