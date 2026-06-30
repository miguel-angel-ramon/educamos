package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionProgramacionDidacticaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionProgramacionDidacticaUnidadProgramacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionProgramacionDidacticaUnidadProgramacionRepository extends AbstractRepository<EvaRelacionProgramacionDidacticaUnidadProgramacion, Long, QEvaRelacionProgramacionDidacticaUnidadProgramacion> {
    
	List<EvaRelacionProgramacionDidacticaUnidadProgramacion> findByProgramacionDidactica(EvaProgramacionDidactica programacionDidactica);

	void deleteAllByUnidadProgramacion(EvaUnidadProgramacion borrarUnidadProgramacion);
	
}