package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionProgramacionDidacticaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionProgramacionDidacticaPonderacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionProgramacionDidacticaPonderacionRepository extends AbstractRepository<EvaRelacionProgramacionDidacticaPonderacion, Long, QEvaRelacionProgramacionDidacticaPonderacion> {
    
	EvaRelacionProgramacionDidacticaPonderacion findByProgramacionDidacticaId(Long idProgramacionDidactica);
	
}