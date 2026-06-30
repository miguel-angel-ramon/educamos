package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionnoformales;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionnoformales.model.DirectorTutorDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionnoformales.model.RegSelDocNoFormalesDto;
import es.jccm.edu.documentosGC.application.domain.actaevaluacionesnoformales.entities.DirectorTutor;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosnoformales.RegSelDocNoFormales;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacionnoformales.IActasEvaluacionNoFormalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Actas de evaluacion EAS", description = "Servicio con las operaciones sobre las actas de evaluacion enseñanzas no formales documentosgc")
public class ActasEvaluacionNoFormalesRestController {
	
	@Autowired
	private IActasEvaluacionNoFormalesService actasEvaluacionNoFormalesService;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	@PostMapping("/createRegistrosRegSolDocNoFormales/{idUnidad}/{localidad}/{idConvOmc}/{idDirector}/{idTutor}")
	public ResponseEntity<Object> createRegistrosRegSolDoc(@PathVariable("idUnidad") Long idUnidad,
														   @PathVariable("localidad") String localidad,
														   @PathVariable("idConvOmc") Long idConvOmc,
														   @PathVariable("idDirector") Long idDirector,
														   @PathVariable("idTutor") Long idTutor) {
		
		ResponseEntity<Object> response = null;  
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);			
						
			List<RegSelDocNoFormales> regSelDocListIn = actasEvaluacionNoFormalesService.createRegistrosRegSolDoc(idUnidad,
																												  localidad,
																												  idConvOmc,
																												  idDirector,
																												  idTutor);
			
			List<RegSelDocNoFormalesDto> regSelDocListOut = regSelDocListIn.stream().map(entity -> modelMapper.map(entity, RegSelDocNoFormalesDto.class)).collect(Collectors.toList());		
		
			response = new ResponseEntity<>(regSelDocListOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Director/Tutor", description = "Este metodo devuelve los identificadores del director y tutor de un centro", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDirectorTutor/{idConvOmc}/{idUnidad}")
	public ResponseEntity<DirectorTutorDto> getDirectorTutor(@PathVariable("idConvOmc") Long idConvOmc,
															 @PathVariable("idUnidad") Long idUnidad) {
		
		DirectorTutor directores = actasEvaluacionNoFormalesService.getDirectorTutor(idConvOmc,idUnidad);
		
		DirectorTutorDto tldocumentosDto = modelMapper.map(directores, DirectorTutorDto.class);		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}
	
	
	/**
	 * Devuelve el identificador tlmatofematrg.x_materiaomg
	 * 
	 *
	 * @param idMateriac
	 * @param idOfertamatrig
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "X_MATERIAOMG", description = "Este metodo devuelve el identificador x_materiaomg", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMateriaOmg/{idMateriac}/{idOfertamatrig}")
	public ResponseEntity<Long> getMateriasCurso(@PathVariable("idMateriac") Long idMateriac,
			 									 @PathVariable("idOfertamatrig") Long idOfertamatrig) 
	{	
		
		return new ResponseEntity<>(actasEvaluacionNoFormalesService.getMateriasCurso(idMateriac, idOfertamatrig), HttpStatus.OK);
	}


}
