package es.jccm.edu.proyectosfct.adapter.out.repositories.convenios;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConvProgHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.QConvProgHorTramoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConvProgHorTramoFctRepository extends AbstractRepository<ConvProgHorTramoFct, Long, QConvProgHorTramoFct> {

	List<ConvProgHorTramoFct> findAllByPeriodoHorarioId(Long id);
}
