package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionelementales;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionelementales.model.DirectoresActaPrivadoElementalesDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionelementales.model.RegSelDocElementalesDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.DirectoresActaPrivadoElementales;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.ProfesorActaEvaluacionElementales;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoselementales.RegSelDocElementales;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionelementales.IActasEvaluacionElementalesService;


@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Actas de evaluacion Idioma", description = "Servicio con las operaciones sobre las actas de evaluacion de enseñanzas elementales documentosgc")
public class ActasEvaluacionElementalesRestController {
	@Autowired
	private IActasEvaluacionElementalesService actasEvaluacionElementalesService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") }) 
	@GetMapping("/getProfesoresCandidatosActaEvaluacionElementales/{idCentro}/{cAnno}/{fSesion}/{idCursoEtapa}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro,
																								 @PathVariable("cAnno") Integer cAnno,																								 
																								 @PathVariable("fSesion") String fSesion, 
																								 @PathVariable("idCursoEtapa") Long idCursoEtapa, 
																								 @PathVariable("idUnidad") Long idUnidad,
			 																					 @PathVariable("fFinconomc") String fFinconomc,
			 																					 @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionElementales> profesoresList = null;
		try {
			profesoresList = actasEvaluacionElementalesService.getProfesoresCandidatosActaEvaluacion(idCentro, 
																									 cAnno, 
																									 fSesion, 
																									 idCursoEtapa, 
																									 idUnidad, 
																									 fFinconomc,
																									 fFinconcen);
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}	
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores seleccionados acta evaluacion", description = "Este metodo devuelve los profesores seleccionados acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionElementales/{idCentro}/{cAnno}/{fSesion}/{idCursoEtapa}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																									@PathVariable("cAnno") Integer cAnno, 
																									@PathVariable("fSesion") String fSesion, 
																									@PathVariable("idCursoEtapa") Long idCursoEtapa, 
																									@PathVariable("idUnidad") Long idUnidad, 
				 																					@PathVariable("fFinconomc") String fFinconomc,
				 																					@PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionElementales> profesoresList = null;
		try {
			profesoresList = actasEvaluacionElementalesService.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																										cAnno, 
																										fSesion, 
																										idCursoEtapa, 
																										idUnidad,
																										fFinconomc,
																										fFinconcen);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}	
	
	/**
	 * Creacion de los Datos de para la tabla regseldoc.
	 *
	 * @param estadoDocumentoGCDto Datos Estado documento
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Creacion datos regseldoc", description = "Este metodo crea los datos regseldoc", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createRegistrosRegSolDocElementales")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocElementalesDto> regSelDocListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocElementales> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocElementales.class)).collect(Collectors.toList());
						
			regSelDocListIn = actasEvaluacionElementalesService.createRegistrosRegSolDoc(regSelDocListIn);
			
			List<RegSelDocElementalesDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocElementalesDto.class)).collect(Collectors.toList());		
		
			response = new ResponseEntity<>(regSelDocListOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}	

	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Listado directorres", description = "Este metodo devuelve la lista de directores al genera un acta para un centro privado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoDirectoresPrivadoElementales/{idConvCentro}")
	public ResponseEntity<List<DirectoresActaPrivadoElementalesDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro) {
		
		List<DirectoresActaPrivadoElementales> directores = actasEvaluacionElementalesService.getListadoDirectoresPrivado(idConvCentro);
		
		List<DirectoresActaPrivadoElementalesDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoElementalesDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}	
}
