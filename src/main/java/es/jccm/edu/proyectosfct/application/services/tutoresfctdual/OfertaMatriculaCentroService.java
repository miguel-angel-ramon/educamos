package es.jccm.edu.proyectosfct.application.services.tutoresfctdual;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.OfertaMatriculaCentroRepository;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaCentro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.OfertaMatriculaProjection;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IOfertaMatriculaCentroService;

@Service
public class OfertaMatriculaCentroService implements IOfertaMatriculaCentroService {
	
	@Autowired
	private OfertaMatriculaCentroRepository ofertaMatriculaCentroRepository;
	
	// Read
	
	public OfertaMatriculaCentro getOfertaMatriculaCentroById(Long idOferta) {
		Optional<OfertaMatriculaCentro> res = ofertaMatriculaCentroRepository.findById(idOferta);
		
		return res.isPresent() ? res.get() : null;
	}

	public List<OfertaMatriculaProjection> getOfertasByCentro(Long idCentro) {
		//TODO: Usar anno actual, no hay datos ni para 2021 ni para 2022
		Integer anno = 2015;
		
		return ofertaMatriculaCentroRepository.getOfertasByCentro(idCentro, anno);
	}

}
