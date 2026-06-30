package es.jccm.edu.proyectosfct.application.services.datosterritoriales;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales.CodigoPaisRepository;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.ICodigoPaisService;



@Service
public class CodigoPaisService implements ICodigoPaisService {
	
	@Autowired
	private CodigoPaisRepository codigoPaisRepository;

	@Override
	public CodigoPais findCodigoPaisById(String id) {
		Optional<CodigoPais> res = codigoPaisRepository.findOneById(id);
		
		return res.isPresent() ? res.get() : null;
	}

@Override
	public List<CodigoPais> findAll() {
	    List<CodigoPais> res = codigoPaisRepository.findByPaisActivoOrderByDescripcionLarga();

	return res;
	}

}
