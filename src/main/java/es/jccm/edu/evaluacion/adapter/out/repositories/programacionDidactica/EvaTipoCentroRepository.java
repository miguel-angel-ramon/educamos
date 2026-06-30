package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaTipoCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaTipoCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaTipoCentroRepository extends AbstractRepository<EvaTipoCentro, Long, QEvaTipoCentro> {
    
}