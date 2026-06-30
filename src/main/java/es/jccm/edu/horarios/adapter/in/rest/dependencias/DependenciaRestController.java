package es.jccm.edu.horarios.adapter.in.rest.dependencias;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.horarios.adapter.in.rest.BasePath;
import es.jccm.edu.horarios.adapter.in.rest.dependencias.model.DependenciaLibreListDto;
import es.jccm.edu.horarios.adapter.in.rest.dependencias.model.DependenciaListDto;
import es.jccm.edu.horarios.adapter.in.rest.dependencias.model.TipoDependenciaDto;
import es.jccm.edu.horarios.application.domain.dependencias.DependenciaLibreList;
import es.jccm.edu.horarios.application.domain.dependencias.DependenciaList;
import es.jccm.edu.horarios.application.domain.dependencias.TipoDependencia;
import es.jccm.edu.horarios.application.ports.in.dependencias.IDependenciasService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.FixmeHorariosDependenciasBasePath)
@Tag(name = "Servicio Dependencias Escritorio", description = "Servicio para recuperar las dependencias del módulo de horarios del escritorio")
@CrossOrigin
public class DependenciaRestController {
	
	@Autowired
	private IDependenciasService dependenciasService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
    
    /**
	 * Conectamos con la BBDD de Delphos y sacamos las dependencias de un profesor.
	 *
	 * @param String idUsuario
	 * @param Integer anno
	 * @return List<DependenciaListDto>
	 */
	@PreAuthorize("hasAnyRole('C','RES_CEN')")
    @Operation(summary = "Recuperar dependencias de un profesor", description = "Este metodo devuelve un objeto List con las despendencias de un profesor", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/dependencias")
    public ResponseEntity<List<DependenciaListDto>> getDependencias(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno) {
    	
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
    	List<DependenciaList> dependenciasList = dependenciasService.getDependencias(datosUsuario.getUsuarioDelphos(), anno);
		
		List<DependenciaListDto> dependenciasOut = dependenciasList.stream().map(x -> modelMapper.map(x, DependenciaListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(dependenciasOut, HttpStatus.OK);
    }

	/**
	 * Conectamos con la BBDD de Delphos y sacamos las dependencias libres de un centro para un día específico.
	 *
	 * @param Long codCentro
	 * @param Integer anno
	 * @param Integer diaSemana
	 * @return List<DependenciaLibreListDto>
	 */
	@PreAuthorize("hasAnyRole('C','RES_CEN')")
	@Operation(summary = "Recuperar dependencias libres de un centor", description = "Este metodo devuelve un objeto List con las despendencias libres de un centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/dependenciasLibres")
	public ResponseEntity<Page<DependenciaLibreListDto>> getDependencias(@RequestParam("codCentro") Long codCentro, @RequestParam("anno") Integer anno, @RequestParam("diaSemana") Integer diaSemana, @RequestParam("page") int page, @RequestParam("numItems") int numItems) {

		Page<DependenciaLibreList> dependenciasLibresList = dependenciasService.getDependenciasLibres(codCentro, anno, diaSemana, page, numItems);

		List<DependenciaLibreListDto> dependenciasLibresOut = dependenciasLibresList.getContent().stream().map(x -> modelMapper.map(x, DependenciaLibreListDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(new PageImpl<>(dependenciasLibresOut, dependenciasLibresList.getPageable(), dependenciasLibresList.getTotalElements()), HttpStatus.OK);
	}

	/**
	 * Endpoint que devuelve los tipos de dependencia que hay en un centro
	 *
	 * @param Long codCentro
	 * @return List<String>
	 */
	
	@Operation(summary = "Recuperar tipos de dependencia de un centro", description = "Este metodo devuelve una lista de String con los tipos de dependencia de un centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/tiposDependencia/{codCentro}")
	public ResponseEntity<List<TipoDependenciaDto>> getTiposDependenciaByCentro(@PathVariable("codCentro") Long codCentro) {

		List<TipoDependencia> tiposList = dependenciasService.getTiposDependenciaByCentro(codCentro);

		List<TipoDependenciaDto> tiposOut = tiposList.stream().map(x -> modelMapper.map(x, TipoDependenciaDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(tiposOut, HttpStatus.OK);

	}

}