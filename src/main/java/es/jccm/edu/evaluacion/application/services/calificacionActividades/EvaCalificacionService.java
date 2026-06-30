package es.jccm.edu.evaluacion.application.services.calificacionActividades;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaCalificacionRepository;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaCalificacionService;

@Service
public class EvaCalificacionService implements IEvaCalificacionService {
	
	@Autowired
	private EvaCalificacionRepository calificacionRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public EvaCalificacion getCalificacionById(Long idCalifica) {
		Optional<EvaCalificacion> calificacion = calificacionRepository.findById(idCalifica);
		if(calificacion.isPresent()) {
			return calificacion.get();
		} else {
			return null;
		}
	}

}
