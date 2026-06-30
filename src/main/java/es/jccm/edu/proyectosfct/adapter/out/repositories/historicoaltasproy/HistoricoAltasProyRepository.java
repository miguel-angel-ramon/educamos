package es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasproy;

import es.jccm.edu.proyectosfct.application.domain.historicoaltasproyectos.entities.HistoricoAltasProy;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasproyectos.entities.QHistoricoAltasProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoAltasProyRepository extends AbstractRepository<HistoricoAltasProy, Long, QHistoricoAltasProy> {

    List<HistoricoAltasProy> findByIdAltassProy(Long idAltassProy);

    HistoricoAltasProy findByIdAltassProyAndNuPeticion(Long idAltassProg, Integer nuPeticion);

}
