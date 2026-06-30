package es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AnexoExtraordinarioAuxDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.ListadoAutorizacionExtraordinarioDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoAux;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinarioHistorial;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.ListadoAutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.ports.in.extraordinario.IExtraordinarioService;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio autorizaciones extraordinario fuera provincia", description = "Servicio de las autorizaciones extraordinarias")
public class ExtraordinarioRestController {

	private static final String NO_CACHE = "no-cache";
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	IExtraordinarioService extraordinarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	IParamsFCTService iParamsFCTService;
	
	//TODO Jaime
	/**
	 * Creacion de una autorizacion del desplazamiento de vehiculo propio.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO', 'FCT')")
	@Operation(summary = "Creación de la autorizacion del desplazamiento", description = "Este metodo crea la autorizacion del desplazamiento", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createAlumnoExtraordinario/{idPerfil}")
	public ResponseEntity<Object> createAlumnoExtraordinario(@RequestBody final List<AutorizacionExtraordinarioDto> listAutorizacionDesplazamientoDto,
			 												       @PathVariable("idPerfil") Long idPerfil,
			 												       @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<AutorizacionExtraordinario> listAutorizacionDesplazamientoIn = 
					extraordinarioService.createAlumnoExtraordinario(listAutorizacionDesplazamientoDto, idPerfil, datosUsuario.getXUsuarioDelphos());
			
			List<AutorizacionExtraordinarioDto> listAutorizacionDesplazamientoOut = listAutorizacionDesplazamientoIn.stream().map(x->modelMapper.map(x, AutorizacionExtraordinarioDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(listAutorizacionDesplazamientoOut, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//TODO Jaime
	/**
	 * 
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Get valores del combo de alumnos", 
	           description = "Este metodo devuelve los valores del combo de los alumnos", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getComboAlumnos/{anno}/{idCentro}/{idTutor}/{idCurso}/{idUnidad}/{nuPeticion}")
	public ResponseEntity<Object> getComboAlumnos(@PathVariable("anno") Integer anno,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idTutor") Long idTutor,
																		@PathVariable("idCurso") Long idCurso,
																		@PathVariable("idUnidad") Long idPerfil,
																		@PathVariable("nuPeticion") Integer nuPeticion,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt); 

		Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
		
		List<ElementoSelect> elementIn = extraordinarioService.getComboAlumnos(anno, idCentro, idTutor, idCurso, idPerfil,nuPeticion, xUsuarioComunica);
		
		List<ElementoSelectDto> elementOut = elementIn.stream().map(x->modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(elementOut, HttpStatus.OK);
	}
	
	
	//TODO Jaime
	/** 
	 * Get autorizacion extraordinario.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get autorizacion extraordinario", description = "Este metodo devuelve la autorizacion extraordinario",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAutorizacionExtraordinario/{idAutExt}")
	public ResponseEntity<AutorizacionExtraordinarioDto> getAutorizacionExtraordinario(@PathVariable("idAutExt") Long idAutExt){
		
		AutorizacionExtraordinario autorizacionExtraordinarioIn =  extraordinarioService.getAutorizacionExtraordinario(idAutExt);
		
		AutorizacionExtraordinarioDto autorizacionExtraordinarioOut = modelMapper.map(autorizacionExtraordinarioIn, AutorizacionExtraordinarioDto.class);
				
		return new ResponseEntity<>(autorizacionExtraordinarioOut, HttpStatus.OK);
	}
	
	

	
	
	//TODO Jaime
	/**
	 * Datos alumno.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT','CFT')")
	@Operation(summary = "Datos alumno", description = "Este metodo devuelve el Nombre del alumno",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosAlumnoExtraordinarioNuevo/{idMatricula}")
	public ResponseEntity<AlumnoDto> getDatosAlumnoExtraordinarioNuevo(@PathVariable("idMatricula") Long idMatricula){
		
		Alumno alumno =  extraordinarioService.getDatosAlumnoExtra(idMatricula);
		AlumnoAux alumnoAux = extraordinarioService.getDateScheduleAlumnoExtra(idMatricula);

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateFini = null;
		Date dateFfin = null;
		try {
			dateFini = formatter.parse(alumnoAux.getFini());
			dateFfin = formatter.parse(alumnoAux.getFfin());
			//Sumar 2 horas en milisegundos
			dateFini.setTime(dateFini.getTime() + 2 * 60 * 60 * 1000);
			dateFfin.setTime(dateFfin.getTime() + 2 * 60 * 60 * 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		alumno.setDateIniExt(dateFini);
		alumno.setDateFinExt(dateFfin);
		alumno.setHorarioExt(alumnoAux.getHorario());
		
		alumno.setDateIniProv(dateFini);
		alumno.setDateFinProv(dateFfin);
		alumno.setHorarioProv(alumnoAux.getHorario());
		alumno.setLgfindeext(0);
		alumno.setLgvacacext(0);
				
		return new ResponseEntity<>(modelMapper.map(alumno, AlumnoDto.class), HttpStatus.OK);
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
	@GetMapping("/getCombosAutorizacionesExtraordinario/{cbName}/{anno}/{idCentro}/{idTutor}/{idFamilia}/{idCurso}/{idUnidad}/{idPerfil}")
	public ResponseEntity<Object> getCombosAutorizacionesExtraordinario(@PathVariable("cbName") String cbName,
																		@PathVariable("anno") Integer anno,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idTutor") Long idTutor,
																		@PathVariable("idFamilia") Long idFamilia,
																		@PathVariable("idCurso") Long idCurso,
																		@PathVariable("idUnidad") Long idUnidad,
																		@PathVariable("idPerfil") Long idPerfil,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt); 
		
		try {
			
			List<ElementoSelect> elementIn = extraordinarioService.getCombosAutorizacionesExtraordinario(cbName, anno, idCentro, idTutor, 
																										 idFamilia, idCurso, idUnidad, idPerfil, datosUsuario.getXUsuarioDelphos());
			
			List<ElementoSelectDto> elementOut = elementIn.stream().map(x->modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(elementOut, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	    
    /**
	 * Get listado autorizaciones extraordinarias fuera de provincia.
	 *
	 * @return the response entity  getListadoAutorizacionesExtraordinario/2022/2157/521/-1/109351/482344
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get listado autorizaciones extraordinario fuera de provincia", description = "Este metodo devuelve el listado autorizaciones extraordinario fuera de provincia",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoAutorizacionesExtraordinario/{anno}/{idCentro}/{idTutor}/{idCurso}/{idUnidad}/{idPerfil}/{nuPeticion}")
	public ResponseEntity<Object> getListadoAutorizacionesExtraordinario(@PathVariable("anno") Integer anno,
																		 @PathVariable("idCentro") Long idCentro,
																		 @PathVariable("idTutor") Long idTutor,
																		 @PathVariable("idCurso") Long idCurso,
																		 @PathVariable("idUnidad") Long idUnidad,
																		 @PathVariable("idPerfil") Long idPerfil,
																		 @PathVariable("nuPeticion") Integer nuPeticion,
																		 @RequestHeader(Constants.AUTHORIZATION) String jwt){
		   
		    DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt); 
		
			List<ListadoAutorizacionExtraordinario> listadoIn = extraordinarioService.getListadoAutorizacionesExtraordinario(anno, idCentro, idTutor, idCurso, idUnidad,idPerfil, nuPeticion, datosUsuario.getXUsuarioDelphos());
			
			List<ListadoAutorizacionExtraordinarioDto> listadoOut = listadoIn.stream().map(x->modelMapper.map(x, ListadoAutorizacionExtraordinarioDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(listadoOut, HttpStatus.OK);
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
	@PostMapping(value="/createSiguienteEstadoExtraFlujoHistorial/{idPerfil}/{abrev}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoHistorial(@RequestBody final AutorizacionExtraordinarioHistorialDto autorizacionExtraordinarioDto,
																	  @PathVariable("idPerfil") Long idPerfil,
																	  @PathVariable("abrev") String abrev,
																	  @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			AutorizacionExtraordinarioHistorial historialIn = extraordinarioService.createSiguienteEstadoFlujoHistorial(autorizacionExtraordinarioDto,																												
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
	 * Generar Anexo XI.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Generar Anexo XI", description = "Este metodo generar Anexo XI", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })  
	@PostMapping("/generarAnexosXI/{idPerfil}/{idCentro}/{abrev}/{annoAnexo}/{idTutor}/{idCurso}/{idUnidad}/{nuPeticion}")
	public ResponseEntity<Object> generarAnexos(@RequestBody final AnexoExtraordinarioAuxDto anexoExtraordinarioAuxDto,
												@RequestHeader(Constants.AUTHORIZATION) String jwt,
												@PathVariable("idPerfil") Long idPerfil,
												@PathVariable("idCentro") Long idCentro,
												@PathVariable("abrev") String abrev,
												@PathVariable("annoAnexo") Integer annoAnexo,
												@PathVariable("idTutor") Long idTutor,
												@PathVariable("idCurso") Long idCurso,
												@PathVariable("idUnidad") Long idUnidad,
												@PathVariable("nuPeticion") Integer nuPeticion) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			extraordinarioService.generarAnexosXI(idPerfil, 
												   idCentro, 
												   datosUsuario.getXUsuarioDelphos(), 
												   abrev, 
												   annoAnexo,
												   idTutor,
												   idCurso,
												   idUnidad,
												   nuPeticion,
												   anexoExtraordinarioAuxDto.getTxjustificacion(),
												   anexoExtraordinarioAuxDto.getTxcontrol(),
												   anexoExtraordinarioAuxDto.getTxcostes());
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Borrado de una autorización de desplazamiento extraordinario y de su historial.
	 * 
	 * @param idAutDes
	 * 
	 * @return
	 */
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Borrado de la autorizacion de desplazamiento extraordinario", description = "Este metodo borra los datos de autorizacion de desplazamiento extraordinario",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteAutorizacionDesplazamientoExtraordinario/{idAutExtPro}")
	public ResponseEntity<HttpStatus> deleteAutorizacionDesplazamientoExtraordinario(
			@Parameter(description = "Identificador de la autorizacion de desplazamiento extraordinaria", required = true) @PathVariable("idAutExtPro") Long idAutExtPro) {
		try {
			extraordinarioService.deleteAutorizacionDesplazamiento(idAutExtPro);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
/**
 * Upload fichero para la autorizacion del extraordinario.
 *
 * @return the response entity
 */
//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
@Operation(summary = "Upload fichero anexo FCT extraordinario", description = "Upload fichero anexo FCT extraordinario", responses = {
		@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
@PostMapping(value="/uploadAdjunto/{id}/{esAuto}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
public ResponseEntity<Object> uploadAdjunto(@PathVariable("id") Long id,
											@PathVariable("esAuto") Boolean esAuto,
											@RequestPart(value = "file", required = false) MultipartFile file) {
	
	
	try {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		extraordinarioService.uploadAdjunto(id,file, esAuto != null && esAuto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	
	} catch (Exception e) {
		return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
@Operation(summary ="Anexo adjunto de desplazamiento extraordinario.", description = "",responses = {
		@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
@GetMapping({ "/generarAutorizacionCentroAnexoXi/{idAutAnexo}/{abrevEstadoSiguiente}"})
    public ResponseEntity<Object> generarAutorizacionCentroAnexoXi(HttpServletResponse response, @PathVariable("idAutAnexo") Long idAutAnexo,
												  @PathVariable("abrevEstadoSiguiente") String abrevEstadoSiguiente){
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename=" + "Adjunto_AnexoXI" +"."+ "pdf");
	response.setHeader("Pragma", NO_CACHE);
	response.setHeader("Cache-Control", NO_CACHE);
	response.setStatus(200);

	    try {

			byte[] documento = extraordinarioService.generarAutorizacionCentroAnexoXi(idAutAnexo,abrevEstadoSiguiente);

			InputStream is = new ByteArrayInputStream(documento);
			FileCopyUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		    return new ResponseEntity<>(HttpStatus.OK);
	    } catch (Exception e) {
		    return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
}
