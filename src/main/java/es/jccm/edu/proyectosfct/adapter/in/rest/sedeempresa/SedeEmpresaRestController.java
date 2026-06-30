package es.jccm.edu.proyectosfct.adapter.in.rest.sedeempresa;

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
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.SedeEmpresaDto;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import es.jccm.edu.proyectosfct.application.ports.in.sedeempresa.ISedeEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Empresas", description = "Servicio con las operaciones sobre Empresas")
public class SedeEmpresaRestController {
	
	@Autowired
	private ISedeEmpresaService sedeEmpresaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	/**
	 * Devuelve un listado de empresas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de sedes de una empresa", description = "Este metodo devuelve una lista de las sedes de una empresa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllSedesEmpresaById/{idEmpresa}")
	public ResponseEntity<List<SedeEmpresaDto>> getAllSedesEmpresaById(@PathVariable("idEmpresa") Long idEmpresa){
		
		List<SedeEmpresa> sedes = sedeEmpresaService.getAllSedesEmpresaById(idEmpresa);
		
		List<SedeEmpresaDto> sedesOut = sedes.stream().map(x -> modelMapper.map(x, SedeEmpresaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<SedeEmpresaDto>>(sedesOut, HttpStatus.OK);
	}

	/**
	 * Datos de una Sede.
	 *
	 * @param id Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de una sede", description = "Este metodo devuelve los datos de una sede",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSedeByIdConvenio/{idConvenio}")
	public ResponseEntity<SedeEmpresaDto> getSedeByIdConvenio(@PathVariable("idConvenio") Long idConvenio) 	{

		SedeEmpresa sedeEmpresa = sedeEmpresaService.getSedeByIdConvenio(idConvenio);
		SedeEmpresaDto sedeEmpresaDto = modelMapper.map(sedeEmpresa, SedeEmpresaDto.class);

		return new ResponseEntity<SedeEmpresaDto>(sedeEmpresaDto, HttpStatus.OK);
	}

	/**
	 * Datos de una Sede.
	 *
	 * @param id Id de la sede
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de una sede por su id", description = "Este metodo devuelve los datos de una sede",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSedeByiD/{idSede}")
	public ResponseEntity<SedeEmpresaDto> getSedeById(@PathVariable("idSede") Long idSede) 	{

		SedeEmpresa sedeEmpresa = sedeEmpresaService.getSedeById(idSede);
		SedeEmpresaDto sedeEmpresaDto = modelMapper.map(sedeEmpresa, SedeEmpresaDto.class);

		return new ResponseEntity<SedeEmpresaDto>(sedeEmpresaDto, HttpStatus.OK);
	}

}
