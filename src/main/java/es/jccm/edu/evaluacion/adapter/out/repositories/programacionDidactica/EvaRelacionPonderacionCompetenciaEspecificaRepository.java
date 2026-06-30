package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionPonderacionCompetenciaEspecifica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionPonderacionCompetenciaEspecifica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EvaRelacionPonderacionCompetenciaEspecificaRepository extends AbstractRepository<EvaRelacionPonderacionCompetenciaEspecifica, Long, QEvaRelacionPonderacionCompetenciaEspecifica> {
    
	@Transactional
	void deleteAllByPonderacion(EvaPonderacion ponderacion);

	List<EvaRelacionPonderacionCompetenciaEspecifica> getAllByPonderacion(EvaPonderacion ponderacion);
	
}