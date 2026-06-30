package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionbachillerato;

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
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionbachillerato.model.ConvocatoriasExtraordinariaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionbachillerato.model.DirectoresActaPrivadoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionbachillerato.model.RegSelDocBachilleratoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ConvocatoriasExtraordinaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.DirectoresActaPrivadoBachillerato;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ProfesorActaEvaluacionBachillerato;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosbachillerato.RegSelDocBachillerato;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionbachillerato.IActasEvaluacionBachilleratoService;
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
@Tag(name = "Actas de evaluacion bachillerato", description = "Servicio con las operaciones sobre las actas de evaluacion de bachillerato documentosgc")
public class ActasEvaluacionBachilleratoRestController {
	
	@Autowired
	private IActasEvaluacionBachilleratoService actasEvaluacionBachilleratoService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresCandidatosActaEvaluacionBachillerato/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																								 @PathVariable("cAnno") Integer cAnno,
			 																					 @PathVariable("fSesion") String fSesion, 
			 																					 @PathVariable("idOfertamatrig") Long idOfertamatrig, 
			 																					 @PathVariable("idUnidad") Long idUnidad,
			 																					 @PathVariable("fFinconomc") String fFinconomc,
			 																					 @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionBachillerato> profesoresList = null;
		try {
			profesoresList = actasEvaluacionBachilleratoService.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionBachillerato/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, 
																									@PathVariable("cAnno") Integer cAnno,
																									@PathVariable("fSesion") String fSesion, 
																									@PathVariable("idOfertamatrig") Long idOfertamatrig, 
																									@PathVariable("idUnidad") Long idUnidad, 
																									@PathVariable("fFinconomc") String fFinconomc,
																									@PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionBachillerato> profesoresList = null;
		try {
			profesoresList = actasEvaluacionBachilleratoService.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc, fFinconcen);
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
	@PostMapping("/createRegistrosRegSolDocBachillerato")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocBachilleratoDto> regSelDocListDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocBachillerato> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocBachillerato.class)).collect(Collectors.toList());
			
			regSelDocListIn = actasEvaluacionBachilleratoService.createRegistrosRegSolDoc(regSelDocListIn);
			
			List<RegSelDocBachilleratoDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocBachilleratoDto.class)).collect(Collectors.toList());		
		
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
	@GetMapping("/getListadoDirectoresPrivadoBachillerato/{idConvCentro}/{fSesion}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<DirectoresActaPrivadoDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro,
																					  @PathVariable("fSesion") String fSesion, 
																					  @PathVariable("fFinconomc") String fFinconomc,
																					  @PathVariable("fFinconcen") String fFinconcen) 
	{
		
		List<DirectoresActaPrivadoBachillerato> directores = actasEvaluacionBachilleratoService.getListadoDirectoresPrivado(idConvCentro,
																															fSesion,
																															fFinconomc,
																															fFinconcen);
		
		List<DirectoresActaPrivadoDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Listado convocatorias extra", description = "Este metodo devuelve la lista de convocatorias extraordinarias", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoConvocatoriasExtraordinaria/{idCentro}/{cAnno}")
	public ResponseEntity<List<ConvocatoriasExtraordinariaDto>> getListadoConvocatoriasExtraordinaria(@PathVariable("idCentro") Long idCentro,
																									  @PathVariable("cAnno") Long cAnno) {
		
		List<ConvocatoriasExtraordinaria> convocatorias = actasEvaluacionBachilleratoService.getListadoConvocatoriasExtraordinaria(idCentro,cAnno);
		
		List<ConvocatoriasExtraordinariaDto> convocatoriasDto = convocatorias.stream().map(entity -> modelMapper.map(entity, ConvocatoriasExtraordinariaDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(convocatoriasDto, HttpStatus.OK);	
	}
	

	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion pendientes", description = "Este metodo devuelve los profesores candidatos acta evaluacion pendientes", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCandidatosActaBachilleratoPendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getCandidatosActaBachilleratoPendientes(@PathVariable("idCentro") Long idCentro, 
																						     	   @PathVariable("cAnno") Integer cAnno,
									 														       @PathVariable("fSesion") String fSesion, 
									 															   @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														       @PathVariable("idUnidad") Long idUnidad,
									 														       @PathVariable("fFinconomc") String fFinconomc,
									 															   @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionBachillerato> profesoresList = null;
		try {
			profesoresList = actasEvaluacionBachilleratoService.getCandidatosActaBachilleratoPendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
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
	@GetMapping("/getSeleccionadosActaBachilleratoPendientes/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{fFinconomc}/{fFinconcen}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getSeleccionadosActaBachilleratoPendientes(@PathVariable("idCentro") Long idCentro, 
																						     	      @PathVariable("cAnno") Integer cAnno,
									 														          @PathVariable("fSesion") String fSesion, 
									 															      @PathVariable("idOfertamatrig") Long idOfertamatrig, 
									 														          @PathVariable("idUnidad") Long idUnidad,
									 														          @PathVariable("fFinconomc") String fFinconomc,
									 															      @PathVariable("fFinconcen") String fFinconcen) {
		
		List<ProfesorActaEvaluacionBachillerato> profesoresList = null;
		try {
			profesoresList = actasEvaluacionBachilleratoService.getSeleccionadosActaBachilleratoPendientes(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, fFinconomc,fFinconcen);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProfesorActaEvaluacionDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorActaEvaluacionDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}
	
	
	

}
