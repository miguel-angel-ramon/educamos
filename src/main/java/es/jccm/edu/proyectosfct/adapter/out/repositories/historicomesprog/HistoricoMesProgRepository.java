package es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesprog;

import es.jccm.edu.proyectosfct.application.domain.historicomesprogramas.entities.HistoricoMesProg;
import es.jccm.edu.proyectosfct.application.domain.historicomesprogramas.entities.QHistoricoMesProg;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoMesProgRepository extends AbstractRepository<HistoricoMesProg, Long, QHistoricoMesProg> {

    List<HistoricoMesProg> findByIdCotizaMesProg(Long idCotizaMes);

    HistoricoMesProg findByIdCotizaMesProgAndNuPeticionMesProg(Long idCotizaMes, Integer nuPeticion);

}
