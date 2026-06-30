package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionActividadCriterioEvaluacionRepository extends AbstractRepository<EvaRelacionActividadCriterioEvaluacion, Long, QEvaRelacionActividadCriterioEvaluacion> {

    List<EvaRelacionActividadCriterioEvaluacion> findAllByActividadId(Long idActividad);

	void deleteAllByActividad(EvaActividad idActividad);

	EvaRelacionActividadCriterioEvaluacion findByActividadIdAndCriterioEvaluacionId(Long idActividad, Long idCriterio);

}