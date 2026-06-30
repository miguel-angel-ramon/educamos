package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;

@Service
public class EvaCriterioEvaluacionService implements IEvaCriterioEvaluacionService {

    @Autowired
    private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public EvaCriterioEvaluacion getCriterioById(Long idCriterio) {
		return criterioEvaluacionRepository.findById(idCriterio).orElse(null);
	}

	@Override
	public CriterioEvaluacionDTO getCriterioByAbreviatura(String abrev) {
		EvaCriterioEvaluacion criterio = criterioEvaluacionRepository.findByAbreviatura(abrev).orElse(null);
		if(criterio != null) {
			return modelMapper.map(criterio, CriterioEvaluacionDTO.class);
		} else {
			return null;
		}
	}
}