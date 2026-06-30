package es.jccm.edu.evaluacion.application.services.calificacionActividades;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaMateriaCursoGenericaRepository;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaMateriaCursoGenericaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaMateriaCursoGenericaService implements IEvaMateriaCursoGenericaService {
	
	@Autowired
	private EvaMateriaCursoGenericaRepository materiaCursoGenericaRepository;
	
	@Autowired
    private ModelMapper modelMapper;

}
