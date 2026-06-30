package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaInstrumentoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaInstrumentoEvaluacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaInstrumentoEvaluacionRepository extends AbstractRepository<EvaInstrumentoEvaluacion, Long, QEvaInstrumentoEvaluacion> {
    
}