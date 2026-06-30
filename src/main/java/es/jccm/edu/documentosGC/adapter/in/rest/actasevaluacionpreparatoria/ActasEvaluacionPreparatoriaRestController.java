package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionpreparatoria;

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
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionpreparatoria.model.DirectoresActaPrivadoPreparatoriaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionpreparatoria.model.RegSelDocPreparatoriaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorActaEvaluacionDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ProfesorFirmanteDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.DirectoresActaPrivadoPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorActaEvaluacionPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorFirmante;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentospreparatoria.RegSelDocPreparatoria;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionpreparatoria.IActasEvaluacionPreparatoriaService;
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
@Tag(name = "Actas de evaluacion ESO", description = "Servicio con las operaciones sobre las actas de evaluacion de ESO documentosgc")
public class ActasEvaluacionPreparatoriaRestController {
	
	@Autowired
	private IActasEvaluacionPreparatoriaService actasEvaluacionPreparatoriaService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores candidatos acta evaluacion", description = "Este metodo devuelve los profesores candidatos acta evaluacion", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProfesoresCandidatosActaEvaluacionPreparatoriaESO/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{idConvUnidad}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresCandidatosActaEvaluacion(@PathVariable("idCentro") Long idCentro, @PathVariable("cAnno") Integer cAnno,@PathVariable("idConvUnidad") Long idConvUnidad
			, @PathVariable("fSesion") String fSesion, @PathVariable("idOfertamatrig") Long idOfertamatrig, @PathVariable("idUnidad") Long idUnidad) {
		
		List<ProfesorActaEvaluacionPreparatoria> profesoresList = null;
		try {
			profesoresList = actasEvaluacionPreparatoriaService.getProfesoresCandidatosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, idConvUnidad);
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
	@GetMapping("/getProfesoresSeleccionadosActaEvaluacionPreparatoriaESO/{idCentro}/{cAnno}/{fSesion}/{idOfertamatrig}/{idUnidad}/{idConvUnidad}")
	public ResponseEntity<List<ProfesorActaEvaluacionDto>> getProfesoresSeleccionadosActaEvaluacion(@PathVariable("idCentro") Long idCentro, @PathVariable("cAnno") Integer cAnno
			, @PathVariable("fSesion") String fSesion, @PathVariable("idOfertamatrig") Long idOfertamatrig, @PathVariable("idUnidad") Long idUnidad, @PathVariable("idConvUnidad") Long idConvUnidad) {
		
		List<ProfesorActaEvaluacionPreparatoria> profesoresList = null;
		try {
			profesoresList = actasEvaluacionPreparatoriaService.getProfesoresSeleccionadosActaEvaluacion(idCentro, cAnno, fSesion, idOfertamatrig, idUnidad, idConvUnidad);
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
	@PostMapping("/createRegistrosRegSolDocPreparatoria/{idUnidad}")
	public ResponseEntity<Object> createRegistrosRegSolDoc(
			@Parameter(description = "RegSelDoc", required = true) @RequestBody final List<RegSelDocPreparatoriaDto> regSelDocListDto,
																   @PathVariable("idUnidad") Long idUnidad) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<RegSelDocPreparatoria> regSelDocListIn = regSelDocListDto.stream().map(entity -> modelMapper.map(entity, RegSelDocPreparatoria.class)).collect(Collectors.toList());
						
			regSelDocListIn = actasEvaluacionPreparatoriaService.createRegistrosRegSolDoc(regSelDocListIn,idUnidad);
			
			List<RegSelDocPreparatoriaDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocPreparatoriaDto.class)).collect(Collectors.toList());		
		
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
	@GetMapping("/getListadoDirectoresPrivadoPreparatoria/{idConvCentro}")
	public ResponseEntity<List<DirectoresActaPrivadoPreparatoriaDto>> getListadoDirectoresPrivado(@PathVariable("idConvCentro") Long idConvCentro) {
		
		List<DirectoresActaPrivadoPreparatoria> directores = actasEvaluacionPreparatoriaService.getListadoDirectoresPrivado(idConvCentro);
		
		List<DirectoresActaPrivadoPreparatoriaDto> tldocumentosDto = directores.stream().map(entity -> modelMapper.map(entity, DirectoresActaPrivadoPreparatoriaDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Oferta matricula curso", description = "Este metodo devuelve oferta matricula curso", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getOfertaMatriculaCurso/{idOfertamatrig}/{idCentro}")
	public ResponseEntity<Integer> getOfertaMatriculaCurso(@PathVariable("idOfertamatrig") Long idOfertamatrig, @PathVariable("idCentro") Long idCentro) {
		
		Integer idOfertamatric = actasEvaluacionPreparatoriaService.getOfertaMatriculaCurso(idOfertamatrig, idCentro);
		
		return new ResponseEntity<>(idOfertamatric, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Profesores firmantes preparatoros", description = "Este metodo devuelve los profesores firmantes para los cursos preparatorios", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFirmantesProfesoradoPreparatorios/{cAnno}/{xCentro}/{idUnidad}/{idCurso}/{sesion}")
	public ResponseEntity<List<ProfesorFirmanteDto>> getFirmantesProfesoradoPreparatorios(@PathVariable("cAnno") Long cAnno, 
																						  @PathVariable("xCentro") Long xCentro, 
																						  @PathVariable("idUnidad") String idUnidad, 
																						  @PathVariable("idCurso") Long idCurso, 
																						  @PathVariable("sesion") String sesion) {
	
		List<ProfesorFirmante>	profesoresList = actasEvaluacionPreparatoriaService.getFirmantesProfesoradoPreparatorios(cAnno, 
																														 xCentro, 
																														 idUnidad, 
																														 idCurso, 
																														 sesion);
		
		List<ProfesorFirmanteDto> profesoresListDto = profesoresList.stream().map(entity -> modelMapper.map(entity, ProfesorFirmanteDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(profesoresListDto, HttpStatus.OK);	
	}
	
	
	
}
