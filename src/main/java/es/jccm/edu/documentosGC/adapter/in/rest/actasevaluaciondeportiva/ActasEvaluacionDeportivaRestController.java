package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluaciondeportiva;

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

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluaciondeportiva.model.DirectoresActaPrivadoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluaciondeportiva.model.RegSelDocDeportivaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.DirectoresActaPrivadoDeportiva;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.ProfesorActaEvaluacionDeportiva;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva.RegSelDocDeportiva;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluaciondeportiva.IActasEvaluacionDeportivaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Actas de evaluacion primaria", description = "Servicio con las operaciones sobre las actas de evaluacion de primaria documentosgc")
public class ActasEvaluacionDeportivaRestController {
	
	@Autowired
	private IActasEvaluacionDeportivaService actasEvaluacionDeportivaService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresCandidatosActaEvaluacionDeportiva/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																								 @PathVariable("cAnno") Integer cAnno,
																								 @PathVariable("fSesion") String fSesion, 
																								 @PathVariable("idOfertamatrig") Long idOfertamatrig, 
																								 @PathVariable("idUnidad") Long idUnidad,
																								 @PathVariable("fFinconomc") String fFinconomc,
																								 @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionDeportiva> profesoresList = null;
		try {
			profesoresList = actasEvaluacionDeportivaService.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc, fFinconcen);
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionDeportiva/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																								    @PathVariable("cAnno") Integer cAnno,
																								    @PathVariable("fSesion") String fSesion, 
																								    @PathVariable("idOfertamatrig") Long idOfertamatrig, 
																								    @PathVariable("idUnidad") Long idUnidad,
																								    @PathVariable("fFinconomc") String fFinconomc,
																								    @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionDeportiva> profesoresList = null;
		try {
			profesoresList = actasEvaluacionDeportivaService.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc, fFinconcen);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}	
	
	/**
	 * CreaciÃ³n de los Datos de para la tabla regseldoc.
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
	@PostMapping("/createRegistrosRegSolDocDeportiva")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocDeportivaDto> regSelDocListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocDeportiva> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocDeportiva.class)).collect(Collectors.toList());
			
			regSelDocListIn = actasEvaluacionDeportivaService.createRegistrosRegSolDoc(regSelDocListIn);
			
			List<RegSelDocDeportivaDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocDeportivaDto.class)).collect(Collectors.toList());		
		
			response = new ResponseEntity<>(regSelDocListOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Listado directores", description = "Este metodo devuelve la lista de directores al genera un acta para un centro privado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoDirectoresPrivadoDeportiva/{idConvCentro}")
	public ResponseEntity<List<DirectoresActaPrivadoDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro) {
		
		List<DirectoresActaPrivadoDeportiva> directores = actasEvaluacionDeportivaService.getListadoDirectoresPrivado(idConvCentro);
		
		List<DirectoresActaPrivadoDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}	

}
