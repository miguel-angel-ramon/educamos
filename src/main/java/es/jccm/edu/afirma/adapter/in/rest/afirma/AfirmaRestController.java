package es.jccm.edu.afirma.adapter.in.rest.afirma;

import es.jccm.edu.afirma.adapter.in.rest.afirma.model.AfirmaVerifyInputDto;
import es.jccm.edu.afirma.adapter.in.rest.afirma.model.DocumentoFirmadoDto;
import es.jccm.edu.afirma.application.ports.in.afirma.IAfirmaService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/afirma")
@Tag(name = "", description = "")
@CrossOrigin
public class AfirmaRestController {

	@Autowired
	private IAfirmaService afirmaService;
	
    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;


	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value = "/verificarFirma")
    public ResponseEntity<DocumentoFirmadoDto> verificarFirma(@RequestBody AfirmaVerifyInputDto afirmaVerifyInputDTO) throws SOAPException, ParserConfigurationException, IOException, SAXException {
        return new ResponseEntity<>(afirmaService.verificarFirma(afirmaVerifyInputDTO.getFirmaBase64(),
                afirmaVerifyInputDTO.getHashDocumentoBase64().replace(" ", "+")), HttpStatus.OK);



	}
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value = "/verificarFirma2")
    public ResponseEntity<Integer> verificarFirma2(@RequestBody AfirmaVerifyInputDto afirmaVerifyInputDTO,
    		                                       @RequestHeader(Constants.AUTHORIZATION) String jwt
    		                                       ) throws SOAPException, ParserConfigurationException, IOException, SAXException {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
		
		Integer result = afirmaService.verificarFirma2(afirmaVerifyInputDTO.getFirmaBase64(),
													   afirmaVerifyInputDTO.getHashDocumentoBase64().replace(" ", "+"),
													   datosUsuario.getNif());		
		
        return new ResponseEntity<Integer>(result, HttpStatus.OK);



	}
	

}
