package es.jccm.edu.proyectosfct.adapter.in.rest.convenios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.ConveniosFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model.ConveniosListDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.FamiliaDto;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConvenioListFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.DatosCentroConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
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
@Tag(name = "Servicio Convenios", description = "Servicio con las operaciones sobre Convenios")
public class ConveniosFctRestController {

	
	private static final String NO_CACHE = "no-cache";
	
	@Autowired
	private IConveniosFctService conveniosFctService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Create
	
	/**
	 * Creación de los Datos de un Convenio.
	 *
	 * @param conveniosFctDto Datos del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de un Convenio", description = "Este metodo crea los datos de un Convenio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenio")
	public ResponseEntity<Object> createConvenioFct(
			@Parameter(description = "Datos del Convenio", required = true) @RequestBody final ConveniosFctDto conveniosFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
			ConveniosFct convenioIn = modelMapper.map(conveniosFctDto, ConveniosFct.class);
		
			convenioIn = conveniosFctService.createConvenio(convenioIn);
		
			ConveniosFctDto convenioOut = modelMapper.map(convenioIn, ConveniosFctDto.class);
		
		
			response = new ResponseEntity<Object>(convenioOut, HttpStatus.OK);
		}catch (DataIntegrityViolationException e) {		
			if (e.getMessage().contains("DELPHOS.CONVENIOS_UK_XEMP_XCEN_IDSED"))
			{
				response = new ResponseEntity<Object>("Ya existe un convenio con la misma empresa y sede.", HttpStatus.BAD_REQUEST);
			} else if (e.getMessage().contains("DELPHOS.CONVENIOS_UK_CDCONVENIO"))
			{
				response = new ResponseEntity<Object>("Ya existe un convenio con el Número convenio introducido.", HttpStatus.BAD_REQUEST);	
			}
		}catch (Exception e) {
			response = new ResponseEntity<Object>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	// Read
	
	/**
	 * Centros.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener centros", description = "Este metodo devuelve el listado de centros", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCentrosConveniosDelegacion/{idPerfil}/{idCentro}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getCentrosConveniosDelegacion(@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("idProvincia") Long idProvincia,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> centros = conveniosFctService.getCentrosDelegacion(datosUsuario.getXUsuarioDelphos(), idPerfil, idCentro, idProvincia);
		
		List<ElementoSelectDto> centrosDto = centros.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(centrosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Convenios", description = "Este metodo devuelve una lista con todos los Convenios",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllConveniosByCentroAndAnno/{idCentro}/{cAnno}")
	public ResponseEntity<List<ConveniosFctDto>> getAllConveniosByCentroAndAnno(@PathVariable("idCentro") Long idCentro,  @PathVariable("cAnno") Integer cAnno){
		
		List<ConveniosFct> convenios =  conveniosFctService.findConveniosByCentroIdAndAnno(idCentro, cAnno);
		
		List<ConveniosFctDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosFctDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosFctDto>>(conveniosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Convenios", description = "Este metodo devuelve una lista con todos los Convenios",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllConveniosDelegacionByCentroAndAnno/{idPerfil}/{idCentroProvincia}/{idCentro}/{cAnno}")
	public ResponseEntity<List<ConveniosFctDto>> getAllConveniosDelegacionByCentroAndAnno(@PathVariable("idPerfil") Long idPerfil, 
			@PathVariable("idCentroProvincia") Long idCentroProvincia, 
			@PathVariable("idCentro") Long idCentro,  
			@PathVariable("cAnno") Integer cAnno,
			@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ConveniosFct> convenios =  conveniosFctService.findConveniosDelegacionByCentroIdAndAnno(datosUsuario.getXUsuarioDelphos() ,idPerfil, idCentroProvincia, idCentro, cAnno);
		
		List<ConveniosFctDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosFctDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosFctDto>>(conveniosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Convenios", description = "Este metodo devuelve una lista con todos los Convenios",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllConvenios")
	public ResponseEntity<List<ConveniosListDto>> getAllConvenios(){
		
		List<ConvenioListFct> convenios =  conveniosFctService.getAllConvenios();
		
		List<ConveniosListDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosListDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosListDto>>(conveniosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Convenios", description = "Este metodo devuelve una lista con todos los Convenios del centro por año",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllConveniosCentroAnno/{idCentro}/{cAnno}/{idEmpresa}/{idEstado}/{idFamilia}/{idTutor}/{idMatricula}/{idTipo}")
	public ResponseEntity<List<ConveniosListDto>> getAllConveniosCentroAnno(@PathVariable("idCentro") Long idCentro,
																			@PathVariable("cAnno") Integer cAnno,
																			@PathVariable("idEmpresa") Long idEmpresa,	
																			@PathVariable("idEstado") Long idEstado,
																			@PathVariable("idFamilia") Long idFamilia,
																			@PathVariable("idTutor") Long idTutor,
																			@PathVariable("idMatricula") Long idMatricula,
																			@PathVariable("idTipo") Long idTipo){
		
		List<ConvenioListFct> convenios =  conveniosFctService.getAllConveniosCentroAnno(idCentro,cAnno, idEmpresa, idEstado, idFamilia, idTutor, idMatricula, idTipo);
		
		List<ConveniosListDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosListDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosListDto>>(conveniosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Convenios activos", description = "Este metodo devuelve una lista con todos los Convenios activos y firmados",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllConveniosActivos/{idCentro}/{lglofp}")
	public ResponseEntity<List<ConveniosListDto>> getAllConveniosActivos(
				@Parameter(description = "Identificador del centro", required = true) @PathVariable("idCentro") Long idCentro,
				                                                                      @PathVariable("lglofp") Integer lglofp){
		
		List<ConvenioListFct> convenios =  conveniosFctService.getAllConveniosActivos(idCentro,lglofp);
		
		List<ConveniosListDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosListDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosListDto>>(conveniosDto, HttpStatus.OK);
	}
	
	
	/**
	 * Datos de un Convenio.
	 *
	 * @param id Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de un Convenio", description = "Este metodo devuelve los datos de un Convenio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioById/{idConvenio}")
	public ResponseEntity<ConveniosFctDto> getConvenioById(
			@Parameter(description = "Identificador del Conveio", required = true) @PathVariable("idConvenio") Long idConvenio) {
		
		ConveniosFctDto convenioOut = modelMapper.map(conveniosFctService.getConvenioById(idConvenio), ConveniosFctDto.class);
		
		return new ResponseEntity<ConveniosFctDto>(convenioOut, HttpStatus.OK);
	}
	
	/**
	 * Datos de un Centro.
	 *
	 * @param idCentro Id del centro
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de un Centro", description = "Este metodo devuelve los datos de un centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosCentro/{idCentro}")
	public ResponseEntity<DatosCentroConvenioProjection> getDatosCentro(
			@Parameter(description = "Identificador del Centro", required = true) @PathVariable("idCentro") Long idCentro) {
		return new ResponseEntity<DatosCentroConvenioProjection>(conveniosFctService.getDatosCentro(idCentro), HttpStatus.OK);
	}
	
	// Update
	
	/**
	 * Actualización de los Datos de un Convenio.
	 *
	 * @param conveniosFctDto Datos del Convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualización de los datos de un Convenio", description = "Este metodo actualiza los datos de un Convenio",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateConvenio")
	public ResponseEntity<ConveniosFctDto> updateConvenioFct(
			@Parameter(description = "Datos del Convenio", required = true) @RequestBody final ConveniosFctDto conveniosFctDto) {
		
		ConveniosFct convenioIn = modelMapper.map(conveniosFctDto, ConveniosFct.class);
		
		convenioIn = conveniosFctService.updateConvenio(convenioIn);
		
		ConveniosFctDto convenioOut = modelMapper.map(convenioIn, ConveniosFctDto.class);
		
		return new ResponseEntity<ConveniosFctDto>(convenioOut, HttpStatus.OK);
	}
	
	// Delete
	
	/**
	 * Borrado de un Convenio.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado de un Convenio", description = "Este metodo borra los datos de un Convneio",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@DeleteMapping("/deleteConvenio/{idConvenio}")
	public ResponseEntity<HttpStatus> deleteConvenio(
			@Parameter(description = "Identificador del Convenio", required = true) @PathVariable("idConvenio") Long idConvenio) {
		try {
			conveniosFctService.deleteConvenio(idConvenio);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Lista de Familias del un convenio", description = "Este metodo devuelve una lista con todas familias de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasConvenio/{idConvenio}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasConvenio(@PathVariable("idConvenio") Long idConvenio) {

		List<Familia> familias = conveniosFctService.getAllFamiliasConvenio(idConvenio);		
		
		List<FamiliaDto> familiasDto = familias.stream()
				.map(entity -> modelMapper.map(entity, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);

	}
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Familias del un convenio", description = "Este metodo devuelve una lista con todas familias de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasListadoConvenio/{idCentro}/{cAnno}/{idEmpresa}/{idEstado}"})
	public ResponseEntity<List<FamiliaDto>> getAllFamiliasListadoConvenio(@PathVariable("idCentro") Long idCentro,
																		@PathVariable("cAnno") Integer cAnno,
																		@PathVariable("idEmpresa") Long idEmpresa,																	
																		@PathVariable("idEstado") Long idEstado) {

		List<Familia> familias = conveniosFctService.getAllFamiliasListadoConvenio(idCentro, cAnno, idEmpresa, idEstado);		
		
		List<FamiliaDto> familiasDto = familias.stream().map(entity -> modelMapper.map(entity, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasDto, HttpStatus.OK);
	}
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Familias del un convenio", description = "Este metodo devuelve una lista con todas familias de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllTutoresListadoConvenio/{idCentro}/{cAnno}/{idEmpresa}/{idEstado}/{idFamilia}"})
	public ResponseEntity<List<ElementoSelectDto>> getAllTutoresListadoConvenio(@PathVariable("idCentro") Long idCentro,
																		 @PathVariable("cAnno") Integer cAnno,
																		 @PathVariable("idEmpresa") Long idEmpresa,																		
																		 @PathVariable("idEstado") Long idEstado,
																		 @PathVariable("idFamilia") Long idFamilia) {

		List<ElementoSelect> tutores = conveniosFctService.getAllTutoresListadoConvenio(idCentro, 
																				        cAnno, 
																				        idEmpresa,																				      
																				        idEstado,
																				        idFamilia);		
		
		List<ElementoSelectDto> tutoresDto = tutores.stream().map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(tutoresDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de alumnado del un convenio", description = "Este metodo devuelve una lista con todo el alumnado de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllAlumnadoListadoConvenio/{idCentro}/{cAnno}/{idEmpresa}/{idEstado}/{idFamilia}/{idTutor}"})
	public ResponseEntity<List<ElementoSelectDto>> getAllAlumnadoListadoConvenio(@PathVariable("idCentro") Long idCentro,
																		 @PathVariable("cAnno") Integer cAnno,
																		 @PathVariable("idEmpresa") Long idEmpresa,																		
																		 @PathVariable("idEstado") Long idEstado,
																		 @PathVariable("idFamilia") Long idFamilia,
																		 @PathVariable("idTutor") Long idTutor) {

		List<ElementoSelect> alumnado = conveniosFctService.getAllAlumnadoListadoConvenio(idCentro, 
																				        cAnno, 
																				        idEmpresa,																				        
																				        idEstado,
																				        idFamilia,
																				        idTutor);		
		
		List<ElementoSelectDto> alumnadoDto = alumnado.stream().map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(alumnadoDto, HttpStatus.OK);
	}
	
	
	
	
	//GetConvenioById Jasper PDF
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@GetMapping("/conveniopdf/{id}")
	public void generateReport(HttpServletResponse response, @PathVariable Long id) throws JRException, IOException {
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=" + "Anexo02" +"."+ "pdf");
	    response.setHeader("Pragma", NO_CACHE);
	    response.setHeader("Cache-Control", NO_CACHE);
	    response.setStatus(200);	    
	    
	    byte [] documento = conveniosFctService.exportReport(id);
	    
	    InputStream is = new ByteArrayInputStream(documento);
	    FileCopyUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
       
    }
	
	//GetConvenioById Jasper PDF
	    //@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
		@GetMapping("/renovarpdf/{id}")
		public void generateReportRenovar(HttpServletResponse response, @PathVariable Long id) throws JRException, IOException {
			response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; filename=" + "Anexo16" +"."+ "pdf");
		    response.setHeader("Pragma", NO_CACHE);
		    response.setHeader("Cache-Control", NO_CACHE);
		    response.setStatus(200);	    
		    
		    byte [] documento = conveniosFctService.exportReportRenovar(id);
		    
		    InputStream is = new ByteArrayInputStream(documento);
		    FileCopyUtils.copy(is, response.getOutputStream());
		    response.flushBuffer();
	       
	    }
	
	/**
	 * Datos de un Convenio.
	 *
	 * @param id Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualizar la fecha de firma", description = "Este metodo actualiza la fecha de firma de un convenio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/firmarConvenio/{fechaFirma}/{idConvenio}/{entidad}")
	public ResponseEntity<ConveniosFctDto> firmarConvenio(
			@Parameter(description = "Fecha de firma del convenio", required = true) @PathVariable("fechaFirma") String fechaFirma,
			@Parameter(description = "Identificador del Convenio", required = true) @PathVariable("idConvenio") Long idConvenio,
			@Parameter(description = "Entidad de firma de Convenio", required = true) @PathVariable("entidad") String entidad
			) {
		
		ConveniosFctDto convenioOut = modelMapper.map(conveniosFctService.firmarConvenio(fechaFirma,idConvenio,entidad), ConveniosFctDto.class);
		
		return new ResponseEntity<ConveniosFctDto>(convenioOut, HttpStatus.OK);
	}
	
	/**
	 * Datos de un Convenio.
	 *
	 * @param id Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Número de Convenio", description = "Este metodo devuelve el siguiente numero de convenio del centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getNumeroConvenio/{idCentro}")
	public ResponseEntity<String> getNumeroConvenio(
			@Parameter(description = "Fecha de firma del convenio", required = true) @PathVariable("idCentro") Long idCentro) 
	{
		
		String numeroConvenio = conveniosFctService.getNumeroConvenio(idCentro);
		
		return new ResponseEntity<String>(numeroConvenio, HttpStatus.OK);
	}
	
	
	
	/**
	 * Empresas.
	 *
	 * 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Obtener Empresas", description = "Este metodo devuelve el listado de Empresas de para el perfil  delegacion", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpresasDelegacion/{idPerfil}/{idCentro}/{idCentroProvincia}/{cAnno}/{idEstado}/{idProvincia}")
		public ResponseEntity<List<ElementoSelectDto>> getEmpresasDelegacion(@PathVariable("idPerfil") Long idPerfil,
																		   @PathVariable("idCentro") Long idCentro,
																		   @PathVariable("idCentroProvincia") Long idCentroProvincia, 
																		   @PathVariable("cAnno") Long cAnno,
																		   @PathVariable("idProvincia") Long idProvincia,
																		   @PathVariable("idEstado") Long idEstado,
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ElementoSelect> cursos = conveniosFctService.getEmpresasDelegacion(datosUsuario.getXUsuarioDelphos(), 
																				idPerfil, 
																				idCentro,
																				idCentroProvincia,
																				cAnno,
																				idProvincia,
																				idEstado);
		
		List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(cursosDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Familias del un convenio", description = "Este metodo devuelve una lista con todas familias de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllFamiliasDelegacionListadoConvenio/{idPerfil}/{idCentro}/{idCentroProvincia}/{cAnno}/{idEmpresa}/{idEstado}/{idProvincia}"})
	public ResponseEntity<List<ElementoSelectDto>> getAllFamiliasDelegacionListadoConvenio(@PathVariable("idPerfil") Long idPerfil,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idCentroProvincia") Long idCentroProvincia,																		
																		@PathVariable("cAnno") Integer cAnno,
																		@PathVariable("idEmpresa") Long idEmpresa,																		
																		@PathVariable("idEstado") Long idEstado,
																		@PathVariable("idProvincia") Long idProvincia,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ElementoSelect> familias = conveniosFctService.getAllFamiliasDelegacionListadoConvenio(datosUsuario.getXUsuarioDelphos(), 
																								    idPerfil, 
																								    idCentro,
																								    idCentroProvincia, 
																								    cAnno, 
																								    idEmpresa,																								     
																								    idEstado,
																								    idProvincia);		
		
		List<ElementoSelectDto> familiasDto = familias.stream().map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(familiasDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Familias del un convenio", description = "Este metodo devuelve una lista con todas familias de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllTutoresDelegacionListadoConvenio/{idPerfil}/{idCentro}/{idCentroProvincia}/{cAnno}/{idEmpresa}/{idEstado}/{idProvincia}/{idFamilia}"})
	public ResponseEntity<List<ElementoSelectDto>> getAllFamiliasDelegacionListadoConvenio(@PathVariable("idPerfil") Long idPerfil,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idCentroProvincia") Long idCentroProvincia,																		
																		@PathVariable("cAnno") Integer cAnno,
																		@PathVariable("idEmpresa") Long idEmpresa,
																		@PathVariable("idEstado") Long idEstado,
																		@PathVariable("idProvincia") Long idProvincia,
																		@PathVariable("idFamilia") Long idFamilia,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ElementoSelect> tutores = conveniosFctService.getAllTutoresDelegacionListadoConvenio(datosUsuario.getXUsuarioDelphos(), 
																								    idPerfil, 
																								    idCentro,
																								    idCentroProvincia, 
																								    cAnno, 
																								    idEmpresa,																								    
																								    idEstado,
																								    idProvincia,
																								    idFamilia);		
		
		List<ElementoSelectDto> tutoresDto = tutores.stream().map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(tutoresDto, HttpStatus.OK);
	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Alumnado del un convenio", description = "Este metodo devuelve una lista con todo alumnado de un convenio", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllAlumnadoDelegacionListadoConvenio/{idPerfil}/{idCentro}/{idCentroProvincia}/{cAnno}/{idEmpresa}/{idEstado}/{idProvincia}/{idFamilia}/{idTutor}"})
	public ResponseEntity<List<ElementoSelectDto>> getAllAlumnadoDelegacionListadoConvenio(@PathVariable("idPerfil") Long idPerfil,
																		@PathVariable("idCentro") Long idCentro,
																		@PathVariable("idCentroProvincia") Long idCentroProvincia,																		
																		@PathVariable("cAnno") Integer cAnno,
																		@PathVariable("idEmpresa") Long idEmpresa,																		
																		@PathVariable("idEstado") Long idEstado,
																		@PathVariable("idProvincia") Long idProvincia,
																		@PathVariable("idFamilia") Long idFamilia,
																		@PathVariable("idTutor") Long idTutor,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		List<ElementoSelect> alumnado = conveniosFctService.getAllAlumnadoDelegacionListadoConvenio(datosUsuario.getXUsuarioDelphos(), 
																								    idPerfil, 
																								    idCentro,
																								    idCentroProvincia, 
																								    cAnno, 
																								    idEmpresa,																								    
																								    idEstado,
																								    idProvincia,
																								    idFamilia,
																								    idTutor);		
		
		List<ElementoSelectDto> alumnadoDto = alumnado.stream().map(entity -> modelMapper.map(entity, ElementoSelectDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<ElementoSelectDto>>(alumnadoDto, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('FCT','CFT')")
	@Operation(summary = "Lista de Convenios", description = "Este metodo devuelve una lista con todos los Convenios del centro por año",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllCoveniosDelegacionCentroAnno/{idPerfil}/{idCentro}/{idCentroProvincia}/{cAnno}/{idEmpresa}/{idEstado}/{idFamilia}/{idProvincia}/{idTutor}/{idMatricula}/{idTipo}")
	public ResponseEntity<List<ConveniosListDto>> getAllCoveniosDelegacionCentroAnno(@PathVariable("idPerfil") Long idPerfil,
																					 @PathVariable("idCentroProvincia") Long idCentroProvincia,
																					 @PathVariable("idCentro") Long idCentro,
																					 @PathVariable("cAnno") Integer cAnno,
																					 @PathVariable("idEmpresa") Long idEmpresa,
																					 @PathVariable("idEstado") Long idEstado,
																					 @PathVariable("idFamilia") Long idFamilia,
																					 @PathVariable("idProvincia") Long idProvincia,
																					 @PathVariable("idTutor") Long idTutor,
																					 @PathVariable("idMatricula") Long idMatricula,
																					 @PathVariable("idTipo") Long idTipo,
																					 @RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ConvenioListFct> convenios =  conveniosFctService.getAllCoveniosDelegacionCentroAnno(datosUsuario.getXUsuarioDelphos(), 
																								  idPerfil, 
																								  idCentro, 
																								  idCentroProvincia, 
																								  cAnno, 
																								  idEmpresa, 
																								  idEstado, 
																								  idFamilia,
																								  idProvincia,
																								  idTutor,
																								  idMatricula,
																								  idTipo);
		
		List<ConveniosListDto> conveniosDto = convenios.stream().map(x -> modelMapper.map(x, ConveniosListDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<List<ConveniosListDto>>(conveniosDto, HttpStatus.OK);
	}
	
	
	/**
	 * Usuario es director.
	 *
	 * @param 
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Número de Convenio", description = "Este metodo devuelve S cuando el usuario es director del centro", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEsDirector/{idCentro}")
	public ResponseEntity<String> getEsDirector(@PathVariable("idCentro") Long idCentro,
			                                    @RequestHeader(Constants.AUTHORIZATION) String jwt) 	{
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		String esDirector = conveniosFctService.getEsDirector(idCentro,datosUsuario.getXUsuarioDelphos());
		
		return new ResponseEntity<String>(esDirector, HttpStatus.OK);
	}
	/**
	 //     * Obtiene un valor numérico que indica si el convenio ya existe o no
	 //     *
	 //     * @param tipoConvenio
	 //     * @param cif
	 //     * @param xCentro
	 //     * @param fhIni
	 //     * @param fhFin
	 //     * @return Integer
	 //     */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Obtener si el convenio esta vigente.", description = "Este metodo devuelve si ya existe un covenio para con ese cif para ese centro. ", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping(value = "/getConveniosVigentes/{tipoConvenio}/{cif}/{xCentro}/{fhIni}/{fhFin}")
	public ResponseEntity<String> getConveniosVigentes(
			@PathVariable("tipoConvenio") Integer tipoConvenio,
			@PathVariable("cif") String cif,
			@PathVariable("xCentro") Long xCentro,
			@PathVariable("fhIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fhIni,
			@PathVariable("fhFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fhFin) {

			String respuesta = conveniosFctService.getConveniosVigentes(tipoConvenio,cif,xCentro,fhIni,fhFin);

		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Datos de un Convenio.
	 *
	 * @param id Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Baja convenio", description = "Baja convenio", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/bajaConvenio/{id}")
	public ResponseEntity<Integer> bajaConvenio(@Parameter(description = "Identificador del Convenio", required = true) @PathVariable("id") Long id) {
		
		Integer baja = conveniosFctService.bajaConvenio(id);
		
		return new ResponseEntity<Integer>(baja, HttpStatus.OK);
	}
}
