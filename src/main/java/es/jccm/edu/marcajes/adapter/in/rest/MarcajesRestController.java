package es.jccm.edu.marcajes.adapter.in.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.marcajes.application.domain.Marcaje;
import es.jccm.edu.marcajes.application.domain.ResponseMarcaje;
import es.jccm.edu.marcajes.application.ports.in.IMarcajesService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.MarcajeBasePath)
@Tag(name = "Servicio de Marcajes del CRFP", description = "Servicio para gestionar los marcajes de los participantes a eventos del CRFP")
@CrossOrigin
public class MarcajesRestController {
	
	
	@Autowired
	private IMarcajesService marcajesService;
	
		
	@PostMapping("/crearmarcaje")
    public ResponseEntity<String> crearMarcaje(@RequestParam String idEvento, @RequestParam String urlMarcaje) {
		
		
		String salida = this.marcajesService.crearMarcaje(idEvento, urlMarcaje);
		    	
		return new ResponseEntity<String>(salida, HttpStatus.OK);
    }
	
	
	@PostMapping("/existemarcaje")
    public ResponseEntity<String> existeMarcaje(@RequestParam String idEvento, @RequestParam String urlMarcaje) {
		
		
		String salida = this.marcajesService.existeMarcaje(idEvento, urlMarcaje);
		
		    	
		return new ResponseEntity<String>(salida, HttpStatus.OK);
    }
	
	
	@PostMapping("/borrado")
    public String borrarMarcaje(@RequestParam String idEvento, @RequestParam String urlMarcaje) {
		
		String salida = ""; 
				
		salida = this.marcajesService.borrarMarcaje(idEvento, urlMarcaje);
		    	
		return salida;
    }
	
	@PostMapping("/")
    public ResponseEntity<ResponseMarcaje> obtenerMarcajes(@RequestParam String idEvento) {
		
		
		
		List<Marcaje> marcajes;
		try {
			marcajes = this.marcajesService.obtenerMarcajes(idEvento);
		} catch (Exception e) {
			marcajes = new ArrayList<Marcaje>();
		}
		
		ResponseMarcaje respMarcaje = new ResponseMarcaje();
		respMarcaje.setOk("ok");
		respMarcaje.setId(idEvento);
		respMarcaje.setMarcajes(marcajes);
		
		
		return new ResponseEntity<ResponseMarcaje>(respMarcaje, HttpStatus.OK);
	}
	
	
	
	

}
