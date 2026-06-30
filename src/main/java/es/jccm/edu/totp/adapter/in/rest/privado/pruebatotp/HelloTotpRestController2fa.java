package es.jccm.edu.totp.adapter.in.rest.privado.pruebatotp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.annotations.Totp;
import es.jccm.edu.totp.adapter.in.rest.BasePath;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TotpBasePath)
@Tag(name = "Hello World con doble autenticacion", description = "Hello world servicio de prueba")
public class HelloTotpRestController2fa {
		
	@GetMapping("/privado/hellototp/{quien}")
	@ResponseBody
	@Totp
	public ResponseEntity<String> helloworld(
			@PathVariable(value="quien",required=true) String quien,
			@RequestHeader(value="TOTP_CODE",required=true) String codigo) {
		return ResponseEntity.ok("hola "+quien);
	}
}


