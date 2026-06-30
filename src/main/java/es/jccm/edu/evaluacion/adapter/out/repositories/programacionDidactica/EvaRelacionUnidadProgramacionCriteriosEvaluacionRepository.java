package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionUnidadProgramacionCriteriosEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionUnidadProgramacionCriteriosEvaluacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository extends AbstractRepository<EvaRelacionUnidadProgramacionCriteriosEvaluacion, Long, QEvaRelacionUnidadProgramacionCriteriosEvaluacion> {
 
	List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> findByUnidadProgramacionId(Long unidadProgramacionId);
	
	EvaRelacionUnidadProgramacionCriteriosEvaluacion findByCriterioEvaluacionIdAndUnidadProgramacionId(Long criterioEvaluacionId, Long unidadProgramacionId);
	
	void deleteAllByUnidadProgramacionId(Long unidadProgramacionId);

	List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> findAllByUnidadProgramacion(EvaUnidadProgramacion unidadProgramacion);

	@Transactional
	void deleteAllByUnidadProgramacion(EvaUnidadProgramacion unidadProgramacion);
	
	@Query("SELECT COUNT(*) FROM EvaRelacionUnidadProgramacionCriteriosEvaluacion ruc INNER JOIN EvaUnidadProgramacion unpr ON unpr = ruc.unidadProgramacion WHERE ruc.criterioEvaluacion.id = :criterioEvaluacionId")
	Long countByCriterioEvaluacion(Long criterioEvaluacionId);
	
}