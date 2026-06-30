package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.ProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.QProgramacionDidactica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ProgramacionDidacticaRepository extends AbstractRepository<ProgramacionDidactica, Long, QProgramacionDidactica> {
    
}

