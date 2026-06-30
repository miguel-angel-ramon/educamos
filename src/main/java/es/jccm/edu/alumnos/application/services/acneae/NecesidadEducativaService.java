package es.jccm.edu.alumnos.application.services.acneae;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.acenae.NecesidadEducativaRepository;
import es.jccm.edu.alumnos.application.domain.acneae.NecesidadEducativa;
import es.jccm.edu.alumnos.application.ports.in.acneae.INecesidadEducativaService;
@Service
public class NecesidadEducativaService implements INecesidadEducativaService{
	
	@Autowired
	private NecesidadEducativaRepository neeRepository;
	


	
	@Override
	public List<NecesidadEducativa> getAllNEE() {
		List <NecesidadEducativa> nee=(List<NecesidadEducativa>) neeRepository.findAll();
		return nee;
	}

	@Override
	public List<NecesidadEducativa> getAllNEEByAdaptacion(String adaptacion) {
		List<NecesidadEducativa> nee=neeRepository.findAllByAdaptacion(adaptacion);
		
		return nee;
	}

	@Override
	public List<NecesidadEducativa> getNEEByMatricula(Long idMatricula) {
		List <NecesidadEducativa> nee=neeRepository.findAllByMatriculaIdMatricula(idMatricula);
		
		return nee;
	}

	@Override
	public List<NecesidadEducativa> getNEEByAdaptacionAndMatricula(String adaptacion, Long idMatricula) {
		
		List <NecesidadEducativa> nee=neeRepository.findAllByAdaptacionAndMatriculaIdMatricula(adaptacion, idMatricula);
		
		return nee;
	}



}
