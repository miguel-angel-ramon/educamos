package es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasprog;

import es.jccm.edu.proyectosfct.application.domain.historicoaltasprogramas.entities.HistoricoAltasProg;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasprogramas.entities.QHistoricoAltasProg;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoAltasProgRepository extends AbstractRepository<HistoricoAltasProg, Long, QHistoricoAltasProg> {

    List<HistoricoAltasProg> findByIdAltassProg(Long idAltassProg);

    HistoricoAltasProg findByIdAltassProgAndNuPeticion(Long idAltassProg, Integer nuPeticion);

}
