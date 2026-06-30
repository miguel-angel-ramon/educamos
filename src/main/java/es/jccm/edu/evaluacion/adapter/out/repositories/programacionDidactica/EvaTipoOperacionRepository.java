package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaTipoOperacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaTipoOperacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaTipoOperacionRepository extends AbstractRepository<EvaTipoOperacion, Long, QEvaTipoOperacion> {
    
}