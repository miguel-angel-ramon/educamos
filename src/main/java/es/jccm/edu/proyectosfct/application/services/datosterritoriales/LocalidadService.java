package es.jccm.edu.proyectosfct.application.services.datosterritoriales;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales.LocalidadRepository;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.ILocalidadService;

@Service
public class LocalidadService implements ILocalidadService {
	
	@Autowired
	private LocalidadRepository localidadRepository;

	@Override
	public Localidad findLocalidadById(Long id) {
		Optional<Localidad> res = localidadRepository.findById(id);
		
		return res.isPresent() ? res.get() : null;
	}

}
