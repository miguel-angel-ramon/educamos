package es.jccm.edu.gestionidentidades.adapter.in.rest.eliminarUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.gestionidentidades.application.ports.in.IEliminarUsuarioYPersonaAsociadaUseCase;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class EliminarUsuario {

	@Autowired
	private IEliminarUsuarioYPersonaAsociadaUseCase altaUsuarioUseCase;
	
	@GetMapping("/apieducamosclm/eliminarUsuario/{oid}")
	@ResponseBody
	public ResponseEntity<String> generarSolicitudRecuperacionClave(
			@PathVariable(value="oid",required=true) String oid) {
		try {
			altaUsuarioUseCase.eliminarUsuarioYPersonaAsociada(oid);
		return new ResponseEntity<String>(oid,HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
