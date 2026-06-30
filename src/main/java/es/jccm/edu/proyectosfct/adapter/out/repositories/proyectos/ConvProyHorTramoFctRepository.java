package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConvProyHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConvProyHorTramoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConvProyHorTramoFctRepository extends AbstractRepository<ConvProyHorTramoFct, Long, QConvProyHorTramoFct> {

	List<ConvProyHorTramoFct> findAllByPeriodoHorarioId(Long id);
}
