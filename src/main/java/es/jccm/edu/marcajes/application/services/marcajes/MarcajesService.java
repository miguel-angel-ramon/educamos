package es.jccm.edu.marcajes.application.services.marcajes;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.marcajes.adapter.out.repositories.marcajes.MarcajesRepository;
import es.jccm.edu.marcajes.application.domain.Marcaje;
import es.jccm.edu.marcajes.application.ports.in.IMarcajesService;

@Service
public class MarcajesService implements IMarcajesService {
	
	private static final Logger LOG = LogManager.getLogger(MarcajesService.class);
	
	@Autowired
	private MarcajesRepository marcajesRepository;
	
	@Override
	public String existeMarcaje(String idEvento, String urlMarcaje) {
		return this.marcajesRepository.getExisteMarcaje(idEvento, urlMarcaje);

	}
	
	

	@Override
	public String crearMarcaje(String idEvento, String urlMarcaje) {
		
		Marcaje marcaje = new Marcaje();
		marcaje.setIdEvento(idEvento);
		marcaje.setUrlMarcaje(urlMarcaje);
		marcaje.setFechaEntrada(new Date());
		try {
			this.marcajesRepository.save(marcaje);
		} catch (Exception e) {
			System.out.println( e );
			return "Ya existe este marcaje";
		}
		return "ok";
	}

	

	@Override
	public String borrarMarcaje(String idEvento, String urlMarcaje) {
		
		Marcaje marcaje =new Marcaje();
		marcaje.setIdEvento("No hay evento");
		
		try {
			marcaje = this.marcajesRepository.findByIdEventoUrlMarcaje(idEvento, urlMarcaje);
			this.marcajesRepository.delete(marcaje);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "No existe este marcaje";
		}
		
		
		
		return "borrado";
		
		
	}

	@Override
	public List<Marcaje> obtenerMarcajes(String idEvento) {
		
		return this.marcajesRepository.findByIdEvento(idEvento);
		
		
	}
	
	
	

}
