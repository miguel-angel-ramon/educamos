package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.QConvProgAluHorTramoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConvProgAluHorTramoFctRepository extends AbstractRepository<ConvProgAluHorTramoFct, Long, QConvProgAluHorTramoFct> {

	List<ConvProgAluHorTramoFct> findAllByPeriodoAlumnoHorarioId(Long id);

	long countByPeriodoAlumnoHorarioId(Long id);

}
