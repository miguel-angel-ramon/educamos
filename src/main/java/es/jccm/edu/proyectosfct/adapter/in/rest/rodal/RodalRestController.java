package es.jccm.edu.proyectosfct.adapter.in.rest.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import com.jccm.cstic.clientesws.clienterodal.RecuperarDocMetadatosFault;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Rodal", description = "Servicio con las operaciones sobre Rodal")
@Slf4j
public class RodalRestController {

    @Autowired
    private IRodalClient rodalClient;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	IParamsFCTService iParamsFCTService;

    /**
	 * Lista todos los documentos de una UnidadFuncional, Sistema, Subsistema y Expediente específico.
	 *
	 * @param Subsistema
	 * @param Expediente
	 * @return List<RodalDocDto>
	 */
	/*
    @Operation(summary = "Listar documentos", description = "Este metodo crea un txt con los datos de todos los documentos", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("proyectosfct/listarDocumentos")
    public ResponseEntity<List<RodalDocDto>> listarDoc(HttpServletResponse response) throws RodalExceptionService, ListarExpedientesFault, IOException {
    	
    	// Parametros documentos
    	ParametrosListarDocumentos paramDoc = new ParametrosListarDocumentos();
		paramDoc.setUnidadFuncional(rodalConfig.getUnidadFuncional());
		paramDoc.setSistema(rodalConfig.getSistema());
		paramDoc.setSubsistema("938221");
		paramDoc.setExpediente("938221");
		
		List<RodalDocDto> rodalOut = null;
    	
    	try {
        				
			List<DocBase> listaDocumentos = rodalClient.listarDocumentos(paramDoc);
			
			rodalOut = listaDocumentos.stream().map(doc -> modelMapper.map(doc, RodalDocDto.class)).collect(Collectors.toList());
			
    	} catch (ListarDocumentosFault e) {
    		e.printStackTrace();
    	}
    	
    	return new ResponseEntity<List<RodalDocDto>>(rodalOut, HttpStatus.OK);
    } */
    
    /**
	 * Descarga el documento de rodal que corresponde con el idDoc recibido.
	 *
	 * @param idDoc
	 * @return ResponseEntity<RodalDocDto>
	 */
    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Descargar documento", description = "Este metodo descarga el documento de rodal que corresponde con el idDoc recibido", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/descargarDocumento/{idRodal}/{txNombreFichero}")
    public void descargarDoc(@Parameter(description = "I de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("idRodal") String idRodal,
    				         @Parameter(description = "Nombre del fichero guardado en la entidad", required = true) @PathVariable("txNombreFichero") String txNombreFichero,
    		                 HttpServletResponse response) throws RodalExceptionService, RecuperarDocMetadatosFault, IOException {
		
		// Recuperamos el documento de rodal
		ByteArrayOutputStream bytes = null;
		try {
			bytes = rodalClient.recuperaDoc(idRodal, "MFCT");
		} catch (RodalExceptionService e) {
			e.printStackTrace();
		}
		
		// Descargamos el documento recuperado
		//response.setContentType("application/pdf");
		//response.setHeader("Content-Disposition", "attachment;filename=" + txNombreFichero);
		
		response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(txNombreFichero, "UTF-8"));
		response.setContentType("application/pdf");
		
		OutputStream out = response.getOutputStream();
        if(bytes!=null) out.write(bytes.toByteArray());        
		
    }
    
    /**
	 * Inserta un documento en rodal.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<RodalDocDto>
	 */
    //@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Insertar documento", description = "Este metodo inserta un documento recibido en rodal", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/insertarDocumento/{entidad}/{idEntidad}/{cAnno}/{idCentro}/{cPerfil}")
    public ResponseEntity<Object> insertarDoc(@Parameter(description = "Documento para subir a Rodal", required = true) @RequestParam("file") MultipartFile file, 
    										  @Parameter(description = "Descripcion de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("entidad") String entidad,
    										  @Parameter(description = "I de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("idEntidad") Long idEntidad,
    										  @Parameter(description = "I de la anno FCT que sube el fichero a Rodal", required = true) @PathVariable("cAnno") Long cAnno,
    										  @Parameter(description = "I de la anno FCT que sube el fichero a Rodal", required = true) @PathVariable("idCentro") Long idCentro,
											  @Parameter(description = "Perfil de llamada al método", required = true) @PathVariable("cPerfil") String cPerfil,
											  @RequestHeader(Constants.AUTHORIZATION) String jwt,
    		                                  HttpServletResponse response) throws RodalExceptionService, InsertarDocFault, IOException {
		try {

			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());
			
			return new ResponseEntity<Object>(rodalClient.insertaDoc(file, "MFCT", entidad , idEntidad, cAnno, idCentro,-1L,
					                                                 cPerfil,datosUsuario.getXUsuarioDelphos(), 
					                                                 xUsuarioComunica), HttpStatus.OK);
			
	        } catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}	

    }
    
    /**
	 * Inserta un documento en rodal.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<RodalDocDto>
	 */
    //@PreAuthorize("hasAnyRole('P','PRO','C','P','FCT')")
    @Operation(summary = "Actualizar documento", description = "Este metodo actualiza un documento recibido en rodal", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/actualizarDocumento/{entidad}/{idEntidad}/{cPerfil}")
    public ResponseEntity<Boolean> actualizaDoc(@Parameter(description = "Documento para subir a Rodal", required = true) @RequestParam("file") MultipartFile file, 
    		                        	        @Parameter(description = "Descripcion de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("entidad") String entidad,
    				                            @Parameter(description = "I de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("idEntidad") Long idEntidad,
												@Parameter(description = "Perfil de llamada al método", required = true) @PathVariable("cPerfil") String cPerfil,
												@RequestHeader(Constants.AUTHORIZATION) String jwt,
    		                                    HttpServletResponse response) throws RodalExceptionService, InsertarDocFault, IOException {
		
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Long xUsuarioComunica = iParamsFCTService.getXusuarioComunicaByOid(datosUsuario.getOid(), datosUsuario.getXUsuarioComunica());

			return new ResponseEntity<Boolean>(rodalClient.actualizaDoc(file, "MFCT", entidad, idEntidad, cPerfil,datosUsuario.getXUsuarioDelphos(), xUsuarioComunica), HttpStatus.OK);
			
	        } catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
    }
    
    /**
	 * Borra un documento en rodal.
	 *
	 * @param MultipartFile
	 * @return ResponseEntity<Boolean>
	 */
    //@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Borrar documento", description = "Este metodo borra un documento en rodal", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/borrarDocumento/{idConfirRodal}/{entidad}")
    public ResponseEntity<Boolean> borrarDocumento(@Parameter(description = "I de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("idConfirRodal") String idConfirRodal,
    											   @Parameter(description = "Descripcion de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("entidad") String entidad                                       
    											   ) throws RodalExceptionService{
    	
		boolean respuesta = rodalClient.borrarDocumento(idConfirRodal, "MFCT", entidad);
		
		return new ResponseEntity<Boolean>(respuesta, HttpStatus.OK);
    }
    

}
    
	

