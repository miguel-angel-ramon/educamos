package es.jccm.edu.totp.adapter.in.rest.privado.cuentagoolgle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.totp.adapter.in.rest.BasePath;
import es.jccm.edu.totp.adapter.in.rest.privado.JwtTokenContext;
import es.jccm.edu.totp.application.ports.in.GestionCuentaGoogleUC;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TotpBasePath)
@Tag(name = "cuenta google", description = "Gestiona la cuenta google")
public class CuentaGoogleController {


	@Autowired
	JwtTokenContext jwtTokenContext;
	
	@Autowired
	GestionCuentaGoogleUC gestionCuentaGoogleUC;
	
	
	@GetMapping("/privado/cuentagoogle/mandarqractivacion")
	@ResponseBody
	public ResponseEntity<String> mandarqractivacion() {
		
		Long oid=jwtTokenContext.getOidDeUsuarioDesdeTokenJwt();
		
		if(oid==null) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED); 
		}

		gestionCuentaGoogleUC.enviarQrActivacionGoogle(oid, "jmartind@jccm.es");
		return ResponseEntity.ok("ok");
	}
}


