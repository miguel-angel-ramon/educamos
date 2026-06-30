package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;

public interface IEvaRelacionActividadCriterioEvaluacionService {

	void guardar(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion);

	EvaRelacionActividadCriterioEvaluacion findByActividadIdAndCriterioEvaluacionId(Long idActividad, Long idCriterio);

}
