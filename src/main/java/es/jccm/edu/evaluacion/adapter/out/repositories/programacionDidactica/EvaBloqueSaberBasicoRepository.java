package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaBloqueSaberBasico;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaBloqueSaberBasico;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaBloqueSaberBasicoRepository extends AbstractRepository<EvaBloqueSaberBasico, Long, QEvaBloqueSaberBasico> {

}
