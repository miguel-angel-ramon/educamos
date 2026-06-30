package es.jccm.edu.evaluacion.application.services.programacionAula;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionActividadCriterioEvaluacionService;

@Service
public class EvaRelacionActividadCriterioEvaluacionService implements IEvaRelacionActividadCriterioEvaluacionService {

    @Autowired
    private EvaRelacionActividadCriterioEvaluacionRepository relacionActividadCriterioEvaluacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
	@Override
	public void guardar(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion) {
		relacionActividadCriterioEvaluacionRepository.save(relacionActividadCriterioEvaluacion);
	}

	@Override
	public EvaRelacionActividadCriterioEvaluacion findByActividadIdAndCriterioEvaluacionId(Long idActividad, Long idCriterio) {
		return relacionActividadCriterioEvaluacionRepository.findByActividadIdAndCriterioEvaluacionId(idActividad, idCriterio);
	}
}