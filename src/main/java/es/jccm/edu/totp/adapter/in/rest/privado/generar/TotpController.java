package es.jccm.edu.totp.adapter.in.rest.privado.generar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.totp.adapter.in.rest.BasePath;
import es.jccm.edu.totp.adapter.in.rest.privado.JwtTokenContext;
import es.jccm.edu.totp.adapter.in.rest.privado.preferenciasusuario.PreferenciasUsuarioModel;
import es.jccm.edu.totp.application.ports.in.generar.GenerarDobleAutenticacion;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TotpBasePath)
@Tag(name = "Códig Totp", description = "Gestiona las preferencias del usuario respecto del doble factor de autenticación")
public class TotpController {


	@Autowired
	JwtTokenContext jwtTokenContext;
	
	@Autowired
	GenerarDobleAutenticacion generarDobleAutenticacion;
	
	@GetMapping("/privado/totp")
	@ResponseBody
	public ResponseEntity<PreferenciasUsuarioModel> postTotp() {
		
		Long oid=jwtTokenContext.getOidDeUsuarioDesdeTokenJwt();
		
		if(oid==null) {
			return new ResponseEntity<PreferenciasUsuarioModel>(HttpStatus.UNAUTHORIZED); 
		}
		
		
		//TODO:
		return null;

	}
}


