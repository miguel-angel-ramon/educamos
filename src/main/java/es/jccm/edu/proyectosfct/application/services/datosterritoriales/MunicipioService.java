package es.jccm.edu.proyectosfct.application.services.datosterritoriales;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales.MunicipioRepository;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.IMunicipioService;

@Service
public class MunicipioService implements IMunicipioService {
	
	@Autowired
	private MunicipioRepository municipioRepository;

	@Override
	public Municipio findMunicipioByProvinciaAndMunicipio(Long idProvincia, Long idMunicipio) {
		Optional<Municipio> res = municipioRepository.findByIdProvinciaAndIdMunicipio(idProvincia, idMunicipio);
		
		return res.isPresent() ? res.get() : null;
	}
	

}
