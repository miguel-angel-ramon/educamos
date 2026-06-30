package es.jccm.edu.proyectosfct.application.services.datosterritoriales;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales.TipoViaRepository;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.ITipoViaService;

@Service
public class TIpoViaService implements ITipoViaService {
	
	@Autowired
	private TipoViaRepository tipoViaRepository;

	@Override
	public TipoVia findTipoViaById(Long id) {
		Optional<TipoVia> res = tipoViaRepository.findById(id);
		
		return res.isPresent() ? res.get() : null;
	}
	
	

}
