package es.jccm.edu.documentosGC.adapter.in.rest.actaevaluacionarte;

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
import es.jccm.edu.documentosGC.adapter.in.rest.actaevaluacionarte.model.DirectoresActaPrivadoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actaevaluacionarte.model.RegSelDocArteDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.DirectoresActaPrivadoArte;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.ProfesorActaEvaluacionArte;
import es.jccm.edu.documentosGC.application.domain.registrodocumentosarte.RegSelDocArte;
import es.jccm.edu.documentosGC.application.ports.in.actaevaluacionarte.IActasEvaluacionArteService;
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
@Tag(name="Actas de evaluacion arte",description = "Servicio con las operaciones sobre las actas de evaluacion de artes documentosgv")
public class ActasEvaluacionArteRestController {
	
	@Autowired
	private IActasEvaluacionArteService actasEvaluacionArteService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCandidatosCombosObraFinal/{idCentro}/{fSesion}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>>getCandidatosCombosObraFinal(@PathVariable("idCentro") Long idCentro, 
			 																			@PathVariable("fSesion") String fSesion) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
	
		try {
			profesoresList = actasEvaluacionArteService.getCandidatosCombosObraFinal(idCentro, fSesion);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}	
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresCandidatosActaEvaluacionArte/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																								 @PathVariable("cAnno") Integer cAnno,
			 																					 @PathVariable("fSesion") String fSesion, 
			 																					 @PathVariable("idOfertamatrig") Long idOfertamatrig, 
			 																					 @PathVariable("idUnidad") Long idUnidad,			 																					
			 																					 @PathVariable("fFinconomc") String fFinconomc,
			 																					 @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
	
		try {
			profesoresList = actasEvaluacionArteService.getProfesoresCandidatosActaEvaluacion(idCentro, 
																							  cAnno, 
																							  fSesion, 
																							  idOfertamatrig, 
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionArte/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																									@PathVariable("cAnno") Integer cAnno,
																									@PathVariable("fSesion") String fSesion, 
																									@PathVariable("idOfertamatrig") Long idOfertamatrig, 
																									@PathVariable("idUnidad") Long idUnidad,
				 																					@PathVariable("fFinconomc") String fFinconomc,
				 																					@PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
		try {
			profesoresList = actasEvaluacionArteService.getProfesoresSeleccionadosActaEvaluacion(idCentro, 
																								 cAnno, 
																								 fSesion, 
																								 idOfertamatrig, 
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
	@PostMapping("/createRegistrosRegSolDocArte")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocArteDto> regSelDocListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocArte> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocArte.class)).collect(Collectors.toList());
			
			regSelDocListIn = actasEvaluacionArteService.createRegistrosRegSolDoc(regSelDocListIn);
			
			List<RegSelDocArteDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocArteDto.class)).collect(Collectors.toList());		
		
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
	@GetMapping("/getListadoDirectoresPrivadoArte/{idConvCentro}")
	public ResponseEntity<List<DirectoresActaPrivadoDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro) {
		
		List<DirectoresActaPrivadoArte> directores = actasEvaluacionArteService.getListadoDirectoresPrivado(idConvCentro);
		
		List<DirectoresActaPrivadoDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}
	
//	@Operation(summary = "Listado convocatorias extra", description = "Este metodo devuelve la lista de convocatorias extraordinarias", 
//			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
//		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
//				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
//				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
//				@ApiResponse(responseCode = "404", description = "No encontrado") })
//	@GetMapping("/getListadoConvocatoriasExtraordinaria/{idCentro}/{cAnno}")
//	public ResponseEntity<List<ConvocatoriasExtraordinariaDto>> getListadoConvocatoriasExtraordinaria(@PathVariable("idCentro") Long idCentro,
//																									  @PathVariable("cAnno") Long cAnno) {
//		
//		List<ConvocatoriasExtraordinariaArte> convocatorias = actasEvaluacionArteService.getListadoConvocatoriasExtraordinaria(idCentro,cAnno);
//		
//		List<ConvocatoriasExtraordinariaDto> convocatoriasDto = convocatorias.stream().map(entity -> modelMapper.map(entity, ConvocatoriasExtraordinariaDto.class))
//														 .collect(Collectors.toList());		
//		
//		return new ResponseEntity<>(convocatoriasDto, HttpStatus.OK);	
//	}
	
//	@Operation(summary = "Listado convocatorias extra", description = "Este metodo devuelve la lista de convocatorias extraordinarias", 
//			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
//		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
//				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
//				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
//				@ApiResponse(responseCode = "404", description = "No encontrado") })
//	@GetMapping("/getConvocatoriasCorrespondencia/{idCentro}/{cAnno}/{idOfertamatrig}/{idConvUnidad}")
//	public ResponseEntity<List<ConvocatoriasCorrespondenciaDto>> getConvocatoriasCorrespondencia(@PathVariable("idCentro") Long idCentro,
//																							    @PathVariable("cAnno") Long cAnno,
//																							    @PathVariable("idOfertamatrig") Long idOfertamatrig,
//																							    @PathVariable("idConvUnidad") Long idConvUnidad
//																							    ) {
//		
//		List<ConvocatoriasCorrespondenciaArte> convocatorias = actasEvaluacionArteService.getConvocatoriasCorrespondencia(idCentro,cAnno,idOfertamatrig, idConvUnidad);
//		
//		List<ConvocatoriasCorrespondenciaDto> convocatoriasDto = convocatorias.stream().map(entity -> modelMapper.map(entity, ConvocatoriasCorrespondenciaDto.class))
//														 .collect(Collectors.toList());		
//		
//		return new ResponseEntity<>(convocatoriasDto, HttpStatus.OK);	
//	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion pendientes", description = "Este metodo devuelve los profesores candidatos acta evaluacion pendientes", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCandidatosActaArtePendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getCandidatosActaArtePendientes(@PathVariable("idCentro") Long idCentro, 
																						     	   @PathVariable("cAnno") Integer cAnno,
									 														       @PathVariable("fSesion") String fSesion, 
									 															   @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														       @PathVariable("idUnidad") Long idUnidad,
									 														       @PathVariable("fFinconomc") String fFinconomc,
									 															   @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
		try {
			profesoresList = actasEvaluacionArteService.getCandidatosActaArtePendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
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
	@GetMapping("/getSeleccionadosActaArtePendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getSeleccionadosActaArtePendientes(@PathVariable("idCentro") Long idCentro, 
																						     	      @PathVariable("cAnno") Integer cAnno,
									 														          @PathVariable("fSesion") String fSesion, 
									 															      @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														          @PathVariable("idUnidad") Long idUnidad,
									 														          @PathVariable("fFinconomc") String fFinconomc,
									 															      @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
		try {
			profesoresList = actasEvaluacionArteService.getSeleccionadosActaArtePendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
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
	@GetMapping("/getVocalesCandidatosActaEvaluacionArte/{idCentro}/{cAnno}/{fSesion}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getVocalesSeleccionadosActaEvaluacionArte(@PathVariable("idCentro") Long idCentro, 
															@PathVariable("cAnno") Integer cAnno,
															@PathVariable("fSesion") String fSesion) {
		
		List<ProfesorActaEvaluacionArte> profesoresList = null;
		try {
			profesoresList = actasEvaluacionArteService.getVocalesCandidatosActaEvaluacion(idCentro,cAnno,fSesion);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}
	
}
