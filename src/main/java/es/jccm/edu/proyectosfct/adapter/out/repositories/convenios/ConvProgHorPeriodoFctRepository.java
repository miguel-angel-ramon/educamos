package es.jccm.edu.proyectosfct.adapter.out.repositories.convenios;

import java.util.List;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConvProgHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.QConvProgHorPeriodoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConvProgHorPeriodoFctRepository extends AbstractRepository<ConvProgHorPeriodoFct, Long, QConvProgHorPeriodoFct> {

	List<ConvProgHorPeriodoFct> findAllByConvenioProgramaId(Long idConvProg);
}
