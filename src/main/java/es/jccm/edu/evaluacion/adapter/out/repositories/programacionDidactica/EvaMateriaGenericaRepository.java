package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaMateriaGenerica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaMateriaGenerica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMateriaGenericaRepository extends AbstractRepository<EvaMateriaGenerica, Long, QEvaMateriaGenerica> {
    
}