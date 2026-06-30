package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ModulosEmpresasDto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosEmpresas;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IModulosEmpresasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Proyectos FCT", description = "Servicio con las operaciones sobre Proyectos FCT")
public class ModulosEmpresasRestController {
	
	@Autowired
	private IModulosEmpresasService modulosEmpresasService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	/**
	 * Creación de Modulos de empresas.
	 *
	 * @param 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de Modulos de empresas", description = "Este metodo crea Modulos de empresas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createModuloEmpresa")
	public ResponseEntity<ModulosEmpresasDto> createProyecto(
			@Parameter(description = "Datos del Proyecto", required = true) @RequestBody final ModulosEmpresasDto moduloEmpresaDto) {
		try {
			ModulosEmpresas moduloEmpresaIn = modelMapper.map(moduloEmpresaDto, ModulosEmpresas.class);
			
			moduloEmpresaIn = modulosEmpresasService.createM(moduloEmpresaIn);

			ModulosEmpresasDto proyectoOut = modelMapper.map(moduloEmpresaIn, ModulosEmpresasDto.class);
			
			return new ResponseEntity<ModulosEmpresasDto>(proyectoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creación de los Datos de modulos a empresas.
	 *
	 * @param  modulo empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de modulos a empresas", description = "Este metodo creamodulos a empresas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createModulosEmpresas/{idConvProy}")
	public ResponseEntity<List<ModulosEmpresasDto>> createModuloActividad(
			@PathVariable("idConvProy") Long idConvProy,
			@Parameter(description = "Datos del Convenio", required = true) @RequestBody final List<ModulosEmpresasDto> moduloEmpresaDto) {
		
 		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		List<ModulosEmpresas> moduloEmpresa = new ArrayList<>();
		 if(moduloEmpresaDto != null && !moduloEmpresaDto.isEmpty()) {
			 moduloEmpresa = moduloEmpresaDto.stream().map(x -> modelMapper.map(x, ModulosEmpresas.class)).collect(Collectors.toList());
		 }
		moduloEmpresa = modulosEmpresasService.createModuloEmpresa(moduloEmpresa, idConvProy);
		
		List<ModulosEmpresasDto> moduloEmpresaOut = moduloEmpresa.stream().map(x -> modelMapper.map(x, ModulosEmpresasDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ModulosEmpresasDto>>(moduloEmpresaOut, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Modulo Empresa", description = "Este metodo devuelve el modulo Empresa", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getIsCheckedModuloEmpresa/{idModCurso}/{idConvProy}" })
	public ResponseEntity<Boolean> getIsCheckedModuloActividad(@PathVariable("idModCurso") Long idModCurso, @PathVariable("idConvProy") Long idConvProy) {
		
		Boolean isChecked = modulosEmpresasService.getIsCheckedModuloEmpresa(idModCurso, idConvProy);
		
		return new ResponseEntity<Boolean>(isChecked, HttpStatus.OK);
	}	
	
	
}
