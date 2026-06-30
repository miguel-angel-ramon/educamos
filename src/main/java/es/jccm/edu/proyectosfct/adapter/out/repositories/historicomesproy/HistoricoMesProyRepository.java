package es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesproy;

import es.jccm.edu.proyectosfct.application.domain.historicomesproyectos.entities.HistoricoMesProy;
import es.jccm.edu.proyectosfct.application.domain.historicomesproyectos.entities.QHistoricoMesProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoMesProyRepository extends AbstractRepository<HistoricoMesProy, Long, QHistoricoMesProy> {

    List<HistoricoMesProy> findByIdCotizaMesProy(Long idCotizaMes);

    HistoricoMesProy findByIdCotizaMesProyAndNuPeticionMesProy(Long idCotizaMes, Integer nuPeticion);

}
