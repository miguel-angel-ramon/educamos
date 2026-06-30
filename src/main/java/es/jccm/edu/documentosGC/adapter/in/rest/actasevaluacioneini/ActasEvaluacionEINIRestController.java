package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacioneini;

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
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacioneini.model.DirectoresActaPrivadoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacioneini.model.RegSelDocEINIDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.DirectoresActaPrivadoEINI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.ProfesorActaEvaluacionEINI;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseini.RegSelDocEINI;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneini.IActasEvaluacionEINIService;
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
@Tag(name = "Actas de evaluacion ensenanzas iniciales", description = "Servicio con las operaciones sobre las actas de evaluacion de ensenanzas iniciales documentosgc")
public class ActasEvaluacionEINIRestController {
	
	@Autowired
	private IActasEvaluacionEINIService actasEvaluacionEINIService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresCandidatosActaEvaluacionEINI/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																								 @PathVariable("cAnno") Integer cAnno,
			 																					 @PathVariable("fSesion") String fSesion, 
			 																					 @PathVariable("idOfertamatrig") Long idOfertamatrig, 
			 																					 @PathVariable("idUnidad") Long idUnidad,
			 																					 @PathVariable("fFinconomc") String fFinconomc,
			 																					 @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionEINI> profesoresList = null;
		try {
			profesoresList = actasEvaluacionEINIService.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionEINI/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																									@PathVariable("cAnno") Integer cAnno,
																									@PathVariable("fSesion") String fSesion, 
																									@PathVariable("idOfertamatrig") Long idOfertamatrig, 
																									@PathVariable("idUnidad") Long idUnidad, 
																									@PathVariable("fFinconomc") String fFinconomc,
																									@PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionEINI> profesoresList = null;
		try {
			profesoresList = actasEvaluacionEINIService.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc, fFinconcen);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionEINII/{idCentro}/{idUnidad}/{dia}/{mes}/{cAnno}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																									@PathVariable("idUnidad") Long idUnidad,
																									@PathVariable("dia") Integer dia, 
																									@PathVariable("mes") String mes, 
																									@PathVariable("cAnno") Integer cAnno) {
		
		List<ProfesorActaEvaluacionEINI> profesoresList = null;
		try {
			profesoresList = actasEvaluacionEINIService.getProfesoresSeleccionadosActaEvaluacionEINII(idCentro, idUnidad, dia, mes, cAnno);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	@Operation(summary = "CreaciÃ³n datos regseldoc", description = "Este metodo crea los datos regseldoc", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createRegistrosRegSolDocEINI")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocEINIDto> regSelDocListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocEINI> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocEINI.class)).collect(Collectors.toList());
			
			regSelDocListIn = actasEvaluacionEINIService.createRegistrosRegSolDoc(regSelDocListIn);
			
			List<RegSelDocEINIDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocEINIDto.class)).collect(Collectors.toList());		
		
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
	@GetMapping("/getListadoDirectoresPrivadoEINI/{idConvCentro}")
	public ResponseEntity<List<DirectoresActaPrivadoDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro) {
		
		List<DirectoresActaPrivadoEINI> directores = actasEvaluacionEINIService.getListadoDirectoresPrivado(idConvCentro);
		
		List<DirectoresActaPrivadoDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Convocatorias oferta centro", description = "Este metodo devuelve la convocatoria oferta centro", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getIdConvocatoriaOmcByConvocatoriaCentro/{idConvocatoria}/{idCentro}/{idOfertamatrig}")
	public ResponseEntity<Long> getIdConvocatoriaOmcByConvocatoriaCentro(@PathVariable("idConvocatoria") Long idConvocatoria, 
			@PathVariable("idCentro") Long idCentro, @PathVariable("idOfertamatrig") Long idOfertamatrig) {
		
		Long idConvOmc = actasEvaluacionEINIService.getIdConvocatoriaOmcByConvocatoriaCentro(idConvocatoria, idCentro, idOfertamatrig);
		
		return new ResponseEntity<>(idConvOmc, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion pendientes", description = "Este metodo devuelve los profesores candidatos acta evaluacion pendientes", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCandidatosActaEINIPendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getCandidatosActaBachilleratoPendientes(@PathVariable("idCentro") Long idCentro, 
																						     	   @PathVariable("cAnno") Integer cAnno,
									 														       @PathVariable("fSesion") String fSesion, 
									 															   @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														       @PathVariable("idUnidad") Long idUnidad,
									 														       @PathVariable("fFinconomc") String fFinconomc,
									 															   @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionEINI> profesoresList = null;
		try {
			profesoresList = actasEvaluacionEINIService.getCandidatosActaEINIPendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores seleccionados acta evaluacion pendientes", description = "Este metodo devuelve los profesores seleccionados acta evaluacion pendientes", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSeleccionadosActaEINIPendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getSeleccionadosActaBachilleratoPendientes(@PathVariable("idCentro") Long idCentro, 
																						     	      @PathVariable("cAnno") Integer cAnno,
									 														          @PathVariable("fSesion") String fSesion, 
									 															      @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														          @PathVariable("idUnidad") Long idUnidad,
									 														          @PathVariable("fFinconomc") String fFinconomc,
									 															      @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionEINI> profesoresList = null;
		try {
			profesoresList = actasEvaluacionEINIService.getSeleccionadosActaEINIPendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}
	
	
	

}
