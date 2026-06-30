package es.jccm.edu.totp.adapter.in.rest.privado.preferenciasusuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.totp.adapter.in.rest.BasePath;
import es.jccm.edu.totp.adapter.in.rest.privado.JwtTokenContext;
import es.jccm.edu.totp.application.domain.PreferenciasUsuario;
import es.jccm.edu.totp.application.ports.in.GetPreferenciasUsuarioUC;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TotpBasePath)
@Tag(name = "Preferencias Usuario", description = "Gestiona las preferencias del usuario respecto del doble factor de autenticación")
public class PreferenciasUsuarioController {
	
	@Autowired
	private GetPreferenciasUsuarioUC getPreferenciasUsuario;

	@Autowired
	JwtTokenContext jwtTokenContext;
	
	@GetMapping("/privado/preferenciasusuario")
	@ResponseBody
	public ResponseEntity<PreferenciasUsuarioModel> getPreferencias() {
		
		Long oid=jwtTokenContext.getOidDeUsuarioDesdeTokenJwt();
		
		if(oid==null) {
			return new ResponseEntity<PreferenciasUsuarioModel>(HttpStatus.UNAUTHORIZED); 
		}
		
		Optional<PreferenciasUsuario> preferencias=getPreferenciasUsuario.getPreferenciasUsuario(oid);
		if(preferencias.isEmpty()) {
			return new ResponseEntity<PreferenciasUsuarioModel>(HttpStatus.NOT_FOUND);	
		}
		
		PreferenciasUsuario p=preferencias.get();
		PreferenciasUsuarioModel model=PreferenciasUsuarioModel.builder()
				.oid(""+oid)
				.mailEnvioDobleFactor(p.getMail().orElse(null))
				.metodoDeAutenticacionDobleFactor(p.getMetodoAutenticacionFavorito())
				.build();
		
		return new ResponseEntity<PreferenciasUsuarioModel>(model,HttpStatus.OK);
		
	}
}


