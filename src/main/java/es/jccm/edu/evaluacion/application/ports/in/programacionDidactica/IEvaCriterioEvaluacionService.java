package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;

public interface IEvaCriterioEvaluacionService {

	EvaCriterioEvaluacion getCriterioById(Long idCriterio);

	CriterioEvaluacionDTO getCriterioByAbreviatura(String shortname);

}
