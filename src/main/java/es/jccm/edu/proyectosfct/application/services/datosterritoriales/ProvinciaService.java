package es.jccm.edu.proyectosfct.application.services.datosterritoriales;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales.ProvinciaRepository;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Provincia;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.IProvinciaService;

@Service
public class ProvinciaService implements IProvinciaService {

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Provincia findProvinciaById(Long id) {
		Optional<Provincia> res = provinciaRepository.findById(id);
		
		return res.isPresent() ? modelMapper.map(res.get(), Provincia.class) : null;
	}

	@Override
	public List<Provincia> getListadoProvincias() {
		return provinciaRepository.findAllProvincias();
	}
	
	@Override
	public List<Provincia> getListadoProvinciasManchegas() {
		return provinciaRepository.findAllByEsManchega("S");
	}

}
