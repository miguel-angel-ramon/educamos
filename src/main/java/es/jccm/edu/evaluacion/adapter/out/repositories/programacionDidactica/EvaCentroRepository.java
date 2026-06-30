package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaCentroRepository extends AbstractRepository<EvaCentro, Long, QEvaCentro> {
	
	EvaCentro findByCodigo(Long codigo);
    
}