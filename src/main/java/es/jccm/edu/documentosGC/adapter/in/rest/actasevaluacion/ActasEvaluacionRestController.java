package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.ActaEvaluacionListDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.ConvocatoriaCentroDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.ConvocatoriasCorrespondenciaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.CursoConvocatoriaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.DatosCentroActaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.DocumentoActaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.FechasConvocatoriaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.UnidadConvocatoriaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model.VistaPartesActasDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model.FirmantesPartesDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model.OtrosFirmantesDto;
import es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model.TlDocumentoDto;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ConvocatoriaUnidadListDto;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.EstadoDocumentoGCDto;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ActaEvaluacionList;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriaCentro;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriasCorrespondencia;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.CursoConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DatosCentroActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DocumentoActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FechasConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FirmantesPartes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.OtrosFirmantes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.TlDocumento;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.UnidadConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.VistaPartesActas;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaUnidadList;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacion.IActasEvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Actas de evaluacion", description = "Servicio con las operaciones sobre las actas de evaluacion documentosgc")
public class ActasEvaluacionRestController {
	
	@Autowired
	private IActasEvaluacionService actasEvaluacionService;
	
	@Autowired
	ModelMapper modelMapper;

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Datos convocatoria", description = "Este metodo devuelve la informacion completa de las convocatorias del centro", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvocatoriasCentro/{idCentro}/{cAnno}/{idTipo}")
	public ResponseEntity<List<ConvocatoriaCentroDto>> getConvocatoriasCentro(@PathVariable("idCentro") Long idCentro,
																			  @PathVariable("cAnno") Long cAnno,
																			  @PathVariable("idTipo") Long idTipo) {
		
		List<ConvocatoriaCentro> convocatorias =  actasEvaluacionService.getConvocatoriasCentro(idCentro,cAnno,idTipo);
		
		List<ConvocatoriaCentroDto> convocatoriasDto = convocatorias.stream().map(entity -> modelMapper.map(entity, ConvocatoriaCentroDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ConvocatoriaCentroDto>>(convocatoriasDto, HttpStatus.OK);		
	}

	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Cursos convocatoria", description = "Este metodo devuelve la informacion completa de los cursos afectados por la convocatoria enviada", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosConvocatoria/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}")
	public ResponseEntity<List<CursoConvocatoriaDto>> getCursosConvocatoria (@PathVariable("idCentro") Long idCentro,
																			 @PathVariable("cAnno") Long cAnno,
																			 @PathVariable("idTipo") Long idTipo,
																			 @PathVariable("idConvCentro") Long idConvCentro) {
		
		List<CursoConvocatoria> curso =  actasEvaluacionService.getCursosConvocatoria(idCentro,
																				      cAnno,
																				      idTipo,
																					  idConvCentro);
		
		List<CursoConvocatoriaDto> cursoDto = curso.stream().map(entity -> modelMapper.map(entity, CursoConvocatoriaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CursoConvocatoriaDto>>(cursoDto, HttpStatus.OK);		
	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Unidad convocatoria", description = "Este metodo devuelve la informacion completa de los grupos afectados por la convocatoria enviada", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesConvocatoria/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}")
	public ResponseEntity<List<UnidadConvocatoriaDto>> getUnidadesConvocatoria (@PathVariable("idCentro") Long idCentro,
																			 @PathVariable("cAnno") Long cAnno,
																			 @PathVariable("idTipo") Long idTipo,
																			 @PathVariable("idConvCentro") Long idConvCentro,
																			 @PathVariable("idOfertamatric") Long idOfertamatric) {
		
		List<UnidadConvocatoria> unidad =  actasEvaluacionService.getUnidadesConvocatoria(idCentro,
																				          cAnno,
																				          idTipo,
																					      idConvCentro,
																					      idOfertamatric);
		
		List<UnidadConvocatoriaDto> unidadDto = unidad.stream().map(entity -> modelMapper.map(entity, UnidadConvocatoriaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<UnidadConvocatoriaDto>>(unidadDto, HttpStatus.OK);

	}	
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Documentos actas cursos", description = "Este metodo devuelve la informacion completa de los documentos afectados por el curso enviado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDocumentosCurso/{idCentro}/{cAnno}/{idOfertamatric}")
	public ResponseEntity<List<DocumentoActaDto>> getDocumentosCurso (@PathVariable("idCentro") Long idCentro,
																      @PathVariable("cAnno") Long cAnno,
																	  @PathVariable("idOfertamatric") Long idOfertamatric) {
		
		List<DocumentoActa> unidad =  actasEvaluacionService.getDocumentosCurso(idCentro,
																				cAnno,	
																				idOfertamatric);
		
		List<DocumentoActaDto> unidadDto = unidad.stream().map(entity -> modelMapper.map(entity, DocumentoActaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentoActaDto>>(unidadDto, HttpStatus.OK);
	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Estados actas evalucion", description = "Este metodo devuelve los estados de las actas de evaluacion ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosActasEvaluacion/{idPerfil}/{cAnno}/{dsAbrevPadre}")
	public ResponseEntity<List<EstadoDocumentoGCDto>> getEstadosActasEvaluacion(@PathVariable("idPerfil") Long idPerfil,
																				@PathVariable("cAnno") Long cAnno,
																				@PathVariable("dsAbrevPadre") String dsAbrevPadre) {	
		
		List<EstadoDocumentoGC> estados =  actasEvaluacionService.getEstadosActasEvaluacion(idPerfil, cAnno, dsAbrevPadre);
		
		List<EstadoDocumentoGCDto> estadosDto = estados.stream()
						.map(entity -> modelMapper.map(entity, EstadoDocumentoGCDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<List<EstadoDocumentoGCDto>>(estadosDto, HttpStatus.OK);	
		
		
	}

    @PreAuthorize("hasAnyRole('C')")
	@Operation(summary = "Listado actas de evaluacion", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllActasEvaluacion/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}/{idUnidad}/{idEstado}/{idDocumento}/{idMateriac}/{idEtapa}/{xEmpleado}")
	public ResponseEntity<List<ActaEvaluacionListDto>> getAllActasEvaluacion(@PathVariable("idCentro") Long idCentro,
																			 @PathVariable("cAnno") Long cAnno,
																			 @PathVariable("idTipo") Long idTipo,
																			 @PathVariable("idConvCentro") Long idConvCentro,
																			 @PathVariable("idOfertamatric") Long idOfertamatric,
																			 @PathVariable("idUnidad") Long idUnidad,
																			 @PathVariable("idEstado") Long idEstado,
																			 @PathVariable("idDocumento") Long idDocumento,
																			 @PathVariable("idMateriac") Long idMateriac,
																			 @PathVariable("idEtapa") Long idEtapa,
																			 @PathVariable("xEmpleado") Long xEmpleado) {
			
			
		List<ActaEvaluacionList> actas =  actasEvaluacionService.getAllActasEvaluacion(idCentro,
															  						   cAnno,
																					   idTipo,
																					   idConvCentro,
																					   idOfertamatric,
																					   idUnidad,
																					   idEstado,
																					   idDocumento,
																					   idMateriac,
																					   idEtapa,
																					   xEmpleado);
			
			List<ActaEvaluacionListDto> actasDto = actas.stream()
							.map(entity -> modelMapper.map(entity, ActaEvaluacionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ActaEvaluacionListDto>>(actasDto, HttpStatus.OK);		
		}

    @PreAuthorize("hasAnyRole('INC')")
	@Operation(summary = "Listado actas de evaluacion inspector de centro", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion del inspector de centros", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllActasEvaluacionInspCentro/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}/{idUnidad}/{idEstado}/{idProvincia}/{idMunicipio}/{xEmpleado}/{fTomapos}/{idDocumento}")
	public ResponseEntity<List<ActaEvaluacionListDto>> getAllActasEvaluacionInspCentro(@PathVariable("idCentro") Long idCentro,
																					   @PathVariable("cAnno") Long cAnno,
																					   @PathVariable("idTipo") Long idTipo,
																					   @PathVariable("idConvCentro") Long idConvCentro,
																					   @PathVariable("idOfertamatric") Long idOfertamatric,
																					   @PathVariable("idUnidad") Long idUnidad,
																					   @PathVariable("idEstado") Long idEstado,
																					   @PathVariable("idProvincia") Long idProvincia,
																					   @PathVariable("idMunicipio") Long idMunicipio,
																					   @PathVariable("xEmpleado") Long xEmpleado,
																					   @PathVariable("fTomapos") String fTomapos,
																					   @PathVariable("idDocumento") Long idDocumento) {	
			
		List<ActaEvaluacionList> actas =  actasEvaluacionService.getAllActasEvaluacionInspCentro(idCentro,
																		  						 cAnno,
																								 idTipo,
																								 idConvCentro,
																								 idOfertamatric,
																								 idUnidad,
																								 idEstado,
																								 idProvincia, 
																								 idMunicipio,
																								 xEmpleado,
																								 fTomapos,
																								 idDocumento);
			
			List<ActaEvaluacionListDto> actasDto = actas.stream()
							.map(entity -> modelMapper.map(entity, ActaEvaluacionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ActaEvaluacionListDto>>(actasDto, HttpStatus.OK);		
		}

    @PreAuthorize("hasAnyRole('INZ')")
	@Operation(summary = "Listado actas de evaluacion inspector de zona", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion del inspector de zona", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllActasEvaluacionZona/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}/{idUnidad}/{idEstado}/{idPerfil}/{idProvincia}/{idMunicipio}/{idUsuario}/{idDocumento}")
	public ResponseEntity<List<ActaEvaluacionListDto>> getAllActasEvaluacionZona(@PathVariable("idCentro") Long idCentro,
																				 @PathVariable("cAnno") Long cAnno,
																				 @PathVariable("idTipo") Long idTipo,
																				 @PathVariable("idConvCentro") Long idConvCentro,
																				 @PathVariable("idOfertamatric") Long idOfertamatric,
																				 @PathVariable("idUnidad") Long idUnidad,
																				 @PathVariable("idEstado") Long idEstado,
																				 @PathVariable("idPerfil") Long idPerfil,
																				 @PathVariable("idProvincia") Long idProvincia,
																				 @PathVariable("idMunicipio") Long idMunicipio,
																				 @PathVariable("idUsuario") Long idUsuario,
																				 @PathVariable("idDocumento") Long idDocumento) {	
			
		List<ActaEvaluacionList> actas =  actasEvaluacionService.getAllActasEvaluacionZona(idCentro, 
																					   	   cAnno, 
																					   	   idTipo,
																					   	   idConvCentro, 
																					   	   idOfertamatric, 
																					   	   idUnidad, 
																					   	   idEstado,
																					   	   idPerfil,
																					   	   idProvincia,
																					   	   idMunicipio,
																					   	   idUsuario,
																					   	   idDocumento);
			
			List<ActaEvaluacionListDto> actasDto = actas.stream()
							.map(entity -> modelMapper.map(entity, ActaEvaluacionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ActaEvaluacionListDto>>(actasDto, HttpStatus.OK);		
		}

    @PreAuthorize("hasAnyRole('I')")
	@Operation(summary = "Listado actas de evaluacion inspector provincial", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion del inspector provincial", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllActasEvaluacionProvincial/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}/{idUnidad}/{idEstado}/{idProvincia}/{idMunicipio}/{idPerfil}/{idUsuario}/{idCentroProvincia}/{idDocumento}")
	public ResponseEntity<List<ActaEvaluacionListDto>> getAllActasEvaluacionProvincial(@PathVariable("idCentro") Long idCentro,
																					   @PathVariable("cAnno") Long cAnno,
																					   @PathVariable("idTipo") Long idTipo,
																					   @PathVariable("idConvCentro") Long idConvCentro,
																					   @PathVariable("idOfertamatric") Long idOfertamatric,
																					   @PathVariable("idUnidad") Long idUnidad,
																					   @PathVariable("idEstado") Long idEstado,
																					   @PathVariable("idProvincia") Long idProvincia,
																					   @PathVariable("idMunicipio") Long idMunicipio,
																					   @PathVariable("idPerfil") Long idPerfil,
																					   @PathVariable("idUsuario") Long idUsuario,
																					   @PathVariable("idCentroProvincia") Long idCentroProvincia,
																					   @PathVariable("idDocumento") Long idDocumento) {	
			
		List<ActaEvaluacionList> actas =  actasEvaluacionService.getAllActasEvaluacionProvincial(idCentro,
																								 cAnno,
																								 idTipo,
																								 idConvCentro,
																								 idOfertamatric,
																								 idUnidad,
																								 idEstado,
																								 idProvincia,
																								 idMunicipio,
																								 idPerfil,
																								 idUsuario,
																								 idCentroProvincia,
																								 idDocumento);
			
			List<ActaEvaluacionListDto> actasDto = actas.stream()
							.map(entity -> modelMapper.map(entity, ActaEvaluacionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ActaEvaluacionListDto>>(actasDto, HttpStatus.OK);		
		}

    @PreAuthorize("hasAnyRole('ICO')")
	@Operation(summary = "Listado actas de evaluacion inspector consejeria", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion del inspector consejeria", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllActasEvaluacionConsejeria/{idCentro}/{cAnno}/{idTipo}/{idConvCentro}/{idOfertamatric}/{idUnidad}/{idEstado}/{idProvincia}/{idMunicipio}/{idDocumento}")
	public ResponseEntity<List<ActaEvaluacionListDto>> getAllActasEvaluacionConsejeria(@PathVariable("idCentro") Long idCentro,
																					   @PathVariable("cAnno") Long cAnno,
																					   @PathVariable("idTipo") Long idTipo,
																					   @PathVariable("idConvCentro") Long idConvCentro,
																					   @PathVariable("idOfertamatric") Long idOfertamatric,
																					   @PathVariable("idUnidad") Long idUnidad,
																					   @PathVariable("idEstado") Long idEstado,
																					   @PathVariable("idProvincia") Long idProvincia,
																					   @PathVariable("idMunicipio") Long idMunicipio,
																					   @PathVariable("idDocumento") Long idDocumento) {	
			
		List<ActaEvaluacionList> actas =  actasEvaluacionService.getAllActasEvaluacionConsejeria(idCentro, 
																								 cAnno, 
																								 idTipo,
																								 idConvCentro, 
																								 idOfertamatric, 
																								 idUnidad, 
																								 idEstado,
																								 idProvincia,
																								 idMunicipio,
																								 idDocumento);
			
			List<ActaEvaluacionListDto> actasDto = actas.stream()
							.map(entity -> modelMapper.map(entity, ActaEvaluacionListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ActaEvaluacionListDto>>(actasDto, HttpStatus.OK);		
		}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Estados actas evalucion", description = "Este metodo devuelve los estados de las actas de evaluacion ", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvocatoriasEvaluacion/{idCentro}/{cAnno}/{idTipo}")
	public ResponseEntity<List<ConvocatoriaUnidadListDto>> getConvocatoriasEvaluacion(@PathVariable("idCentro") Long idCentro,
																					  @PathVariable("cAnno") Long cAnno,
																					  @PathVariable("idTipo") Long idTipo) {
		
		List<ConvocatoriaUnidadList> convocatoriaUnidad =  actasEvaluacionService.getConvocatoriasEvaluacion(idCentro, cAnno, idTipo);
		
		List<ConvocatoriaUnidadListDto> convocatoriaUnidadDto = convocatoriaUnidad.stream().map(entity -> modelMapper.map(entity, ConvocatoriaUnidadListDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(convocatoriaUnidadDto, HttpStatus.OK);	
	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Datos del centro", description = "Este metodo devuelve los datos del centro", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosCentroActa/{idCentro}/{cAnno}")
	public ResponseEntity<DatosCentroActaDto> getLocalidadCentro(@PathVariable("idCentro") Long idCentro,
													 			 @PathVariable("cAnno") Long cAnno) {
		
		DatosCentroActa datos =  actasEvaluacionService.getLocalidadCentro(idCentro,cAnno);
		
		DatosCentroActaDto datosDto = modelMapper.map(datos, DatosCentroActaDto.class);    
		
		return new ResponseEntity<DatosCentroActaDto>(datosDto, HttpStatus.OK);
		
		
		

	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Nombre Report", description = "Este metodo devuelve el nombre del report lanzado en delphos", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNombreReport/{idDocumento}")
	public ResponseEntity<String> getNombreReport(@PathVariable("idDocumento") Long idDocumento) {
		
		String nombreReport =  actasEvaluacionService.getNombreReport(idDocumento);
		
		return new ResponseEntity<>(nombreReport, HttpStatus.OK);	

	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Fechas convocatoria", description = "Este metodo devuelve las fechas de inicio fin de la convocatoria unidad", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechasConvocatoria/{idConvUnidad}/{idConvocatoria}/{idEtapa}")
	public ResponseEntity<FechasConvocatoriaDto> getFechasConvocatoria(@PathVariable("idConvUnidad") Long idConvUnidad,
																	   @PathVariable("idConvocatoria") Long idConvocatoria,
																	   @PathVariable("idEtapa") Long idEtapa) {
		
		FechasConvocatoria fecha =  actasEvaluacionService.getFechasConvocatoria(idConvUnidad,idConvocatoria,idEtapa);
		
		FechasConvocatoriaDto fechaDto = modelMapper.map(fecha, FechasConvocatoriaDto.class);    
		
		return new ResponseEntity<FechasConvocatoriaDto>(fechaDto, HttpStatus.OK);

	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Tldocumentos", description = "Este metodo devuelve los documentos", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDocumentosByIdCentroAndCAnnoAndIdConvunidad/{idCentro}/{cAnno}/{idConvunidad}")
	public ResponseEntity<List<TlDocumentoDto>> getDocumentosByIdCentroAndCAnnoAndIdConvunidad(@PathVariable("idCentro") Long idCentro, @PathVariable("cAnno") Integer cAnno
			                                                                                 , @PathVariable("idConvunidad") Long idConvunidad) {
		
		List<TlDocumento> tldocumentos = actasEvaluacionService.getDocumentosByIdCentroAndCAnnoAndIdConvunidad(idCentro, cAnno, idConvunidad);
		
		List<TlDocumentoDto> tldocumentosDto = tldocumentos.stream().map(entity -> modelMapper.map(entity, TlDocumentoDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(tldocumentosDto, HttpStatus.OK);	
	}	
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Acta generada", description = "Este metodo un valos numerico mayor que 0 si el alta que se pretende crear ya ha sido generado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getActaEvaluacionGenerada/{cAnno}/{idCentro}/{idTipoDocumento}/{idConvUnidad}/{idDocumento}/{idCurso}/{idMateria}/{idConvCentro}")
	public ResponseEntity<Integer> getActaEvaluacionGenerada(@PathVariable("cAnno") Integer cAnno, 
															 @PathVariable("idCentro") Long idCentro,
															 @PathVariable("idTipoDocumento") Long idTipoDocumento,
															 @PathVariable("idConvUnidad") Long idConvUnidad,
															 @PathVariable("idDocumento") Long idDocumento,
															 @PathVariable("idCurso") Long idCurso,
															 @PathVariable("idMateria") Long idMateria,
															 @PathVariable("idConvCentro") Long idConvCentro) {
		
		return new ResponseEntity<Integer>(actasEvaluacionService.getActaEvaluacionGenerada(cAnno, 
																						    idCentro,
																						    idTipoDocumento,
																						    idConvUnidad,
																						    idDocumento,
																						    idCurso,
																						    idMateria,
																						    idConvCentro), HttpStatus.OK);
		
	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Otros firmantes", description = "Este metodo devuelve los firmantes director y tutor en la generacion de actas", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getOtrosFirmantes/{idCentro}/{idUnidad}/{dia}/{mes}/{anno}/{idDirector}/{annoAca}")
	public ResponseEntity<List<OtrosFirmantesDto>> getOtrosFirmantes(@PathVariable("idCentro") Long idCentro,
			                                                         @PathVariable("idUnidad") Long idUnidad, 
																	 @PathVariable("dia") Integer dia,
																	 @PathVariable("mes") String mes,
																	 @PathVariable("anno") Integer anno,
																	 @PathVariable("idDirector") Long idDirector,
																	 @PathVariable("annoAca") Integer annoAca) {		
		
		List<OtrosFirmantes> otros = actasEvaluacionService.getOtrosFirmantes(idCentro, idUnidad, dia, mes, anno, idDirector, annoAca);
		
		List<OtrosFirmantesDto> otrosDto = otros.stream().map(entity -> modelMapper.map(entity, OtrosFirmantesDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(otrosDto, HttpStatus.OK);	
	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Otros firmantes", description = "Este metodo devuelve los firmantes director y tutor en la generacion de actas", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getOtrosFirmantesNoFormales/{idCentro}/{idUnidad}/{idDirector}/{annoAca}")
	public ResponseEntity<List<OtrosFirmantesDto>> getOtrosFirmantesNoFormales(@PathVariable("idCentro") Long idCentro,
			                                                         @PathVariable("idUnidad") Long idUnidad,
																	 @PathVariable("idDirector") Long idDirector,
																	 @PathVariable("annoAca") Integer annoAca) {		
		
		List<OtrosFirmantes> otros = actasEvaluacionService.getOtrosFirmantesNoFormales(idCentro, idUnidad, idDirector, annoAca);
		
		List<OtrosFirmantesDto> otrosDto = otros.stream().map(entity -> modelMapper.map(entity, OtrosFirmantesDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(otrosDto, HttpStatus.OK);	
	}


    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Fechas convocatoria", description = "Este metodo devuelve las fechas de inicio fin de la convocatoria por curso", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFechasConvocatoriaCurso/{idConvCentro}/{idOfertamatrig}/{idCentro}")
	public ResponseEntity<FechasConvocatoriaDto> getFechasConvocatoriaCurso(@PathVariable("idConvCentro") Long idConvCentro,
																	        @PathVariable("idOfertamatrig") Long idOfertamatrig,
																	        @PathVariable("idCentro") Long idCentro) {
		
		FechasConvocatoria fecha =  actasEvaluacionService.getFechasConvocatoriaCurso(idConvCentro,
																				      idOfertamatrig,
																				      idCentro);
		
		FechasConvocatoriaDto fechaDto = modelMapper.map(fecha, FechasConvocatoriaDto.class);    
		
		return new ResponseEntity<FechasConvocatoriaDto>(fechaDto, HttpStatus.OK);

	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Listado convocatorias extra", description = "Este metodo devuelve la lista de convocatorias extraordinarias", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvocatoriasCorrespondencia/{idCentro}/{cAnno}/{idOfertamatrig}/{idConvUnidad}")
	public ResponseEntity<List<ConvocatoriasCorrespondenciaDto>> getConvocatoriasCorrespondencia(@PathVariable("idCentro") Long idCentro,
																							    @PathVariable("cAnno") Long cAnno,
																							    @PathVariable("idOfertamatrig") Long idOfertamatrig,
																							    @PathVariable("idConvUnidad") Long idConvUnidad
																							    ) {
		
		List<ConvocatoriasCorrespondencia> convocatorias = actasEvaluacionService.getConvocatoriasCorrespondencia(idCentro,cAnno,idOfertamatrig, idConvUnidad);
		
		List<ConvocatoriasCorrespondenciaDto> convocatoriasDto = convocatorias.stream().map(entity -> modelMapper.map(entity, ConvocatoriasCorrespondenciaDto.class))
														 .collect(Collectors.toList());		
		
		return new ResponseEntity<>(convocatoriasDto, HttpStatus.OK);	
	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Materias curso", description = "Este metodo devuelve la informacion completa de las materias asociadas a un curso", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMateriasCurso/{idOfertamatric}/{tipo}")
	public ResponseEntity<List<DocumentoActaDto>> getMateriasCurso (@PathVariable("idOfertamatric") Long idOfertamatric,
																	@PathVariable("tipo") String tipo) {
		
		List<DocumentoActa> unidad =  actasEvaluacionService.getMateriasCurso(idOfertamatric,tipo);
		
		List<DocumentoActaDto> unidadDto = unidad.stream().map(entity -> modelMapper.map(entity, DocumentoActaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentoActaDto>>(unidadDto, HttpStatus.OK);
	}

    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Documento curso", description = "Este metodo devuelve si un centro oferta cursos segun el acta dado", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNumOfertasDocumento/{idCentro}/{cAnno}/{idDocumento}")
	public ResponseEntity<Integer> getNumOfertasDocumento(@PathVariable("idCentro") Long idCentro,
													  @PathVariable("cAnno") Long cAnno,
													  @PathVariable("idDocumento") Long idDocumento
			) {
		
		Integer etapa = actasEvaluacionService.getNumOfertasDocumento(idCentro,cAnno,idDocumento);	
		
		return new ResponseEntity<>(etapa, HttpStatus.OK);	
	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Etapas actas", description = "Este metodo devuelve la informacion completa de las etapas asociadas a un acta", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEtapasActas/{idCentro}/{cAnno}/{idDocumento}/{idCurso}")
	public ResponseEntity<List<DocumentoActaDto>> getEtapasActas (@PathVariable("idCentro") Long idCentro,
																  @PathVariable("cAnno") Long cAnno,
																  @PathVariable("idDocumento") Long idDocumento,
																  @PathVariable("idCurso") Long idCurso) {
		
		List<DocumentoActa> unidad =  actasEvaluacionService.getEtapasActas(idCentro,
																	   	    cAnno,
																			idDocumento,
																			idCurso);
		
		List<DocumentoActaDto> unidadDto = unidad.stream().map(entity -> modelMapper.map(entity, DocumentoActaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<DocumentoActaDto>>(unidadDto, HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Centro vista Partes y Actas", description = "Este metodo devuelve si el centro debe mostrar las tarjetas de partes y y actas en el inicio", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCentroVistaPartesActas/{idCentro}")
	public ResponseEntity<VistaPartesActasDto> getCentroVistaPartesActas(@PathVariable("idCentro") Long idCentro) {
		
		VistaPartesActas vista =  actasEvaluacionService.getCentroVistaPartesActas(idCentro);
		
		VistaPartesActasDto vistaDto = modelMapper.map(vista, VistaPartesActasDto.class);    
		
		return new ResponseEntity<VistaPartesActasDto>(vistaDto, HttpStatus.OK);

	}
	
    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO')")
	@Operation(summary = "Firmantes partes", description = "Este metodo devuelve los firmantes de los partes mensuales", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFirmantesPartes/{idCentro}")
	public ResponseEntity<List<FirmantesPartesDto>> getFirmantesPartes(@PathVariable("idCentro") Long idCentro) {		
		
		List<FirmantesPartes> otros = actasEvaluacionService.getFirmantesPartes(idCentro);
		
		List<FirmantesPartesDto> otrosDto = otros.stream().map(entity -> modelMapper.map(entity, FirmantesPartesDto.class)).collect(Collectors.toList());		
		
		return new ResponseEntity<>(otrosDto, HttpStatus.OK);	
	}
		
}
	
	
	
	
	
	
	
	
	
	
	


