package es.jccm.edu.proyectosfct.application.services.tutoresfctdual;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.CentroRepository;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ICentroService;

@Service
public class CentroService implements ICentroService {
	
	@Autowired
	private CentroRepository centroRepository;

	// Read
	
	public Centro getCentroById(Long idCentro) {
		Optional<Centro> res = centroRepository.findById(idCentro);
		
		return res != null ? res.get() : null;
	}

}
