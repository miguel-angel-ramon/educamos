package es.jccm.edu.alumnos.application.services.acneae;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.acenae.CompensacionDesigualdadRepository;
import es.jccm.edu.alumnos.application.domain.acneae.CompensacionDesigualdad;
import es.jccm.edu.alumnos.application.ports.in.acneae.ICompensacionDesigualdadService;

@Service
public class CompensacionDesigualdadService  implements ICompensacionDesigualdadService {

	@Autowired
	private CompensacionDesigualdadRepository compensacionRepository;
	
	@Override
	public List<CompensacionDesigualdad> getAllCompensaciones() {
		
		List<CompensacionDesigualdad>  compesaciones=(List<CompensacionDesigualdad>) compensacionRepository.findAll();
		
		return compesaciones;
	}

	@Override
	public List<CompensacionDesigualdad> getCompensacionesByMatricula(Long IdMatricula) {
		
		List<CompensacionDesigualdad> compensaciones=compensacionRepository.findAllByMatriculaIdMatricula(IdMatricula);
		return compensaciones;
	}

}
