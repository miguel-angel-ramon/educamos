package es.jccm.edu.sms.adapter.in.rest.sms;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.afirma.adapter.in.rest.afirma.model.DocumentoFirmadoDto;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DatosPersonalesUsuarioDto;
import es.jccm.edu.simulacion.application.ports.in.usuarios.IUsuariosService;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoFirmadoSmsDTO;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoPendienteFirmaSmsDTO;
import es.jccm.edu.sms.adapter.in.rest.sms.model.SmsFirmaDTO;
import es.jccm.edu.sms.application.domain.sms.entities.Sms;
import es.jccm.edu.sms.application.ports.in.sms.ISmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/sms")
@Tag(name = "Servicio SMS", description = "Servicio con las operaciones sobre los SMS")
public class SmsRestController {


    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IUsuariosService usuariosService;
    @Autowired
    private ISmsService smsService;


    @Operation(summary = "Genera un código para enviarselo al usuario")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "SMS enviado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Sms.class))}), @ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content), @ApiResponse(responseCode = "404", description = "No se ha podido generar el código", content = @Content)})
    @GetMapping("/generarCodigo")
    public ResponseEntity<String> enviarSms(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
            DatosPersonalesUsuarioDto datosPersonalesOut = modelMapper
                    .map(usuariosService.getDatosPersonalesUsuario(datosUsuario.getXUsuarioComunica(),datosUsuario.getOid()), DatosPersonalesUsuarioDto.class);
            smsService.envia(datosPersonalesOut, datosUsuario.getXUsuarioComunica());
            return new ResponseEntity<String>("SMS enviado correctamente.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Ha alcanzado el límite máximo de envío de SMS por día.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error en el envío de SMS.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Verifica si un código enviado es válido")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Verificación completa", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Sms.class))}), @ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content), @ApiResponse(responseCode = "404", description = "No se ha encontrado el código", content = @Content)})
    @GetMapping("/verficiarCodigo")
    public ResponseEntity<Boolean> verificarCodigo(@RequestParam("codigoVerificacion") String codigoVerificacion, @RequestHeader(Constants.AUTHORIZATION) String jwt) throws Exception {
        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        Boolean valoracion = smsService.getVerificacionSmsByCodigo(codigoVerificacion, datosUsuario.getXUsuarioComunica());
        return new ResponseEntity<Boolean>(valoracion, HttpStatus.OK);
    }

   @Operation(summary = "Verifica si un código enviado existente ha sido usado y se procede a firmar")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Firma completada correctamente",
                   content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoFirmadoDto.class))}),
           @ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content),
           @ApiResponse(responseCode = "404", description = "No se ha encontrado el código", content = @Content),
           @ApiResponse(responseCode = "500", description = "Firma fallida, error interno o código no válido", content = @Content)})
   @PostMapping("/verificarCodigoYFirmar")
   public ResponseEntity<List<DocumentoFirmadoSmsDTO>> verificarCodigoYFirma(
           @RequestBody List<DocumentoPendienteFirmaSmsDTO> smsFirmaDto,
           @RequestHeader(Constants.AUTHORIZATION) String jwt) {
       try {
           DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
           //DatosPersonalesUsuarioDto datosPersonalesOut = modelMapper
           //        .map(usuariosService.getDatosPersonalesUsuario(datosUsuario.getXUsuarioComunica()), DatosPersonalesUsuarioDto.class);
           List<DocumentoFirmadoSmsDTO> documentosFirmadosDto = smsService.verificarFirmar(
        		   smsFirmaDto,
                   datosUsuario);
           return new ResponseEntity<>(documentosFirmadosDto, HttpStatus.OK);
       } catch (Exception e) {
           e.getMessage();
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   @Operation(summary = "Se procede a firmar digitalmente documentos")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Firma completada correctamente",
                   content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoFirmadoDto.class))}),
           @ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content),
           @ApiResponse(responseCode = "404", description = "No se ha encontrado el documento", content = @Content),
           @ApiResponse(responseCode = "500", description = "Firma fallida o error interno", content = @Content)})
   @PostMapping("/firmarDocumentos")
   public ResponseEntity<List<DocumentoFirmadoSmsDTO>> firmarDocumentos(
           @RequestBody List<DocumentoPendienteFirmaSmsDTO> documentosBase64,
           @RequestHeader(Constants.AUTHORIZATION) String jwt) {
       try {
           DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
          // DatosPersonalesUsuarioDto datosPersonalesOut = modelMapper
          //         .map(usuariosService.getDatosPersonalesUsuario(datosUsuario.getXUsuarioComunica()), DatosPersonalesUsuarioDto.class);
           List<DocumentoFirmadoSmsDTO> documentosFirmadosDto = smsService.firmarDocumentos(
                   documentosBase64,
                   datosUsuario);
           if(documentosFirmadosDto.size() > 0) {
        	   return new ResponseEntity<>(documentosFirmadosDto, HttpStatus.OK); 
           } else {
        	   return new ResponseEntity<>(documentosFirmadosDto, HttpStatus.NOT_FOUND); 
           }
          
       } catch (Exception e) {
           e.getMessage();
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   /**
	 * Devuelve el teléfono del usuario logado para mostrarselo durante el proceso de firma
	 * 
	 * 
	 * @param unidad
	 * @param convocatoria
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve el teléfono del usuario logado", description = "Devuelve el teléfono del usuario logado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTelefonoUsuarioLogado")
	public ResponseEntity<String> getTelefonoUsuarioLogado(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
																				
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		String telefono = smsService.getTelefonoUsuarioLogado(datosUsuario.getIdEmpleadoComunica());
		
		return new ResponseEntity<>(telefono, HttpStatus.OK);
	}

}