package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionFlujoSiguienteDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.HistoricoFlujoAutorizacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.ListadoAutorizacionDesplazamientoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.ListadoAutorizacionesAnexosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamientoHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.ports.in.desplazamiento.IDesplazamientoService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Gastos FCT", description = "Servicio con las operaciones sobre Gastos FCT")
public class DesplazamientoRestController {
	
	private static final String NO_CACHE = "no-cache";
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	IDesplazamientoService desplazamientoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Creacion de una autorizacion del desplazamiento de vehiculo propio.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Creación de la autorizacion del desplazamiento", description = "Este metodo crea la autorizacion del desplazamiento", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createAutorizacionDesplazamiento/{idPerfil}")
	public ResponseEntity<Object> createAutorizacionDesplazamiento(@RequestBody final List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto,
			 												       @PathVariable("idPerfil") Long idPerfil,
			 												       @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<AutorizacionDesplazamiento> listAutorizacionDesplazamientoIn = 
					desplazamientoService.createAutorizacionDesplazamiento(listAutorizacionDesplazamientoDto, idPerfil, datosUsuario.getXUsuarioDelphos());
			
			List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoOut = listAutorizacionDesplazamientoIn.stream().map(x->modelMapper.map(x, AutorizacionDesplazamientoDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(listAutorizacionDesplazamientoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creacion siguiente estado flujo historial.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Creación siguiente estado flujo historial", description = "Este metodo crea el siguiente estado flujo historial", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createSiguienteEstadoFlujoHistorial/{idPerfil}/{abrev}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoHistorial(@RequestBody final AutorizacionDesplazamientoHistorialDto autorizacionDesplazamientoDto,
																	  @PathVariable("idPerfil") Long idPerfil,
																	  @PathVariable("abrev") String abrev,
																	  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			AutorizacionDesplazamientoHistorial historialIn = desplazamientoService.createSiguienteEstadoFlujoHistorial(autorizacionDesplazamientoDto,																												
																												        idPerfil, 
																												        abrev, 
																												        datosUsuario.getXUsuarioDelphos() );
			
			AutorizacionDesplazamientoHistorialDto historialOut = modelMapper.map(historialIn, AutorizacionDesplazamientoHistorialDto.class);
			
			return new ResponseEntity<>(historialOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Upload fichero para la autorizacion del desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Upload fichero autorizacion desplazamiento", description = "Upload fichero autorizacion desplazamiento", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadFicheroAutorizacionDesplazamiento/{idAutDes}/{idCentro}/{idRodal}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> uploadFicheroAutorizacionDesplazamiento(@PathVariable("idAutDes") Long idAutDes,
																		  @PathVariable("idCentro") Long idCentro,
																		  @PathVariable("idRodal") String idRodal,
																		  @RequestPart List<MultipartFile> files,																		  
																		  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			desplazamientoService.uploadFicheroAutorizacionDesplazamiento(idAutDes, idCentro, idRodal, files, datosUsuario.getXUsuarioDelphos());
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete ticket de un gasto del tutor.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU')")
	@Operation(summary = "Delete fichero autorizacion desplazamiento", description = "Este metodo borra el fichero autorizacion desplazamiento", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteFicheroAutorizacionDesplazamiento")
	public ResponseEntity<Object> deleteTicketTutor(
			@Parameter(description = "Documento para subir a Rodal", required = true) @RequestBody final List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			desplazamientoService.deleteFicheroAutorizacionDesplazamiento(listAutorizacionDesplazamientoDto);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/** 
	 * Get autorizacion desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get autorizacion desplazamiento", description = "Este metodo devuelve la autorizacion desplazamiento",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAutorizacionDesplazamiento/{idAutDes}")
	public ResponseEntity<AutorizacionDesplazamientoDto> getAutorizacionDesplazamiento(@PathVariable("idAutDes") Long idAutDes){
		
		AutorizacionDesplazamiento autorizacionDesplazamientoIn =  desplazamientoService.getAutorizacionDesplazamiento(idAutDes);
		
		AutorizacionDesplazamientoDto autorizacionDesplazamientoOut = modelMapper.map(autorizacionDesplazamientoIn, AutorizacionDesplazamientoDto.class);
				
		return new ResponseEntity<>(autorizacionDesplazamientoOut, HttpStatus.OK);
	}
	
	/**
	 * Get siguiente estado flujo autorizacion.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get flujo autorizacion desplazamiento", description = "Este metodo devuelve el flujo autorizacion desplazamiento",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSiguienteAutorizacionFlujo/{idPerfil}/{idAut}/{abrev}")
	public ResponseEntity<List<AutorizacionFlujoSiguienteDto>> getSiguienteFlujoAutorizacionDesplazamiento(@PathVariable("idPerfil") Long idPerfil,
																						    @PathVariable("idAut") Long idAut,
																							@PathVariable("abrev") String abrev){		

		
		List<AutorizacionFlujoSiguiente> listadoIn = desplazamientoService.getSiguienteFlujoAutorizacionDesplazamiento(idPerfil, idAut, abrev);
		
		List<AutorizacionFlujoSiguienteDto> listadoOut = listadoIn.stream().map(x -> modelMapper.map(x, AutorizacionFlujoSiguienteDto.class)).collect(Collectors.toList());			
		
		return new ResponseEntity<>(listadoOut, HttpStatus.OK);
		
		
		/*AutorizacionFlujo flujoIn =  desplazamientoService.getSiguienteFlujoAutorizacionDesplazamiento(idPerfil, idAut, abrev);
		
		AutorizacionFlujoDto flujOut = modelMapper.map(flujoIn, AutorizacionFlujoDto.class);
				
		return new ResponseEntity<>(flujOut, HttpStatus.OK); */
		
		
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Esta emancipado", description = "Este metodo devuelve si esta emancipadoa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmancipado/{idMatricula}")
	public ResponseEntity<Integer> getEstaEmancipado(@PathVariable("idMatricula") Long idMatricula){	
		
		Integer emancipado = desplazamientoService.getEmancipado(idMatricula);			
				
		return new ResponseEntity<Integer>(emancipado, HttpStatus.OK);
	}
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista tutores matricula", description = "Este metodo devuelve una lista de tutores por matricula",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTutoresMatricula/{idMatricula}")
	public ResponseEntity<List<Integer>> getTutoresMatricula(@PathVariable("idMatricula") Long idMatricula){		
	
		List<Integer> tutores =  desplazamientoService.getTutoresMatricula(idMatricula);			
				
		return new ResponseEntity<List<Integer>>(tutores, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Get valores de los combos del listado autorizaciones desplazamiento", 
	           description = "Este metodo devuelve los valores de los combos del listado autorizaciones desplazamiento", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCombosAutorizacionesDesplazamiento/{cbName}/{anno}/{idPeriodo}/{idCentro}/{idTutor}/{idFamilia}/{idCurso}/{idPerfil}")
	public ResponseEntity<Object> getCombosAutorizacionesDesplazamiento(@PathVariable("cbName") String cbName,
																		@PathVariable("anno") Integer anno,
																		@PathVariable("idPeriodo") Long idPeriodo,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idTutor") Long idTutor,
																		@PathVariable("idFamilia") Long idFamilia,
																		@PathVariable("idCurso") Long idCurso,
																		@PathVariable("idPerfil") Long idPerfil,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt); 
		
		try {
			List<ElementoSelect> elementIn = desplazamientoService.getCombosAutorizacionesDesplazamiento(cbName, anno, idPeriodo, idCentro, idTutor, 
																										 idFamilia, idCurso, idPerfil, datosUsuario.getXUsuarioDelphos());
			
			List<ElementoSelectDto> elementOut = elementIn.stream().map(x->modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(elementOut, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Get anexo XII.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get anexo XII", description = "Este metodo devuelve el anexo XII",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnexosXII/{idTutor}/{idMatricula}/{idAutDes}")
		public void getAnexosXII(HttpServletResponse response,
				                 @PathVariable("idTutor") Long idTutor,
			                     @PathVariable("idMatricula") Long idMatricula,
			                     @PathVariable("idAutDes") Long idAutDes) throws JRException, IOException {		
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoXII" +"."+ "pdf");
	    response.setHeader("Pragma", NO_CACHE);
	    response.setHeader("Cache-Control", NO_CACHE);
	    response.setStatus(200);
		
	    byte[] anexo = desplazamientoService.getAnexosXII(idTutor,idMatricula,idAutDes);		
	    
	    InputStream is = new ByteArrayInputStream(anexo);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();	
	}
	
	    
    /**
	 * Get listado autorizaciones desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get listado autorizaciones desplazamiento", description = "Este metodo devuelve el listado autorizaciones desplazamiento",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoAutorizacionesDesplazamiento/{anno}/{idPeriodo}/{idCentro}/{idTutor}/{idFamilia}/{idCurso}/{idUnidad}/{idPerfil}")
	public ResponseEntity<List<ListadoAutorizacionDesplazamientoDto>> getListadoAutorizacionesDesplazamiento(@PathVariable("anno") Integer anno,
																		 @PathVariable("idPeriodo") Long idPeriodo,
																		 @PathVariable("idCentro") Long idCentro,
																		 @PathVariable("idTutor") Long idTutor,
																		 @PathVariable("idFamilia") Long idFamilia,
																		 @PathVariable("idCurso") Long idCurso,
																		 @PathVariable("idUnidad") Long idUnidad,
																		 @PathVariable("idPerfil") Long idPerfil,
																		 @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		    DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
			List<ListadoAutorizacionDesplazamiento> listadoIn = desplazamientoService.getListadoAutorizacionesDesplazamiento(anno, idPeriodo, idCentro, idTutor, idFamilia, idCurso, idUnidad, idPerfil, datosUsuario.getXUsuarioDelphos());
			
			List<ListadoAutorizacionDesplazamientoDto> listadoOut = listadoIn.stream().map(x -> modelMapper.map(x, ListadoAutorizacionDesplazamientoDto.class)).collect(Collectors.toList());			
			
			return new ResponseEntity<>(listadoOut, HttpStatus.OK);
	}
	
    /**
	 * Get listado autorizaciones desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get listado autorizaciones anexo", description = "Este metodo devuelve el listado autorizaciones anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoAutorizacionesAnexos/{anno}/{abrev}/{idPeriodo}/{idCentro}/{idTutor}/{idFamilia}/{idCurso}/{idUnidad}/{idPerfil}/{idEstado}/{nuPeticion}")
	public ResponseEntity<List<ListadoAutorizacionesAnexosDto>> getListadoAutorizacionesAnexos(@PathVariable("anno") Integer anno,
																							   @PathVariable("abrev") String abrev,
																							   @PathVariable("idPeriodo") Long idPeriodo,
																							   @PathVariable("idCentro") Long idCentro,
																							   @PathVariable("idTutor") Long idTutor,
																							   @PathVariable("idFamilia") Long idFamilia,
																							   @PathVariable("idCurso") Long idCurso,
																							   @PathVariable("idUnidad") Long idUnidad,
																							   @PathVariable("idPerfil") Long idPerfil,
																							   @PathVariable("idEstado") Long idEstado,
																							   @PathVariable("nuPeticion") Integer nuPeticion,
																							   @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		    DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
			List<ListadoAutorizacionesAnexos> listadoIn = desplazamientoService.getListadoAutorizacionesAnexos(anno, 
																											   abrev,					
																											   idPeriodo, 
																											   idCentro, 
																											   idTutor, 
																											   idFamilia, 
																											   idCurso, 
																											   idUnidad,
																											   datosUsuario.getXUsuarioDelphos(),
																											   idPerfil,
																											   idEstado,
					                                                                                           nuPeticion);
			
			List<ListadoAutorizacionesAnexosDto> listadoOut = listadoIn.stream().map(x -> modelMapper.map(x, ListadoAutorizacionesAnexosDto.class)).collect(Collectors.toList());			
			
			return new ResponseEntity<>(listadoOut, HttpStatus.OK);
	}
	
    /**
	 * Get listado autorizaciones desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get listado autorizaciones anexo", description = "Este metodo devuelve el listado autorizaciones anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") }) 
	@GetMapping("/getCountAnexosPendientesFirmaDirector/{cAnno}/{abrev}/{idCentro}/{idTutor}/{idCurso}/{idUnidad}")
	public ResponseEntity<List<String>> getCountAnexosPendientesFirmaDirector(@PathVariable("cAnno") Integer cAnno,
																	     @PathVariable("abrev") String abrev,
																	     @PathVariable("idCentro") Long idCentro,
																		 @PathVariable("idTutor") Long idTutor,																		 
																		 @PathVariable("idCurso") Long idCurso,
																		 @PathVariable("idUnidad") Long idUnidad){  
		
		  List<String> tutores = desplazamientoService.getCountAnexosPendientesFirmaDirector(cAnno, 
																					         abrev,							
																		 			         idCentro, 
																					         idTutor,																					    
																					         idCurso, 
																					         idUnidad);
			
						
			
			return new ResponseEntity<>(tutores, HttpStatus.OK);
	}
	
	/**
	 * Historico gastos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista histórico gasto", description = "Este metodo devuelve una Lista de historico de un gasto tutor/alumnado y anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getHistoricoFlujoAutorizacion/{id}/{abrev}")
	public ResponseEntity<List<HistoricoFlujoAutorizacionDto>> getHistoricoFlujoAutorizacion(@PathVariable("id") Long id,
																		                     @PathVariable("abrev") String abrev){
		
		List<HistoricoFlujoAutorizacion> historicoIn =  desplazamientoService.getHistoricoFlujo(id, abrev);
		
		List<HistoricoFlujoAutorizacionDto> historicoOut = historicoIn.stream().map(x -> modelMapper.map(x, HistoricoFlujoAutorizacionDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(historicoOut, HttpStatus.OK);
	}
	
	/**
	 * Generar Anexo VIII.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Generar Anexo VII", description = "Este metodo generar Anexo VII", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") }) 
	@PostMapping("/generarAnexosVII/{idPerfil}/{idCentro}/{idPeriodoGasto}/{abrev}/{annoAnexo}/{idTutor}/{idFamilia}/{idCurso}/{idUnidad}")
	public ResponseEntity<Object> generarAnexos(
												@RequestHeader(Constants.AUTHORIZATION) String jwt,
												@PathVariable("idPerfil") Long idPerfil,
												@PathVariable("idCentro") Long idCentro,
												@PathVariable("idPeriodoGasto") Long idPeriodoGasto,
												@PathVariable("abrev") String abrev,
												@PathVariable("annoAnexo") Integer annoAnexo,
												@PathVariable("idTutor") Long idTutor,
												@PathVariable("idFamilia") Long idFamilia,
												@PathVariable("idCurso") Long idCurso,
												@PathVariable("idUnidad") Long idUnidad) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			desplazamientoService.generarAnexosVII(idPerfil, 
												   idCentro, 
												   datosUsuario.getXUsuarioDelphos(), 
												   idPeriodoGasto, 
												   abrev, 
												   annoAnexo,
												   idTutor,
												   idFamilia,
												   idCurso,
												   idUnidad);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Datos alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Datos alumno", description = "Este metodo devuelve el Nombre tutor",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosAlumnoGastoNuevo/{idMatricula}")
	public ResponseEntity<AlumnoDto> getDatosAlumnoGastoNuevo(@PathVariable("idMatricula") Long idMatricula){
		
		Alumno alumno =  desplazamientoService.getDatosAlumno(idMatricula);
				
		return new ResponseEntity<>(modelMapper.map(alumno, AlumnoDto.class), HttpStatus.OK);
	}	
	
	/**
	 * Numero de dias de los periodos del alumnado.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Numero de dias de los periodos del alumnado", description = "Este metodo devuelve el numero de dias de los periodos del alumnado",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNumeroDiasPeriodoAlumno/{idMatricula}")
	public ResponseEntity<Integer> getNumeroDiasPeriodoAlumno(@PathVariable("idMatricula") Long idMatricula){
		
		Integer dias =  desplazamientoService.getNumeroDiasPeriodoAlumno(idMatricula);
				
		return new ResponseEntity<>(dias, HttpStatus.OK);
	}	
	
	/**
	 * Actualización firma
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT')")
	@Operation(summary = "Actualización fecha firma", description = "Nos permite actualizar la fecha de firma y el xUsuario de los anexos, posiciones permitidas 1 y 2",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateFirmaAnexoAutorizacion/{idHistorial}/{idPerfil}/{abrev}")
	public ResponseEntity<Boolean> updateFirmaAnexo(@PathVariable("idHistorial") Long idHistorial,
													@PathVariable("idPerfil") Long idPerfil,
													@PathVariable("abrev") String abrev,
													@RequestHeader(Constants.AUTHORIZATION) String jwt){
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);			
			
			desplazamientoService.updateFirmaAnexo(idHistorial, 
												   datosUsuario.getXUsuarioDelphos(),		
												   idPerfil,
												   abrev);
			
			return new ResponseEntity<>(true, HttpStatus.OK);

		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/** 
	 * Get autorizacion desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get detalle anexo", description = "Este metodo devuelve detalle anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDetalleAnexo/{idAnexo}")
	public ResponseEntity<AutorizacionesAnexosDto> getDetalleAnexo(@PathVariable("idAnexo") Long idAnexo){	
		
		AutorizacionesAnexos autorizacionIn =  desplazamientoService.getDetalleAnexo(idAnexo);
		
		AutorizacionesAnexosDto autorizacionDesplazamientoOut = modelMapper.map(autorizacionIn, AutorizacionesAnexosDto.class);
				
		return new ResponseEntity<>(autorizacionDesplazamientoOut, HttpStatus.OK);
	}
	
	/**
	 * Creacion siguiente estado flujo historial.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Creación siguiente estado flujo historial", description = "Este metodo crea el siguiente estado flujo historial", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createSiguienteEstadoFlujoHistorialAnexo/{idPerfil}/{abrev}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoHistorialAnexo(@RequestBody final AutorizacionesAnexosHistorialDto historialAnexoDto,
																	  @PathVariable("idPerfil") Long idPerfil,
																	  @PathVariable("abrev") String abrev,
																	  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			AutorizacionesAnexosHistorial historialIn = desplazamientoService.createSiguienteEstadoFlujoHistorialAnexo(historialAnexoDto,																												
																												       idPerfil, 
																												       abrev, 
																												       datosUsuario.getXUsuarioDelphos() );
			
			AutorizacionesAnexosHistorialDto historialOut = modelMapper.map(historialIn, AutorizacionesAnexosHistorialDto.class);
			
			return new ResponseEntity<>(historialOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/**
	 * Borrado de la autorización de desplazamiento
	 * @param idAutDes
	 * @return
	 */
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Borrado de la autorizacion de desplazamiento", description = "Este metodo borra los datos de autorizacion de desplazamiento",
	responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteAutorizacionDesplazamiento/{idAutDes}")
	public ResponseEntity<HttpStatus> deleteAutorizacionDesplazamiento(
			@Parameter(description = "Identificador del Convenio", required = true) @PathVariable("idAutDes") Long idAutDes) {
		try {
			desplazamientoService.deleteAutorizacionDesplazamiento(idAutDes);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/** 
	 * Get autorizacion desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get autorizacion anexo", description = "Este metodo devuelve la autorizacion anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAutorizacionAnexoHistorial/{id}/{per}")
	public ResponseEntity<AutorizacionesAnexosHistorialDto> getAutorizacionAnexoHistorial(@PathVariable("id") Long id,
			                                                                              @PathVariable("per") Integer per){
		
		AutorizacionesAnexosHistorial anexoIn =  desplazamientoService.getAutorizacionAnexoHistorial(id,per);
		
		AutorizacionesAnexosHistorialDto anexoOut = modelMapper.map(anexoIn, AutorizacionesAnexosHistorialDto.class);
				
		return new ResponseEntity<>(anexoOut, HttpStatus.OK);
	}

	/**
	 * Get listado centros anexo XI firmados
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get listado centros anexo XI firmados", description = "Este metodo devuelve el listado de centros con la autorización de AnexoXI firmadas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoCentrosAnexoXIFirmado/{anno}/{abrev}/{idCentro}/{idPerfil}")
	public ResponseEntity<List<ElementoSelectDto>> getListadoCentrosAnexoXIFirmado(@PathVariable("anno") Integer anno,
																				   @PathVariable("abrev") String abrev,
																				   @PathVariable("idCentro") Long idCentro,
																				   @PathVariable("idPerfil") Long idPerfil,
																				   @RequestHeader(Constants.AUTHORIZATION) String jwt){

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ElementoSelect> centros = desplazamientoService.getListadoCentrosAnexoXIFirmado(anno,
				abrev,
				idCentro,
				datosUsuario.getXUsuarioDelphos(),
				idPerfil);

		List<ElementoSelectDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<ElementoSelectDto>>(centrosDto, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Obtener el estado del anexo de la autorización",
			description = "Este endpoint devuelve el estado del anexo asignado a una autorización desplazamiento/extraordinaria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getEstadoAnexoAutorizacion/{anno}/{idCentro}/{idTutor}/{idUnidad}/{idCurso}/{idTipo}/{idPeriodo}/{nuPeticion}")
    public ResponseEntity<String> getEstadoAnexoAutorizacion( @PathVariable("anno") Integer anno,
                                                                 @PathVariable("idCentro") Long idCentro,
                                                                 @PathVariable("idTutor") Long idTutor,
                                                                 @PathVariable("idUnidad") Long idUnidad,
                                                                 @PathVariable("idCurso") Long idCurso,
                                                                 @PathVariable("idTipo") Long idTipo,
															     @PathVariable("idPeriodo") Long idPeriodo,
															     @PathVariable("nuPeticion") Integer nuPeticion){
        String estado = desplazamientoService.getEstadoAnexoAutorizacion(anno, idCentro, idTutor, idUnidad, idCurso, idTipo, idPeriodo, nuPeticion);
        return new ResponseEntity<String>(estado, HttpStatus.OK);
    }


}
